plugins {
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(project(":api"))
}

tasks {
    assemble {
        finalizedBy(shadowJar)
    }

    jar {
        manifest {
            attributes("Main-Class" to "org.spigotmc.cogs.core.CogsEntrypoint")
        }
    }
}
