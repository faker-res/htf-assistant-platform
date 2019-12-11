#!/usr/bin/env bash

APP_HOME=/niub/www
PROJECT_NAME=$1
ENV=$2
FILE_NAME=${PROJECT_NAME}.jar

if [ ! -n "$1" ];then
    echo "please input PROJECT_NAME prameter like [md-service-mongo] [md-parser-pdf] [md-parser-excel].";
    exit
fi

if [ ! -n "$2" ];then
    echo "please input ENV prameter like [dev] [pre] [prod].";
    exit
fi

echo "start service: ${PROJECT_NAME}"
echo " path: ${APP_HOME}/${PROJECT_NAME}/${FILE_NAME}  env : ${ENV}"
java -server -jar ${APP_HOME}/${PROJECT_NAME}/${FILE_NAME} --spring.profiles.active=${ENV} >/dev/null 2>&1 &


if [ "$?" = "0" ]; then
    echo "${1%/} start succeed"
    echo $BOOT_START_LOG
else
    echo "${1%/} start failed"
    echo $BOOT_START_LOG
fi

