import xyz.izadi.convention.adibide.AdibideBuildType

plugins {
    id("adibide.android.application")
    id("adibide.android.application.compose")
    id("adibide.android.application.jacoco")
    id("adibide.android.hilt")
}

android {
    namespace = "xyz.izadi.adibide"

    defaultConfig {
        applicationId = "xyz.izadi.adibide"
        versionCode = 1
        versionName = "0.0.1" // x.y.z; x = major, y = minor, z = patch level

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = AdibideBuildType.DEBUG.applicationIdSuffix
        }

        val release by getting {
            isMinifyEnabled = true
            applicationIdSuffix = AdibideBuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // TODO: if this is going to be released, remember to properly sign it
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    packaging.apply {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":core:designsystem"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)

    androidTestImplementation(project(":core:testing"))
}
