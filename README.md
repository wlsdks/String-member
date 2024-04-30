# 소셜 네트워크 서비스 String

### 기술 stack
- 프레임워크: SpringBoot 3.2.5, Spring Security
- 언어: Kotlin (java21 기반)
- ORM: JPA, QueryDsl
- RDB: h2, postgreSql
- NoSQL: Redis (SNS의 실시간성 기능에 적용예정)
- 라이브러리: jwt, lombok, p6spy
- 배포: Dockerfile, docker-compose
- api명세: swagger
- 테스트 코드: Junit, gradle, mockito

### 아키텍처
- 모놀리식 프로젝트
- MVC패턴 적용 (controller, service, repository 3계층)
- 로그인 기능은 Spring Security 자체 기능을 통해 적용 (JWT 인증)
- 서비스는 interface를 적용시켜 OCP를 지키도록 설계
- Entity와 DTO를 확실하게 분리시킴
- 서버 전용 CustomException을 선언 (ErrorCode, Handler 작성)
- docker 이미지 빌드는 local, prod로 나눔 (dockercompose로 구현)
- 환경설정(yml)은 local, prod, test 총3개로 분리되어있음 (local, prod는 postgreSQL을 사용하고 test는 h2 db를 사용한다.)

### DevOps
- **CI/CD**: Jenkins, Github hook, Sonarqube
- **모니터링**: Prometheus, Grafana, Slack API
- **로그**: ELK
- **성능테스트**: nGrinder, JMeter

### 서버 실행 방법 (local)
- Dockerfile로 서버를 이미지화 시키고 이 설정을 docker-compose.yml에 연결하여 빌드한 스프링 서버와 postgreSql을 동시에 컨테이너에 올린다.
- 컨테이너로 킨 서버에서는 localhost로 URL연결이 안되서 compose설정에서 depends_on을 적어주고 그 값을 DB_URL설정에서 도메인으로 사용하는것을 확인할 수 있다.
```bash
docker-compose -f docker-compose.local.yml up --build
```

### 서버 실행 방법 (운영)
- 운영의 경우는 내부 수정이 필요 (docker-compose로 2개의 컨테이너가 돌기때문에 수정이 필요함)
```bash
docker-compose -f docker-compose.prod.yml up --build
```
