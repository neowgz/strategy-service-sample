#!/bin/bash
#set -x
port=9100
cd `dirname $0`
DEPLOY_DIR=`pwd`
RUN_SH=$DEPLOY_DIR/run.sh
LIB_DIR=$DEPLOY_DIR/lib
LOG_DIR=$DEPLOY_DIR/logs
STDOUT_FILE="$LOG_DIR/log.txt"
PID_FILE="$LOG_DIR/pid"

LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`
JAVA_CP=" -classpath $LIB_JARS "
SERVICE_NAME_FLAG=" -Dservice.name=$DEPLOY_DIR"
JAVA_BASE_OPTS="$SERVICE_NAME_FLAG "

case $1 in
start)
    mkdir -p $LOG_DIR
    #safe check
    port_count=`netstat -nl  2>/dev/null |grep -w ${port} |grep "LISTEN" | wc -l`
    if [ $port_count -ge 1 ]; then
        echo "${port} is occupied by other service"
        exit 1
    fi
    echo "starting  service..."
    nohup  java $JAVA_BASE_OPTS $JAVA_CP   wgz.strategy.sample.StrategyServer >$STDOUT_FILE 2>&1 &
    echo -n $! > $PID_FILE
    count=0
    waitSeconds=0
    while [ $count -lt 1 -a $waitSeconds -lt 100 ]
    do    
        echo -e ".\c"
        sleep 1 
        count=`netstat -nl  2>/dev/null |grep -w ${port} |grep "LISTEN" | wc -l`
        waitSeconds=$((waitSeconds + 1))
    done
    if [ $count -lt 1 ]; then
        echo "fail to start service, please check!!"
        exit 1
    fi
    echo "subsidy service is started"
    ;;
stop)
    if [ ! -f "$PID_FILE" ]
    then
        echo "error: could not find file $PID_FILE"
    else
        pid_n=`cat $PID_FILE`
        kill -9 $(cat "$PID_FILE") > /dev/null 2>&1
        rm $PID_FILE
        echo "$pid_n subsidy service is stopped" 
    fi
    ;;
restart)
    $RUN_SH stop $@
    sleep 2 
    $RUN_SH start $@
    ;;
*)
    echo "Usage: $0 {start|stop|restart} " 
    exit 1
esac