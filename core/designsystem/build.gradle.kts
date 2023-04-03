plugins {
    id("adibide.android.library")
    id("adibide.android.library.compose")
}

android {
    namespace = "xyz.izadi.adibide.core.designsystem"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.material3)

    debugApi(libs.androidx.compose.ui.tooling)
    debugApi(libs.androidx.compose.ui.tooling.preview)
}
