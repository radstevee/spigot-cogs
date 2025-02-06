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
}
