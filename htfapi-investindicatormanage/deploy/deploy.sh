#!/bin/bash

# author: zhairp
# date: 2019.5.11
# 命令:
#  chmod 740 deploy/deploy.sh
#  ./deploy/deploy.sh dev/test/prod
# dev 开发环境
# test 测试环境
# prod 生产环境

PROFILE=$1
export DEST_HOST_DEV=(10.50.16.124)
export DEST_HOST_TEST=(10.50.16.126)
export DEST_HOST_PROD=(127.0.0.1)

export DEST_USER=root
export DEST_DIR=/data/www/service-indicatormanage
export DEST_FILE=service-indicatormanage.jar

#参数校验
if [ ! -n "$1" ]; then
    echo "please input ENV paramete like [dev] [test] [prod].";
    exit
fi

if test "$1" = "dev";then
    DEST_HOST=(${DEST_HOST_DEV[*]})
fi
if test "$1" = "test";then
    DEST_HOST=(${DEST_HOST_TEST[*]})
fi
if test "$1" = "prod";then
    DEST_HOST=(${DEST_HOST_PROD[*]})
fi
echo "Current ENV is ":$PROFILE,"Current DEST_HOST is":${DEST_HOST[*]}

function package {
     mvn clean package -Dmaven.test.skip=true -P$PROFILE
     if [ $? -ne 0 ]; then
         echo "package error!" 1>&2
         exit 1
     fi
     echo "package success!"
}

function deploy {
    for((i=0;i<${#DEST_HOST[*]};i++))
       do
           echo "deploy DEST_HOST:${DEST_HOST[$i]}! "
           ssh -T ${DEST_USER}@${DEST_HOST[$i]} << EOF
              mkdir -p ${DEST_DIR}/logs
EOF
           scp target/${DEST_FILE} deploy/restart.sh ${DEST_USER}@${DEST_HOST[$i]}:${DEST_DIR}/
           ssh -T ${DEST_USER}@${DEST_HOST[$i]} << EOF
              sh ${DEST_DIR}/restart.sh ${DEST_FILE} ${DEST_DIR} ${PROFILE}
EOF
       done
}

function auto_build {
   package
   deploy
   echo "自动化部署结束..."
}

echo "自动化部署开始..."
auto_build