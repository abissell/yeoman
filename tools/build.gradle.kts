plugins {
    id("myproject.java-conventions")
    application
    id("org.beryx.jlink") version "3.2.1"
}

dependencies {
    implementation(project(":functions"))
}

application {
    applicationDefaultJvmArgs = listOf("-ea", "--enable-preview")
    mainModule = "com.abissell.yeoman.tools"
    mainClass = "com.abissell.yeoman.tools.Main"
}

jlink {
    addOptions("--compress", "zip-6", "--no-header-files", "--no-man-pages")
    launcher {
        name = "yeoman"
    }
}
