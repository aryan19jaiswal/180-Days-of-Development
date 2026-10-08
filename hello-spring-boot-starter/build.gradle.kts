plugins {
	`java-library`
}

group = "com.daysOfBatman"
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
	api(platform("org.springframework.boot:spring-boot-dependencies:4.1.1"))
	api("org.springframework.boot:spring-boot-autoconfigure")
	annotationProcessor(platform("org.springframework.boot:spring-boot-dependencies:4.1.1"))
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}
