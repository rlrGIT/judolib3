plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "dev.russell"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-hateoas")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("io.github.wimdeblauwe:htmx-spring-boot-thymeleaf:3.5.0")
	implementation("org.springframework.session:spring-session-core")

	implementation("software.amazon.awssdk:s3:2.27.9")
	implementation("software.amazon.awssdk:url-connection-client:2.27.15")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	testImplementation("org.testcontainers:junit-jupiter:1.20.1")
	testImplementation("org.testcontainers:localstack:1.20.1")
	testImplementation("org.testcontainers:mongodb:1.20.1")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
