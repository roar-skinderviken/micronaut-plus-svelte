plugins {
    kotlin("jvm") version libs.versions.kotlin
    id("com.google.devtools.ksp") version "2.0.21-1.0.26"
    id("io.micronaut.application") version "4.4.5"
}

dependencies {
    runtimeOnly("org.yaml:snakeyaml")
    implementation("io.micronaut.views:micronaut-views-thymeleaf")

    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib.jdk8)

    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("io.micronaut:micronaut-http-client")
}

kotlin { jvmToolchain(21) }
application { mainClass = "no.javatec.ApplicationKt" }

micronaut {
    version = libs.versions.micronautPlatform
    runtime("netty")
    testRuntime("kotest5")
    processing {
        incremental(true)
        annotations("no.javatec.*")
    }
}

tasks.register<Copy>("processFrontendResources") {
    val backendTargetDir = project.layout.buildDirectory.dir("resources/main/static")
    val frontendBuildDir =
        project(":frontend").layout.projectDirectory.dir("dist")

    group = "Frontend"
    description = "Process frontend resources"
    dependsOn(":frontend:assembleFrontend")

    from(frontendBuildDir)
    into(backendTargetDir)
}

tasks.named("processResources") {
    dependsOn("processFrontendResources")
}