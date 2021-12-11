val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.0"
}

group = "com.januszpol"
version = "0.0.1"
application {
    mainClass.set("com.januszpol.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-host-common:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")

	implementation("org.jetbrains.exposed", "exposed-core", "0.34.1")
	implementation("org.jetbrains.exposed", "exposed-jdbc", "0.34.1")
	implementation("org.xerial:sqlite-jdbc:3.30.1")
}
