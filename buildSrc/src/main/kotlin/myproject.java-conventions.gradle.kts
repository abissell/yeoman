plugins {
    java
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

version = "0.1.0"
group = "com.abissell.yeoman"

repositories {
    mavenCentral()
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("--enable-preview")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("--enable-preview")
}

val catalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    testImplementation(catalog.findLibrary("org-junit-jupiter-junit-jupiter").get())
    testRuntimeOnly(catalog.findLibrary("org-junit-platform-junit-platform-launcher").get())
}
