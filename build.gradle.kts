import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23"
	kotlin("plugin.jpa") version "1.9.23"

	// querydsl을 위해 kapt 추가
	kotlin("kapt") version "1.9.21"
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

	// spring
	implementation("org.springframework.boot:spring-boot-starter-web")

	// spring security, jwt
	implementation("org.springframework.boot:spring-boot-starter-security")
//	implementation("io.jsonwebtoken:jjwt-api:0.12.5")
//	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
//	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// swagger-ui
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

	// postgresql, h2
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2")

	// jpa, querydsl, p6spy
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")

	// jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
	kapt("jakarta.annotation:jakarta.annotation-api")
	kapt("jakarta.persistence:jakarta.persistence-api")

	implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")

	// dev tools
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
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

kapt {
	generateStubs = true
}
// Querydsl 설정 완료

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
