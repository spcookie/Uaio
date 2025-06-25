//import jdk.tools.jlink.resources.plugins

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.kotlin.kapt") version "1.9.25"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.25"
    id("io.micronaut.crac") version "4.5.3"
    id("io.micronaut.application") version "4.5.3"
    id("com.gradleup.shadow") version "8.3.6"
    id("io.micronaut.test-resources") version "4.5.3"
    id("io.micronaut.aot") version "4.5.3"
}

version = "0.1"
group = "io.net"

val kotlinVersion = project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

dependencies {
    kapt("io.micronaut.data:micronaut-data-processor")
    kapt("io.micronaut:micronaut-http-validation")
    kapt("io.micronaut.microstream:micronaut-microstream-annotations")
    kapt("io.micronaut.serde:micronaut-serde-processor")
    kapt("io.micronaut.validation:micronaut-validation-processor")
    kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")
    implementation("io.micrometer:context-propagation")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut:micronaut-retry")
    implementation("io.micronaut.cache:micronaut-cache-caffeine")
    implementation("io.micronaut.crac:micronaut-crac")
    implementation("io.micronaut.data:micronaut-data-r2dbc")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.microstream:micronaut-microstream")
    implementation("io.micronaut.microstream:micronaut-microstream-annotations")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.micronaut.sql:micronaut-jooq")
    implementation("io.micronaut.validation:micronaut-validation")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("org.apache.logging.log4j:log4j-api")
    implementation(platform("org.apache.logging.log4j:log4j-bom:2.24.2"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jobrunr:jobrunr-micronaut-feature:6.3.5")
    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("org.apache.logging.log4j:log4j-core")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl")
    runtimeOnly("org.postgresql:r2dbc-postgresql")
    runtimeOnly("org.yaml:snakeyaml")
    testImplementation("io.micronaut:micronaut-http-client")
    developmentOnly("io.micronaut.controlpanel:micronaut-control-panel-management")
    developmentOnly("io.micronaut.controlpanel:micronaut-control-panel-ui")
    testResourcesService("org.postgresql:postgresql")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:atomicfu:0.19.0")
    implementation("cn.hutool:hutool-all:5.8.20")
    implementation("io.ktor:ktor-server-core-jvm:3.2.0")
    implementation("io.ktor:ktor-server-netty:3.2.0")
    implementation("io.ktor:ktor-server-core:3.2.0")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
}


application {
    mainClass = "io.net.ApplicationKt"
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("io.net.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}



