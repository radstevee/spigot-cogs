plugins {
    alias(libs.plugins.shadow)
    application
}

dependencies {
    implementation(project(":api"))
}

tasks {
    assemble {
        finalizedBy(shadowJar)
    }
}

application {
    mainClass = "org.spigotmc.cogs.core.CogsEntrypoint"
}
