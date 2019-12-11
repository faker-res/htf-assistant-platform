#!/bin/bash

DEST_FILE=$1

if [ ! -n "$1" ];then
    echo "please input DEST_FILE parameter like [service-assistant-platform.jar]"
    exit
fi

echo "stop service: ${DEST_FILE}"

id=`ps aux|grep 'java'|grep -E ${DEST_FILE}|awk '{print $2}'`
echo "service pid: ${id}"
ps aux|grep 'java'|grep -E ${DEST_FILE}|awk '{print $2}'|xargs kill -9

if [ "$?" = "0" ];then
    echo "${DEST_FILE} STOP succeed"
    sleep 5s
    id=`ps aux|grep 'java'|grep -E ${DEST_FILE}|awk '{print $2}'`
    echo "service new pid: ${id}"
else
    echo "${DEST_FILE} STOP failed"
fi