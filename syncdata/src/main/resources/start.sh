#!/bin/sh
echo "starting"
PIDFILE="./run.pid"
pwd=`pwd`
cd ${pwd}
nohup java -Xms128m -Xmx256m -XX:PermSize=256m -jar syncdata-0.0.1-SNAPSHOT.jar & echo $! > ${PIDFILE}
echo "start ok"
