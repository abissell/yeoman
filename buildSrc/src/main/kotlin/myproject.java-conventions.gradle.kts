import net.ltgt.gradle.errorprone.errorprone
import net.ltgt.gradle.nullaway.nullaway
import org.gradlex.javamodule.moduleinfo.ExtraJavaModuleInfoPluginExtension

plugins {
    java
    id("net.ltgt.errorprone")
    id("net.ltgt.nullaway")
    id("com.diffplug.spotless")
    id("org.gradlex.extra-java-module-info")
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

pluginManager.withPlugin("org.gradlex.extra-java-module-info") {
    configure<ExtraJavaModuleInfoPluginExtension> {
        module("com.drewnoakes:metadata-extractor", "metadata.extractor")
        module("com.adobe.xmp:xmpcore", "xmpcore")
        // Pulled in by error_prone, NullAway, and JSpecify
        knownModule("com.github.ben-manes.caffeine:caffeine", "com.github.ben.manes.caffeine")
        module("com.github.kevinstern:software-and-algorithms", "com.github.kevinstern.softwareandalgorithms")
        module("com.google.auto:auto-common", "com.google.auto.common")
        module("com.google.auto.service:auto-service-annotations", "com.google.auto.service")
        module("com.google.auto.value:auto-value-annotations", "com.google.auto.value")
        knownModule("com.google.errorprone:error_prone_annotations", "com.google.errorprone.annotations")
        module("com.google.errorprone:error_prone_core", "com.google.errorprone.core")
        module("com.google.errorprone:error_prone_check_api", "com.google.errorprone.check.api")
        module("com.google.errorprone:error_prone_annotation", "com.google.errorprone.annotation")
        module("com.google.googlejavaformat:google-java-format", "com.google.googlejavaformat")
        knownModule("com.google.guava:guava", "com.google.guava")
        module("com.google.guava:listenablefuture", "com.google.guava.listenablefuture")
        module("com.google.protobuf:protobuf-java", "com.google.protobuf")
        module("com.uber.nullaway:nullaway", "com.uber.nullaway")
        module("io.github.eisop:dataflow-errorprone", "org.checkerframework.dataflow")
        module("io.github.java-diff-utils:java-diff-utils", "io.github.javadiffutils")
        module("javax.inject:javax.inject", "javax.inject")
        knownModule("org.checkerframework:dataflow-nullaway", "org.checkerframework.dataflow.nullaway")
        knownModule("org.jspecify:jspecify", "org.jspecify")
        knownModule("org.pcollections:pcollections", "org.pcollections")
    }
}
