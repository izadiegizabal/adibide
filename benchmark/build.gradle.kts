plugins {
    id("adibide.android.test")
    alias(libs.plugins.androidx.baselineprofile)
}

android {
    namespace = "xyz.izadi.adibide.baselineprofile"

    defaultConfig {
        minSdk = 28 // minimum supported baseline profile generator
    }

    targetProjectPath = ":app"
}

baselineProfile {
    managedDevices += "pixel6api31aosp"
    useConnectedDevices = false
}

dependencies {
    implementation(libs.androidx.test.ext)
    implementation(libs.androidx.test.espresso.core)
    implementation(libs.androidx.test.uiautomator)
    implementation(libs.androidx.benchmark.macro)
}
