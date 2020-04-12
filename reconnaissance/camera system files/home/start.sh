#!/bin/sh

getHwInfo()
{
        grep $1 /home/hardinfo.bin | awk -F '>'  '{print $2}' | awk -F '<' '{print $1}'
}

getHwCfg()
{
        grep $1 /home/hwcfg.ini | awk '{printf $3}'
}

grep mtd3 /proc/mtd > /dev/null && ro_rootfs=1
mkdir /var/run

#check if stop app auto run
read -t 1 -p "Press 'q' in 1 seconds to exit: " q
if [ $? -eq 0 -a "$q" = "q" ]; then exit; fi

if [ -n "$ro_rootfs" ]; then
        echo "ro_rootfs"
else
        if [ -f /home/wpa_supplicant ]; then
                rm -f /bin/wpa_supplicant
                ln -s /home/wpa_supplicant /bin/wpa_supplicant
        fi

    cp -f /home/tees /bin
    chmod -R 777 /home
fi

#check if old sc1135
#or if ii2 3229 is 2
mirror_type=$(getHwCfg gk_mirror_type)
if [ "$mirror_type" = "1" ]; then
    echo "sensor do not support vi mirror! check sensor_hw.bin"
    mv -f /home/sensors/sc1135_hw.bin_old_10bits /home/sensors/sc1135_hw.bin
fi

#drivers
if [ -n "$ro_rootfs" ]; then
    insmod /home/drv/gio.ko.lzma
else
    insmod /home/gio.ko
fi
insmod /home/drv/exfat.ko.lzma
mdev -s

#run custom init for board OEM
/home/custom_init.sh

#init isp
/home/sensor.sh

#Extract {sensor_name}.bin & {sensor_name}_hw.bin to /home/sensors
if [ -d /home/sensors/lzma -a -L /tmp/sensor_hw.bin ]; then
    sensor_name=$(basename `readlink /tmp/sensor_hw.bin` _hw.bin)
    BINNAME="${sensor_name}.bin"

    lens_mm=$(getHwCfg lens_mm)
    [ -n "$lens_mm" ] && BINNAME=$BINNAME-${lens_mm}mm;

    BINFILE=/home/sensors/lzma/$BINNAME.lzma
    if [ ! -f $(readlink /tmp/sensor_hw.bin) ]; then
        rm -f /home/sensors/sc*.bin /home/sensors/gc*.bin /home/sensors/jx*.bin

        unlzma -c $BINFILE > /home/sensors/${sensor_name}.bin
        /home/initool /home/sensors/drv.cfg set :drvname $BINNAME

        HW_BIN_LZMA=`ls /home/sensors/lzma/${sensor_name}*_hw.bin.lzma`
        unlzma -c $HW_BIN_LZMA > /home/sensors/${sensor_name}_hw.bin
    else
        DRVNAME=`/home/initool /home/sensors/drv.cfg get :drvname`
        [ -z "$DRVNAME" ] && DRVNAME=$sensor_name.bin
        if [ "$BINNAME" != "$DRVNAME" ]; then
            unlzma -c $BINFILE > /home/sensors/${sensor_name}.bin
            /home/initool /home/sensors/drv.cfg set :drvname $BINNAME
        fi
    fi
fi

#check if 1080, modify uboot args
/home/check_mem.sh

BOARD_ID=$(getHwInfo BoardType)

#mount SD card
if [ -b /dev/mmcblk0p1 ]; then
        mount -t vfat /dev/mmcblk0p1  /mnt
        mount -t exfat /dev/mmcblk0p1  /mnt
elif [ -b /dev/mmcblk0 ]; then
        mount -t vfat /dev/mmcblk0 /mnt
        mount -t exfat /dev/mmcblk0 /mnt
fi

#Upgrade firmware from TF card
if [ -f /mnt/firmware.bin ]; then
        /bin/sdc_tool -d $BOARD_ID -c /home/model.ini /mnt/firmware.bin

        #check upgrade from OTA or factory test
        if [ -f /mnt/OTA ]; then
                rm /mnt/firmware.bin
                rm /mnt/OTA
        else
                if [ -n "$ro_rootfs" ]; then
                    mount -t tmpfs tmpfs /opt
                fi
                touch /opt/upgrading
        fi
fi

#Run facoty_tool.sh for burn id and change voice and change hwcfg.ini
/home/factory_tool.sh

#Run debug_cmd.sh
if [ -f "/mnt/debug_cmd.sh" ]; then
        echo "find debug cmd file, wait for cmd running..."
        /mnt/debug_cmd.sh
fi
umount /mnt

#Update form flash
if [ -f /home/firmware.bin ]; then
        /bin/sdc_tool -d $BOARD_ID /home/firmware.bin
        if [ $? -eq 0 ]; then
                echo "upgrade success."
        else
                echo "upgrade failed."
        fi
        rm -f /home/firmware.bin
fi

#network init
idProduct=`cat /sys/bus/usb/devices/1-1/idProduct`
idVendor=`cat /sys/bus/usb/devices/1-1/idVendor`
WIFIDRVS="mt7601Usta 8188fu 9083h"
if [ -n "$ro_rootfs" ]; then DRVPATH=/home/drv; else DRVPATH=/home; fi
if [ "$idVendor" = "2310" -a "$idProduct" = "9086" ]; then
    WIFIDRV=9083h
elif [ "$idVendor" = "0bda" -a "$idProduct" = "f179" ]; then
    WIFIDRV=8188fu
elif [ "$idProduct" = "7601" ]; then
    WIFIDRV=mt7601Usta
