#!/bin/bash

##
# author: Jenkin.K
# date: 2017.12.15
#
# 命令: ./deploy.sh dev/pre/prod
# dev: 测试环境
# pre: 预发布环境
# prod: 生产环境
# module 目录有两级结构的,只需修改PROJECT_NAME={PROJECT_NAME}
# module 目录是一级结构的,需要修改PROJECT_NAME={PROJECT_NAME} 与 FILE_PATH=target and TOOLS_PATH=deploy/tools/bin
# SERVER_PORT 服务启动的端口

if [ ! -n "$1" ];then
    echo "please input ENV paramete like [dev] [test] [prod].";
    exit
fi

export PROJECT_NAME=registercenter
export APP_HOME=/niub/www

SRC_DIR=$(dirname "$0")
FILE_PATH=target
TOOLS_PATH=deploy/tools/bin
export FILE_NAME=${PROJECT_NAME}.jar
export DEST_DIR=${APP_HOME}/${PROJECT_NAME}
export ENV=$1
export SERVER_PORT=8001
export PROJECT_PATH=registercenter

date=$(date +%Y%m%d%H%M)

dev=(
118.31.117.206
)
pre=(
118.31.249.221
)
prod=(
47.97.85.125
)
prod_trial=(
118.31.113.111
)

SERVER_LIST=()

if [ "${ENV}" == "dev" ]; then
    SERVER_LIST=(${dev[*]})
fi
if [ "${ENV}" == "test" ]; then
    SERVER_LIST=(${pre[*]})
fi
if [ "${ENV}" == "prod" ]; then
    SERVER_LIST=(${prod[*]})
fi
if [ "${ENV}" == "prod_trial" ]; then
        SERVER_LIST=(${prod_trial[*]})
fi

echo "ENV is : " $1
echo 'service list = ': ${SERVER_LIST[*]}

for (( i=0; i<${#SERVER_LIST[*]}; ++i ))
    do
        echo SERVER_LIST ip : ${SERVER_LIST[$i]}
    done

function package {
    mvn clean package -U -Dmaven.test.skip=true -P${ENV}
}

function deploy {
	for (( i=0; i<${#SERVER_LIST[*]}; ++i ))
        do
            echo "backup jar on SERVER_LIST , ip [ ${SERVER_LIST[$i]} ] !"
            ssh -T jenkins@${SERVER_LIST[$i]} << EOF
                sudo -i
                mkdir -p ${DEST_DIR}/logs
                mkdir -p ${DEST_DIR}/bak
                chown -R jenkins:jenkins ${DEST_DIR}
                mv ${DEST_DIR}/${FILE_NAME} ${DEST_DIR}/bak/${FILE_NAME}.${date}

EOF
            echo "rsync jar to SERVER_LIST , ip [ ${SERVER_LIST[$i]} ] !"
            echo "rysnc -vz ${FILE_PATH}/${FILE_NAME} jenkins@${SERVER_LIST[$i]}:${DEST_DIR}/${FILE_NAME}"
            scp ${FILE_PATH}/${FILE_NAME} jenkins@${SERVER_LIST[$i]}:${DEST_DIR}/${FILE_NAME}
            echo "TOOLS_PATH:" ${TOOLS_PATH}
            scp ${TOOLS_PATH}/start.sh ${TOOLS_PATH}/stop.sh jenkins@${SERVER_LIST[$i]}:${DEST_DIR}
            echo "restart ${PROJECT_NAME} service"
            ssh -T jenkins@${SERVER_LIST[$i]} << EOF
                sudo -i
                echo "stop ################################################"
                sh ${DEST_DIR}/stop.sh ${PROJECT_NAME}
                echo "start ################################################"
                sh ${DEST_DIR}/start.sh ${PROJECT_NAME} ${ENV}
EOF
#            healthCheck ${SERVER_LIST[$i]}
            echo ${SERVER_LIST[$i]} "发布成功"
        done
}

function healthCheck {
    HEALTH_CHECK_URL=http://$1:${SERVER_PORT}/healthCheck
    for k in {1..20}; do
        STATUS_CODE=`curl -o /dev/null -s -w "%{http_code}" ${HEALTH_CHECK_URL}`
        if [ "$STATUS_CODE" = "200" ]; then
           echo $1 "发布成功"
           break
        else
           echo "健康检查失败 [" ${k} "] 次:"${HEALTH_CHECK_URL}
           sleep 1
        fi

        if [ $k = 20 ]; then
            echo "发布失败"
        fi
    done
}

function de {
    package
    deploy
}

echo "if before ${ENV}V"
if [ "${ENV}V" != "V" ]; then
    echo "enter if"
	de
fi