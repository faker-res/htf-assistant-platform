#!/bin/bash

# author: zhairp
# date: 2019.5.11

DEST_FILE=$1
DEST_DIR=$2
PROFILE=$3

if [ ! -n "$1" ];then
    echo "please input DEST_FILE parameter like [${DEST_FILE}]"
    exit
fi

id=`ps aux|grep 'java'|grep -E ${DEST_FILE}|awk '{print $2}'`
echo "service pid: ${id}"
ps aux|grep 'java'|grep -E ${DEST_FILE}|awk '{print $2}'|xargs kill -9
java -server -jar ${DEST_DIR}/${DEST_FILE} --spring.profiles.active=${PROFILE} >${DEST_DIR}/logs/out.log &
#java -server -jar ${DEST_DIR}/${DEST_FILE} --spring.profiles.active=${PROFILE} >/dev/null 2>&1 &

if [ "$?" = "0" ];then
    echo "${DEST_FILE} STOP succeed"
    id=`ps aux|grep 'java'|grep -E ${DEST_FILE}|awk '{print $2}'`
    echo "service new pid: ${id}"
else
    echo "${DEST_FILE} STOP failed"
fi