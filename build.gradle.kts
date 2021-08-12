import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
//    id("org.springframework.boot") version "2.5.2"
//    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.20"
//    kotlin("plugin.spring") version "1.5.20"
//    kotlin("plugin.jpa") version "1.5.20"
}

group = "cbx"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
//    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    testImplementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
   implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.9.8")
//    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//    testImplementation("org.jetbrains.kotlin:kotlin-reflect")
//    testImplementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//    testImplementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation(kotlin("test"))
//    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.test {
    useJUnitPlatform()
}
