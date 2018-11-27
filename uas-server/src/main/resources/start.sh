#!/bin/sh
echo "starting"
PIDFILE="./run.pid"
pwd=`pwd`
cd ${pwd}
nohup java -Xms512m -Xmx1024m -XX:PermSize=256m -jar uas-server-0.0.1-SNAPSHOT.jar & echo $! > ${PIDFILE}
echo "start ok"
