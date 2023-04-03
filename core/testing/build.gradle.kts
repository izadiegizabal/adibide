plugins {
    id("adibide.android.library")
    id("adibide.android.library.compose")
    id("adibide.android.hilt")
}

android {
    namespace = "xyz.izadi.adibide.core.testing"
}

dependencies {
    api(libs.androidx.compose.ui.test)
    api(libs.androidx.test.core)
    api(libs.androidx.test.espresso.core)
    api(libs.hilt.android.testing)
    api(libs.junit)

    debugApi(libs.androidx.compose.ui.testManifest)
}
