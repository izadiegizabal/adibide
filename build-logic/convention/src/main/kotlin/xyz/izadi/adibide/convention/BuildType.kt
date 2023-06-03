package xyz.izadi.adibide.convention

/**
 * This is shared between :app and :benchmarks module to provide configurations type safety.
 */
@Suppress("unused")
enum class AdibideBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE,
    BENCHMARK(".benchmark")
}
