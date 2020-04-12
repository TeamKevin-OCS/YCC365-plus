#!/bin/sh

PTZ_TEST=/home/ptz_test
if [  ! -f /home/eye.conf ]; then
 do_test=`grep auto_test_ptz /home/hwcfg.ini | awk '{print $3}'`
 if [ "$do_test" = "1" ]; then
  test_hours=`grep ptz_test_hours /home/hwcfg.ini | awk '{print $3}'`
  if [ "$test_hours" = "" ]; then test_hours=2; fi

(
sleep 12
N=0
MAX=$(($test_hours*3600))
while [ $N -lt $MAX ]
do
    $PTZ_TEST r
    sleep 12
    N=$(($N+12))
done
)&

 fi
fi
