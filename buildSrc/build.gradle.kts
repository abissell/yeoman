plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("net.ltgt.gradle:gradle-errorprone-plugin:5.0.0")
    implementation("net.ltgt.gradle:gradle-nullaway-plugin:3.0.0")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:8.2.1")
    implementation("org.gradlex:extra-java-module-info:1.14")
}
