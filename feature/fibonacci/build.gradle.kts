plugins {
    id("adibide.android.feature")
    id("adibide.android.library.compose")
    id("adibide.android.library.jacoco")
}

android {
    namespace = "xyz.adibide.feature.fibonacci"
}

dependencies {
    implementation(libs.androidx.activity.compose)
}
