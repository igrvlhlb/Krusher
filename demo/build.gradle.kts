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
    implementation("com.github.ajalt.clikt:clikt:5.0.0")
    implementation("com.github.ajalt.clikt:clikt-markdown:5.0.0")
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass = "com.igrvlhlb.MainKt"
}

tasks.register<Jar>("appJar") {
    archiveFileName.set("${application.applicationName}-app.jar")
    manifest {
        attributes["Main-Class"] = application.mainClass
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}