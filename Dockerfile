# Dockerfile

# 사용할 Java 이미지
FROM eclipse-temurin:21

# 프로젝트 디렉토리를 /app으로 설정
WORKDIR /app

# 빌드 결과물을 컨테이너에 복사
COPY ./build/libs/*.jar ./app.jar

# 애플리케이션 실행 명령어
ENTRYPOINT ["java","-jar","/app/app.jar"]