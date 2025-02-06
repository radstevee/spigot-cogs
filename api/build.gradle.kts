plugins {
    alias(libs.plugins.checkers)
    `java-library`
}

dependencies {
    api(libs.javacord)
    api(libs.jetbrains.annotations)
    api(libs.gson)
    api(libs.checkers.qual)
    api(libs.bundles.logging)
}
