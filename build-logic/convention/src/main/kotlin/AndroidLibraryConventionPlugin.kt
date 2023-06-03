import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import xyz.izadi.adibide.convention.Configurations.androidTestImplementation
import xyz.izadi.adibide.convention.Configurations.testImplementation
import xyz.izadi.adibide.convention.configureGradleManagedDevices
import xyz.izadi.adibide.convention.configureKotlinAndroid

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureGradleManagedDevices(this)
            }

            dependencies {
                add(androidTestImplementation, kotlin("test"))
                add(testImplementation, kotlin("test"))
            }
        }
    }
}
