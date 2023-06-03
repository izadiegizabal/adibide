package xyz.izadi.adibide.benchmark.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import xyz.izadi.adibide.benchmark.BenchmarkDefaults.PACKAGE_NAME
import xyz.izadi.adibide.benchmark.fibonacci.fibonacciMoveBoard
import xyz.izadi.adibide.benchmark.fibonacci.fibonacciMoveMinimap
import xyz.izadi.adibide.benchmark.fibonacci.fibonacciRestart
import xyz.izadi.adibide.benchmark.fibonacci.fibonacciSelectNumbers
import xyz.izadi.adibide.benchmark.fibonacci.fibonacciWaitForContent

/**
 * This test class generates a basic startup baseline profile for the target package.
 *
 * We recommend you start with this but add important user flows to the profile to improve their performance.
 * Refer to the [baseline profile documentation](https://d.android.com/topic/performance/baselineprofiles)
 * for more information.
 *
 * You can run the generator with the Generate Baseline Profile run configuration,
 * or directly with `generateBaselineProfile` Gradle task:
 * ```
 * ./gradlew :app:generateReleaseBaselineProfile -Pandroid.testInstrumentationRunnerArguments.androidx.benchmark.enabledRules=BaselineProfile
 * ```
 * The run configuration runs the Gradle task and applies filtering to run only the generators.
 *
 * Check [documentation](https://d.android.com/topic/performance/benchmarking/macrobenchmark-instrumentation-args)
 * for more information about available instrumentation arguments.
 *
 * After you run the generator, you can verify the improvements running the [StartupBenchmarks] benchmark.
 **/
@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() {
        rule.collectBaselineProfile(PACKAGE_NAME) {
            // this block defines the app's critical user journey. Here we are interested in
            // optimizing for app startup. But you can also navigate and scroll
            // through your most important UI.

            // start default activity for your app
            pressHome()
            startActivityAndWait()

            // wait for content to load
            fibonacciWaitForContent()

            // move things around
            fibonacciMoveBoard()
            fibonacciMoveMinimap()

            // select some number
            fibonacciSelectNumbers()

            // restart the board
            fibonacciRestart()
        }
    }
}
