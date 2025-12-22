plugins {
    java
    application
}

repositories {
    mavenCentral()
}

version = "0.2.0"

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
    archiveBaseName.set("dentareport")
    archiveVersion.set(project.version.toString())

    manifest {
        attributes("Main-Class" to application.mainClass.get())
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(
        configurations.runtimeClasspath.map {
            it.map { file ->
                if (file.isDirectory) file else zipTree(file)
            }
        }
    )
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

