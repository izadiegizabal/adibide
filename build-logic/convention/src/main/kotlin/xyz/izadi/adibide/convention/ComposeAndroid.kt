package xyz.izadi.adibide.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import xyz.izadi.adibide.convention.Configurations.androidTestImplementation
import xyz.izadi.adibide.convention.Configurations.implementation

/**
 * Configure Compose-specific options
 */
internal fun Project.configureComposeAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("androidxComposeCompiler").getString()
        }

        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            add(implementation, platform(bom))
            add(androidTestImplementation, platform(bom))
        }
    }
}
