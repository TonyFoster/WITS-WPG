plugins {
    id("java")
}

group = "tw.mcark.tony"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("io.javalin:javalin:6.3.0")
    implementation("org.quartz-scheduler:quartz:2.3.2")
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("org.seleniumhq.selenium:selenium-java:4.26.0")
    implementation("org.slf4j:slf4j-simple:2.0.16")
    implementation("com.zaxxer:HikariCP:6.1.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "tw.mcark.tony.App"
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from({
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    })
}