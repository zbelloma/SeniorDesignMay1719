#!/bin/bash
varia=$(</home/pi/SeniorDesignMay1719/pybt/rboot.txt)
echo $varia
if [[ "$varia" == "0" ]]
then
	echo 1 > /home/pi/SeniorDesignMay1719/pybt/rboot.txt
	shutdown now -r
fi
echo 0 > /home/pi/SeniorDesignMay1719/pybt/rboot.txt
sleep 10
sudo python /home/pi/SeniorDesignMay1719/pybt/blueagent5.py &
sudo python /home/pi/SeniorDesignMay1719/pybt/server2.py &
