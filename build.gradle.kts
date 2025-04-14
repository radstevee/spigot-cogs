import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin

plugins {
    alias(libs.plugins.shadow) apply false
    alias(libs.plugins.checkers) apply false
    alias(libs.plugins.spotless) apply false
    java
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

tasks.register("lint") {
    subprojects.forEach { project ->
        dependsOn(project.tasks.getByName("spotlessApply"))
    }
}

tasks.register<JavaExec>("run") {
    project(":modules").subprojects.forEach { project ->
        dependsOn(project.tasks["assemble"])
    }

    classpath = files(project(":core").tasks.getByName("shadowJar"))
    workingDir = file("run")
    mainClass = "org.spigotmc.cogs.core.CogsEntrypoint"
    javaLauncher = javaToolchains.launcherFor {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(21)
    }
}
