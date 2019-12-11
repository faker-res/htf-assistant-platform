#!/usr/bin/env bash

PROJECT_NAME=$1

if [ ! -n "$1" ];then
    echo "please input PROJECT_NAME prameter like [md-service-mongo] [md-parser-pdf] [md-parser-excel].";
    exit
fi

echo "stop service: ${PROJECT_NAME}"

id=`ps aux|grep 'java'|grep -E ${PROJECT_NAME} |awk '{print $2}'`
echo "service pid: ${id}"
ps aux|grep 'java'|grep -E ${PROJECT_NAME} |awk '{print $2}'| xargs kill -9

if [ "$?" = "0" ]; then
    echo "${1%/} STOP succeed"
    echo $BOOT_START_LOG
else
    echo "${1%/} STOP failed"
    echo $BOOT_START_LOG
fi