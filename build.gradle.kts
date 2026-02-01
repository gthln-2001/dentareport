plugins {
    java
    application
}

version = "0.2.0"

repositories {
    mavenCentral()
}
val appName = "dentareport"
val appVersion = project.version.toString()

dependencies {
    implementation("com.google.guava:guava:33.1.0-jre")
    implementation("com.formdev:flatlaf:3.5")
    implementation("org.apache.commons:commons-lang3:3.18.0")
    implementation("org.apache.httpcomponents.client5:httpclient5-fluent:5.3.1")
    implementation("org.apache.poi:poi-ooxml:5.4.0")
    implementation("org.apache.logging.log4j:log4j-core:2.24.3")
    implementation("org.apache.logging.log4j:log4j-api:2.24.3")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.24.3")
    implementation("org.xerial:sqlite-jdbc:3.45.3.0")

    testImplementation("org.assertj:assertj-core:3.25.3")
    testImplementation("org.jmockit:jmockit:1.49")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.2")
    testImplementation("org.mockito:mockito-core:5.11.0")

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

tasks.register("release_skip_tests") {
    group = "distribution"
    description = "Builds the distributable JAR, skips the test run"
    outputs.upToDateWhen { false }
    dependsOn(tasks.named("distJar"))
}


tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    jvmArgs(
        "-javaagent:${
        configurations.testRuntimeClasspath.get()
            .files
            .first { it.name.startsWith("jmockit") }
            .absolutePath
    }")

    testLogging {
        events("FAILED")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}

tasks.test {
    systemProperty("java.awt.headless", "true")
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

