# 소셜 네트워크 서비스 String

### 기술 stack
- 프레임워크: SpringBoot 3.2.5, Spring Security
- 언어: Kotlin (java21 기반)
- ORM: JPA, QueryDsl
- RDB: h2, postgreSql
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
- 배포는 임시로 local, prod를 나눔 (dockercompose로 임시 구현)
