import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import xyz.izadi.adibide.convention.configureGradleManagedDevices
import xyz.izadi.adibide.convention.configureKotlinAndroid
import xyz.izadi.adibide.convention.getInt

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)

                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                defaultConfig.targetSdk = libs.findVersion("targetSdk").getInt()

                configureGradleManagedDevices(this)
            }
        }
    }
}
