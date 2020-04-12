#!/bin/sh

#kernel_ver=$(uname -r)
kernel_ver=3.4.43-gk

USE_OLD_SENSOR_DRIVER=NO    #YES or NO, default no use.
USE_LOAD_HAL_DRIVER=YES      #YES or NO, default no use.

if [ $USE_LOAD_HAL_DRIVER = "YES" ];then
    insmod /lib/modules/$kernel_ver/extra/hal.ko.lzma
fi
insmod /lib/modules/$kernel_ver/extra/hw_crypto.ko.lzma
insmod /lib/modules/$kernel_ver/extra/media.ko.lzma
insmod /lib/modules/$kernel_ver/extra/audio.ko.lzma

if [ $USE_OLD_SENSOR_DRIVER = "YES" ]; then
    if [ -e /lib/modules/$kernel_ver/extra/${1}.ko.lzma ];then
        insmod /lib/modules/$kernel_ver/extra/${1}.ko.lzma
    else
        echo "/lib/modules/$kernel_ver/extra/${1}.ko.lzma is no exist."
        exit 0
    fi
else
    insmod /lib/modules/$kernel_ver/extra/sensor.ko.lzma
    # Use sensor.ko.lzma
    rm /tmp/sensor_hw.bin -f
    rm /tmp/sensor_ex.ko.lzma -f
    if [ $# -ne 1 ] ;then
        /home/sensordetect
    else
        if [ -e /etc/sensors/$1"_hw.bin" ];then
            if [ -e /tmp/sensor_hw.bin ]; then
                rm /tmp/sensor_hw.bin -f
            fi
            ln -s /etc/sensors/$1"_hw.bin" /tmp/sensor_hw.bin
        else
            echo "\"/etc/sensors/${1}_hw.bin\" is no exist."
            exit 0
        fi
        if [ -e /lib/modules/$kernel_ver/extra/$1"_ex.ko.lzma" ];then
            if [ -e /tmp/sensor_ex.ko.lzma ]; then
                rm /tmp/sensor_ex.ko.lzma -f
            fi
            ln -s /lib/modules/$kernel_ver/extra/$1"_ex.ko.lzma" /tmp/sensor_ex.ko.lzma
        else
            echo "\"/lib/modules/$kernel_ver/extra/${1}_ex.ko.lzma\" is no exist."
        fi
    fi
    if [ -e /tmp/sensor_ex.ko.lzma ];then
        insmod /tmp/sensor_ex.ko.lzma
    fi
fi

if [ -e /etc/display/lcd_hw.bin ];then
    if [ -e /tmp/lcd_hw.bin ]; then
        rm /tmp/lcd_hw.bin -f
    fi
    ln -s /etc/display/lcd_hw.bin /tmp/lcd_hw.bin
fi
