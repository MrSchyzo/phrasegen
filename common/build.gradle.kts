plugins {
    kotlin("jvm") version "1.7.10"
    `jvm-test-suite`
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    api("io.github.mrschyzo:opiniom:0.1.0")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.2")
}

group = "com.mrschyzo"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    kotlinOptions.javaParameters = true
}

tasks.test {
    useJUnitPlatform()
}
