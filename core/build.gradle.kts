plugins {
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(project(":api"))
}

tasks {
    shadowJar {
        relocate("META-INF.versions.9.org.apache.logging.log4j", "org.apache.logging.log4j")
    }

    assemble {
        finalizedBy(shadowJar)
    }

    jar {
        manifest {
            attributes("Main-Class" to "org.spigotmc.cogs.core.CogsEntrypoint")
        }
    }
}
