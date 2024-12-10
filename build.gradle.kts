plugins {
    id("java")
    id("idea")
}

group = "com.github.gguangnian.retry"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.slf4j:slf4j-api")

    runtimeOnly("org.apache.logging.log4j:log4j-core")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")

    constraints {
        compileOnly("org.slf4j:slf4j-api:2.0.16")

        runtimeOnly("org.apache.logging.log4j:log4j-core:2.24.0")
        runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.24.0")

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
        testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    }
}

tasks.test {
    useJUnitPlatform()
}