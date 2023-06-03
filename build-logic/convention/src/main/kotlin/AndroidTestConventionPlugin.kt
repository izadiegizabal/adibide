import com.android.build.gradle.TestExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import xyz.izadi.adibide.convention.configureGradleManagedDevices
import xyz.izadi.adibide.convention.configureKotlinAndroid
import xyz.izadi.adibide.convention.getInt

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.test")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<TestExtension> {
                configureKotlinAndroid(this)

                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                defaultConfig.targetSdk = libs.findVersion("targetSdk").getInt()

                configureGradleManagedDevices(this)
            }
        }
    }
}
