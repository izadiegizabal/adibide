plugins {
    `kotlin-dsl`
}

group = "xyz.izadi.adibide.buildlogic"

java {
    toolchain {
        languageVersion.set(
            JavaLanguageVersion.of(
                libs.versions.java.get().toInt()
            )
        )
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "adibide.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "adibide.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplicationJacoco") {
            id = "adibide.android.application.jacoco"
            implementationClass = "AndroidApplicationJacocoConventionPlugin"
        }
        register("androidHilt") {
            id = "adibide.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
    }
}
