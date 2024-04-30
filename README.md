# 소셜 네트워크 서비스 String

### 개발기간
- 2024-04-27 ~ ing
- JIRA대신 깃허브 Projects 기능 사용 (이슈 생성 및 PR 진행)
- WIKI를 사용하여 필요한 내용은 기록해둘 예정 (운영에 대한 정보는 비공개)

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
- 모놀리식 프로젝트 (추후 MSA로 변환 가능성 있음)
- MVC패턴 적용 (controller, service, repository 3계층)
- Spring Security를 통한 로그인 및 인증기능 구현 (JWT)
- 서비스는 interface를 적용시켜 OCP를 지키도록 설계 (객체지향적인 설계)
- 단일 책임 원칙(SRP)에 따른 코드 설계 (객체지향적인 설계)
- Factory Method 패턴으로 객체를 생성하여 무분별하게 생성자가 사용되는것을 방지
- Entity와 DTO를 확실하게 분리시킴 (도메인과 POJO 객체의 분리로 계층을 확실히 분리하여 설계)
- Entity와 DTO의 변환은 따로 converter 클래스를 만들고 그곳에서 처리하도록 설계
- String 서버 전용 예외처리 코드인 CustomException사용 (ErrorCode, Handler 작성)
- 배포를 위한 docker 이미지 빌드는 local, prod로 나눠서 구현 (dockercompose로 구현)
- 환경설정(yml)은 local, prod, test 총3개로 분리되어있음 (local, prod는 postgreSQL을 사용하고 test는 h2 db를 사용한다.)
- CI/CD는 깃허브의 hook을 사용하며 소나큐브를 통해 코드를 테스트한 후 배포가 진행되도록 설계
- 롤링 배포방식으로 무중단 배포가 가능하도록 설계

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