fi
if [ -n "$WIFIDRV" ]; then
    for w in $WIFIDRVS; do
        if [ $w != $WIFIDRV ]; then rm -f $DRVPATH/$w.ko*; fi
    done
    insmod $DRVPATH/$WIFIDRV.ko.lzma || insmod $DRVPATH/$WIFIDRV.ko
fi

#check if old sc1135
#or if ii2 3229 is 2
mirror_type=$(getHwCfg gk_mirror_type)
if [ "$mirror_type" = "1" ]; then
    echo "sensor do not support vi mirror! check sensor_hw.bin"
    mv -f /home/sensors/sc1135_hw.bin_old_10bits /home/sensors/sc1135_hw.bin
fi

#run tees for debug info
/home/tees -s -v -b 20 -e ps -e 'ifconfig; route -n' -e 'wpa_cli status' -e 'mount' -e 'uptime' -e 'df' -e 'netstat -napt' -e free -a /tmp/closelicamera.log -o /mnt/mmc01/1/ipc.log -o /mnt/mmc01/0/ipc.log &

#ID
sed -i '/test_max_pos/d' /home/ptz.cfg
if [ ! -f /home/eye.conf ]; then
     EXTRA_FLAGS='test_max_pos=1'
else
     EXTRA_FLAGS='test_max_pos=0'
fi
#init ptz
ptz_mcu=$(getHwCfg ptz_mcu)
has_ptz=$(getHwCfg support_ptz)
if [ "$ptz_mcu" = "1" ]; then
        if [ -n "$ro_rootfs" ]; then
                mv -f /home/drv/gkptz-dsa.ko.lzma /home/drv/gkptz.ko.lzma
        else
                mv -f /home/gkptz-dsa.ko /home/gkptz.ko
        fi
fi

if [ "$has_ptz" = "1" ]; then
    NO_SLFCK=0
    if [ -f /home/silent_reboot ]; then
        NO_SLFCK=1; rm /home/silent_reboot;
    elif [ $(getHwCfg ptz_no_selfck) -eq 1 ]; then
        if [ -f /home/devParam.dat -o $(getHwCfg support_ap_mode) -eq 2 ]; then
            NO_SLFCK=1;
        fi
    fi
    if [ -n "$ro_rootfs" ]; then
        insmod /home/drv/gkptz.ko.lzma cfg_file=/home/ptz.cfg psp_file=/home/psp.dat no_selfck=$NO_SLFCK $EXTRA_FLAGS
    else
        insmod /home/gkptz.ko cfg_file=/home/ptz.cfg psp_file=/home/psp.dat no_selfck=$NO_SLFCK $EXTRA_FLAGS
        fi
    #check hwcfg.ini, start ptz auto test if configured
    /home/auto_ptz_test.sh
fi

#433. read pins configuration from hardinfo.bin
dsa_ko=$(getHwCfg support_433)
if [ "$dsa_ko" = "1" ]; then
    dsa_pins=$(getHwInfo DsaPins)
    if [ -n "$dsa_pins" ]; then dsa_pins='dsa_pins='$dsa_pins; fi
        if [ -n "$ro_rootfs" ]; then
        insmod /home/drv/dsa.ko.lzma $dsa_pins
        else
            insmod /home/dsa.ko
        fi
fi
#wpa_supplicant -B -iwlan0 -c /home/wpa_supplicant.conf &
mdev -s

[ -f /home/custom_init.drv.sh ] && /home/custom_init.drv.sh

#sleep 1
ifconfig lo 127.0.0.1
ifconfig wlan0 up
ifconfig ra0 up
ifconfig eth0 up
MAC=`ifconfig | awk '/wlan0/{print $NF}'`
( echo "$MAC" | grep 00:E0:4C ) && (echo "err mac"; reboot; exit)
( echo "$MAC" | grep FF:FF:FF ) && (echo "err mac"; reboot; exit)
MAC1=`ifconfig | awk '/ra0/{print $NF}'`
( echo "$MAC1" | grep 00:E0:4C ) && (echo "err mac1"; reboot; exit)
( echo "$MAC1" | grep FF:FF:FF ) && (echo "err mac1"; reboot; exit)
export PATH=/home/ap:$PATH
/home/rsyscall.goke
if [ $? -eq 0 ]; then
        echo "rsyscall_suc"
        touch /tmp/rsycall_start
else
        echo "rsyscall_failed"
fi

echo 524288 > /proc/sys/net/core/wmem_max
echo 1450 > /sys/class/net/wlan0/mtu
echo 1450 > /sys/class/net/ra0/mtu
echo 1450 > /sys/class/net/eth0/mtu
[ -f /home/custom_init.last.sh ] && /home/custom_init.last.sh

tar xzf /home/VOICE.tgz -C /tmp
if [ -f /tmp/VOICE/OVERSEA ]; then
    cp /home/cloud_oversea.ini /tmp/cloud.ini
else
    cp /home/cloud.ini /tmp
    echo "CST-8" > /etc/TZ
fi
cp /home/ca-bundle-add-closeli.crt /tmp


cd /tmp
(
export CLOSELICAMERA_LOGMAXLINE=1000
/p2pcam/p2pcam
echo -e '[data]\ntime = '`date +%s` > /home/reboot.time
if [ -f /tmp/firmware.bin ]; then
    /bin/sdc_tool -d $BOARD_ID /tmp/firmware.bin
    sync
    reboot
else
    echo "Crashed ? dump log..."
    killall -10 tees
fi
)&

sleep 5
sync; echo 3 > /proc/sys/vm/drop_caches
free
