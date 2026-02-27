plugins {
    id("myproject.java-conventions")
    `java-library`
}

dependencies {
    implementation(libs.metadata.extractor)
}
