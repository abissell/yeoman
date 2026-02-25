import net.ltgt.gradle.errorprone.errorprone
import net.ltgt.gradle.nullaway.nullaway

plugins {
    java
    id("net.ltgt.errorprone")
    id("net.ltgt.nullaway")
    id("com.diffplug.spotless")
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

val catalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    errorprone(catalog.findLibrary("errorprone-core").get())
    errorprone(catalog.findLibrary("nullaway").get())
    implementation(catalog.findLibrary("jspecify").get())

    testImplementation(catalog.findLibrary("org-junit-jupiter-junit-jupiter").get())
    testRuntimeOnly(catalog.findLibrary("org-junit-platform-junit-platform-launcher").get())
}

nullaway {
    onlyNullMarked = true
}

spotless {
    java {
        googleJavaFormat().aosp()
        removeUnusedImports()
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("--enable-preview")
    options.errorprone.nullaway {
        error()
    }
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("--enable-preview")
}
