PROJECT_ROOT="/home/ubuntu/app"
PROJECT_NAME="Final-Project"
JAR_FILE="$PROJECT_ROOT/${PROJECT_NAME}-0.0.1-SNAPSHOT.jar"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# build 파일 복사
echo "$TIME_NOW > $JAR_FILE 파일 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT/build/libs/*.jar $JAR_FILE

# jar 파일에 실행권한 추가
echo "> $JAR_FILE 에 실행권한 추가"
chmod +x $JAR_FILE

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
nohup java -jar \
    -Dspring.config.location=/var/www/html/spring/src/main/resources/application.properties,/var/www/html/spring/src/main/resources/application.yml \
    -Dspring.profiles.active=real \
    $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG