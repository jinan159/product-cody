plugins {
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    id("com.epages.restdocs-api-spec") version "0.18.0"

    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")

    application
}

group = "com.musinsa.product.search"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // spring
    implementation("org.springframework.boot:spring-boot-starter-web")

    // orm - exposed
    val exposedVersion: String by project
    implementation("org.jetbrains.exposed:exposed-spring-boot-starter:$exposedVersion")

    // serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")

    // kotlin-reflect
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // h2
    runtimeOnly("com.h2database:h2")
    testRuntimeOnly("com.h2database:h2")

    // redis
    implementation("org.redisson:redisson-spring-boot-starter:3.27.1")

    // test
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // spring-test
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // kotest
    val kotestVersion: String by project
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")

    // mocking
    testImplementation("io.mockk:mockk:1.13.7")
    testImplementation("com.ninja-squad:springmockk:4.0.2")

    // api-documentation
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.18.0")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

tasks.compileKotlin {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.jar {
    enabled = false
}

openapi3 {
    title = "Musinsa product search API"
    description = "Musinsa product search API"
    version = "${project.version}"
    format = "yaml"
    setServer("http://localhost:8080")
}

tasks.register<Copy>("copyOasToSwagger") {
    val targetFile = layout.buildDirectory.file("api-spec/openapi3.yaml").get()

    delete("src/main/resources/static/swagger-ui/openapi3.yaml")
    from("$targetFile")
    into("src/main/resources/static/swagger-ui/.")

    dependsOn("openapi3")
}

application {
    mainClass.set("com.musinsa.product.search.ApplicationKt")
}