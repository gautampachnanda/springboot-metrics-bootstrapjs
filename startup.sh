#!/bin/sh
set -e
echo "Please ensure docker machine has been created using"
echo "docker-machine create --driver virtualbox dev"
echo "Checking docker-machine status"
output=$( docker-machine status dev )
echo $output
if [ $output != "Running" ] 
	then
		docker-machine start dev
	else
		echo "docker-machine is running"
fi
echo "Checking mongo status"
mongorunning=$( ps -ef | grep mongod | grep -v grep | grep -c mongod )
if [ $mongorunning -eq "0" ]; then
    echo "The mongod server  is DOWN" 
    nohup mongod >/dev/null 2>&1
else
    echo "The mongod server  is UP" 
fi
gradle clean ec build
docker-compose build
docker-compose up