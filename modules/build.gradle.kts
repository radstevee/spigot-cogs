subprojects {
    dependencies {
        implementation(project(":api"))
        annotationProcessor(project(":api"))
    }

    tasks {
        val copy = register<Copy>("copy") {
            from(jar)
            into(rootProject.file("run/modules"))
        }

        build {
            dependsOn(copy)
        }
    }
}
