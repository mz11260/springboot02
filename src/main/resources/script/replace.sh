# replace.sh 构建完成后备份旧版本和拷贝新版本
# 执行脚本需要传入jenkins构建项目的路径或远程登录后的目录和编译后的jar包名称
# ./replace.sh $WORKSPACE/xxx/xxx xxx.jar
#!/bin/bash

current_file_path=$(cd `dirname $0`; pwd)
cd "$current_file_path"
cd ../

PROJECT_HOME=`pwd`
BUILD_DIR=$1
JAR_NAME=$2

echo "project home: ${PROJECT_HOME}"
echo "jenkins build directory: ${BUILD_DIR}"
echo "jar name: ${JAR_NAME}"
#for loop in `ls ${PROJECT_HOME}/*.jar`;do
#   JAR_NAME=${loop}
#   break
#done
#JAR_NAME=${JAR_NAME##*/}

HISTORY_DIR="${PROJECT_HOME}/history/"

if [ ! -d ${HISTORY_DIR} ]
then
    mkdir ${HISTORY_DIR}
    echo "Make directory 'history' successfully"
fi

if [ -f "${PROJECT_HOME}/${JAR_NAME}" ]
then
   echo "Moving old versions to history directory"
   mv ${PROJECT_HOME}/${JAR_NAME} ${HISTORY_DIR}/${JAR_NAME}.`date +%Y%m%d%H%M%S`
fi

echo "Moving '${BUILD_DIR}/${JAR_NAME}' to project home"
mv ${BUILD_DIR}/${JAR_NAME} ${PROJECT_HOME}/${JAR_NAME}