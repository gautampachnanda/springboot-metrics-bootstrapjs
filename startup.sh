#!/bin/sh
#nohup mongod >/dev/null 2>&1
gradle clean ec build
docker-compose build
docker-compose up