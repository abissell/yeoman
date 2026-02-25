plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("net.ltgt.gradle:gradle-errorprone-plugin:5.0.0")
    implementation("net.ltgt.gradle:gradle-nullaway-plugin:3.0.0")
}
