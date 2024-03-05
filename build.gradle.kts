plugins {
    kotlin("jvm") version "1.9.22"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val dropwizardVersion = "4.0.3"

dependencies {
    implementation("io.dropwizard:dropwizard-core:$dropwizardVersion")
    implementation("io.dropwizard:dropwizard-auth:$dropwizardVersion")

    testImplementation("io.dropwizard:dropwizard-testing:$dropwizardVersion")

    testImplementation("io.rest-assured:rest-assured:5.3.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}