plugins {
    alias(libs.plugins.shadow) apply false
    alias(libs.plugins.checkers) apply false
}

group = "org.spigotmc.cogs"
version = property("cogsVersion")!!

allprojects {
    group = rootProject.group
    version = rootProject.version

    apply(plugin = "java-library")

    repositories {
        mavenCentral()
    }
}
