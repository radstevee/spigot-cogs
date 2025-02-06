import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin

plugins {
    alias(libs.plugins.shadow) apply false
    alias(libs.plugins.checkers) apply false
    alias(libs.plugins.spotless) apply false
}

group = "org.spigotmc.cogs"
version = property("cogsVersion")!!

allprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
    }

    apply(plugin = "java-library")
    apply<SpotlessPlugin>()

    configure<SpotlessExtension> {
        java {
            importOrder()
            removeUnusedImports()
            cleanthat()
            palantirJavaFormat()
            formatAnnotations()
            leadingTabsToSpaces(4)
        }
    }
}

tasks.register("runSpotless") {
    subprojects
        .filter { project -> project.plugins.hasPlugin(SpotlessPlugin::class.java) }
        .forEach { project -> dependsOn(project.tasks.getByName("spotlessApply")) }
}
