plugins {
    java
    application
}

repositories {
    mavenCentral()
}

version = "0.2.0"
val appName = "dentareport"
val appVersion = project.version.toString()

dependencies {
    implementation(libs.guava)
    implementation(libs.flatlaf)

    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("de.dentareport.Dentareport")
}

tasks.jar {
    archiveBaseName.set(appName)
    archiveVersion.set(appVersion)

    manifest {
        attributes("Main-Class" to application.mainClass.get())
    }
}

tasks.register<Jar>("distJar") {
    group = "distribution"
    description = "Creates a runnable fat JAR"

    dependsOn(tasks.classes)

    archiveBaseName.set(appName)
    archiveVersion.set(appVersion)
    archiveClassifier.set("dist")

    manifest {
        attributes("Main-Class" to application.mainClass.get())
    }

    configureAsFatJar()
}

tasks.register("release") {
    group = "distribution"
    description = "Runs tests and builds the distributable JAR"
    outputs.upToDateWhen { false }
    dependsOn(tasks.named("test"), tasks.named("distJar"))
}


tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    testLogging {
        events("FAILED")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}

fun Jar.configureAsFatJar() {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(
        configurations.runtimeClasspath.map {
            it.map { file ->
                if (file.isDirectory) file else zipTree(file)
            }
        }
    )

    from(sourceSets.main.get().output)
}

