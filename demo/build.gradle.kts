plugins {
    kotlin("jvm") version "1.9.20"
    application
}

group = "com.igrvlhlb"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":lib"))
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass = "com.igrvlhlb.MainKt"
}