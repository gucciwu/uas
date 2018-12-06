PIDFILE='./run.pid'
echo "stopping..."
PID=`head -1 ${PIDFILE}`
echo ${PID} 
`kill -9 ${PID}`
echo "stop ok"
