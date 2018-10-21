#!/bin/bash
source /etc/profile   # 脚本开头添加
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/config

SERVER_NAME=`sed '/dubbo.app.name/!d;s/.*=//' config/zk.properties | tr -d '\r'`
SERVER_PROTOCOL=`sed '/dubbo.protocol.name/!d;s/.*=//' config/zk.properties | tr -d '\r'`
SERVER_PORT=`sed '/dubbo.protocol.port/!d;s/.*=//' config/zk.properties | tr -d '\r'`
LOGS_FILE=`sed '/dubbo.log4j.file/!d;s/.*=//' config/zk.properties | tr -d '\r'`

START_PATH=`/bin/readlink -f $0`
PATH_MD5=`echo -n  $START_PATH  | /usr/bin/openssl md5 | /bin/awk '{printf $2}'`
FINAL_MD5=`/bin/hostname`-`echo ${PATH_MD5:0:5}`
PINPOINT="-javaagent:/service/software/pinpoint-agent/pinpoint-bootstrap-1.6.2.jar -Dpinpoint.agentId=$FINAL_MD5 -Dpinpoint.applicationName=wms3-service"  # 项目-应用名

if [ -z "$SERVER_NAME" ]; then
    SERVER_NAME=`hostname`
fi

PIDS=`ps -f | grep java | grep "$CONF_DIR" |awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "ERROR: The $SERVER_NAME already started!"
    echo "PID: $PIDS"
    exit 1
fi

if [ -n "$SERVER_PORT" ]; then
    SERVER_PORT_COUNT=`netstat -tln | grep $SERVER_PORT | wc -l`
    if [ $SERVER_PORT_COUNT -gt 0 ]; then
        echo "ERROR: The $SERVER_NAME port $SERVER_PORT already used!"
        exit 1
    fi
fi

LOGS_DIR=""
if [ -n "$LOGS_FILE" ]; then
    LOGS_DIR=`dirname $LOGS_FILE`
else
    LOGS_DIR=$DEPLOY_DIR/logs
fi

if [ ! -d $LOGS_DIR ]; then
    mkdir $LOGS_DIR
fi

if [ -d "/service/logs/service/production/wms/service" ];then
        echo "the dir is exit"
    else
        mkdir -p /service/logs/service/production/wms/service
fi
STDOUT_FILE=/service/logs/service/production/wms/service/stdout.log

LIB_DIR=$DEPLOY_DIR/lib
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`

JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
JAVA_DEBUG_OPTS=""
if [ "$1" = "debug" ]; then
    JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
fi
JAVA_JMX_OPTS=""
if [ "$1" = "jmx" ]; then
    JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "
fi
JAVA_MEM_OPTS=""
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server -Xmx4g -Xms4g -Xmn1500m -XX:PermSize=256m -Xss256k -XX:SurvivorRatio=4 -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -Djava.rmi.server.hostname=$SERVER_NAME -Dfile.encoding=UTF-8 -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "
else
    JAVA_MEM_OPTS=" -server -Xms1g -Xmx1g -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi

echo -e "Starting the $SERVER_NAME ...\c"
nohup java $PINPOINT $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -javaagent:/service/software/perfino/perfino.jar=server=10.11.31.11,port=18001,name=$SERVER_NAME,group=wms -classpath  $CONF_DIR:$LIB_JARS com.jumbo.ProductProvider > $STDOUT_FILE 2>&1 &

COUNT=0
while [ $COUNT -lt 1 ]; do    
    echo -e ".\c"
    sleep 1 
    if [ -n "$SERVER_PORT" ]; then
        if [ "$SERVER_PROTOCOL" == "dubbo" ]; then
    	    COUNT=`echo status | nc -i 1 127.0.0.1 $SERVER_PORT | grep -c OK`
        else
            COUNT=`netstat -an | grep $SERVER_PORT | wc -l`
        fi
    else
    	COUNT=`ps -f | grep java | grep "$DEPLOY_DIR" | awk '{print $2}' | wc -l`
    fi
    if [ $COUNT -gt 0 ]; then
        break
    fi
done

echo "OK!"
PIDS=`ps -f | grep java | grep "$DEPLOY_DIR" | awk '{print $2}'`
echo "PID: $PIDS"
echo "STDOUT: $STDOUT_FILE"
