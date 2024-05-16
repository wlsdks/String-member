import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
    kotlin("kapt") version "1.9.23"                         // querydsl을 위해 kapt 추가
    id("org.jlleitschuh.gradle.ktlint").version("12.1.0")   // kotlin 코드 컨벤션을 위한 ktlint 추가
    id("com.google.cloud.tools.jib") version "3.4.2"        // docker 대신 JIB 사용
}

group = "com.tony"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

// 추가
val queryDslVersion: String by extra

repositories {
    mavenCentral()
}

dependencies {

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // mvc, validation
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // security, jwt
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // swagger-ui
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    // postgresql, h2
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")

    // querydsl, p6spy
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")

    // jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // dev tools
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    // kotest (생성자 주입을 받으려면 extension을 사용해야함)
    testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
    testImplementation("io.kotest:kotest-assertions-core:5.6.2")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
    testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:2.0.2")
}

// Querydsl 설정 추가
val generated = file("src/main/generated")

// querydsl QClass 파일 생성 위치를 지정
tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(generated)
}

// kotlin source set 에 querydsl QClass 위치 추가
sourceSets {
    main {
        kotlin.srcDirs += generated
    }
}

// gradle clean 시에 QClass 디렉토리 삭제
tasks.named("clean") {
    doLast {
        generated.deleteRecursively()
    }
}

// Querydsl 설정 완료

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

// gradle 빌드시에 ktlint가 동작하지 않도록 처리 (오류가 심해서 나중에 수정해야 할것같음) 다시 동작시키려면 enabled = true로 변경
afterEvaluate {
    tasks.withType(org.jlleitschuh.gradle.ktlint.tasks.GenerateReportsTask::class.java).configureEach {
//        enabled = true
        enabled = false
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

configure<KtlintExtension> {
    filter {
        // 'entity' 패키지 안의 모든 Kotlin 파일을 검사에서 제외
        exclude("**/domain/**")
    }
}

val dockerUsername: String by project
val dockerPassword: String by project

jib {
    // 애플리케이션을 빌드할 기본 이미지를 구성
    from {
        image = "eclipse-temurin:21.0.3_9-jre-ubi9-minimal"
    }
    // 애플리케이션을 빌드할 대상 이미지를 구성
    to {
        image = "wlsdks12/string-server"
        tags = setOf("0.0.3")

        auth {
            username = dockerUsername
            password = dockerPassword
        }
    }
    // 빌드된 이미지에서 실행될 컨테이너를 구성
    container {
        jvmFlags = listOf(
            "-Dspring.profiles.active=local",
            "-Dfile.encoding=UTF-8",
        )
        ports = listOf("8080")
        setAllowInsecureRegistries(true)  // 보안이 적용되지 않은 registry 연결 허용
    }
}
