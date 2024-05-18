# Dockerfile
# docker-compose를 실행하면 이 dockerfile이 실행된다.

# 사용할 Java 이미지
FROM eclipse-temurin:21

# ARG는 빌드 시간에 사용할 수 있는 변수를 선언해.
# 여기서는 JAR_FILE 변수를 선언하고, 초기값으로 ./build/libs/jpashop-0.0.1-SNAPSHOT.jar를 설정했어.
# 이는 도커 이미지를 빌드할 때 이 경로의 JAR 파일을 사용하겠다는 거야.
ARG JAR_FILE=./build/libs/string-0.0.1-SNAPSHOT.jar

# COPY 명령어는 호스트 시스템에서 Docker 컨테이너로 파일이나 폴더를 복사할 때 사용해.
# JAR_FILE 변수로 지정된 파일을 Docker 이미지 안의 app.jar라는 이름으로 복사하고 있다.
COPY ${JAR_FILE} app.jar

# ENTRYPOINT는 컨테이너가 시작될 때 실행될 명령어를 설정해. 여기서는 java 명령을 사용해 app.jar 파일을 실행하고 있다.
# 추가적으로, -Djava.security.egd=file:/dev/./urandom 옵션을 통해 더 빠른 엔트로피 생성을 위해 urandom 장치를 사용하도록 설정하고 있어.
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
