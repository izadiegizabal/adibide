package xyz.izadi.adibide.benchmark

import android.graphics.Point
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Direction.DOWN
import androidx.test.uiautomator.Direction.LEFT
import androidx.test.uiautomator.Direction.RIGHT
import androidx.test.uiautomator.Direction.UP
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import kotlin.random.Random

object BenchmarkDefaults {
    const val TIMEOUT = 5_000L
    const val PACKAGE_NAME = "xyz.izadi.adibide"
}

fun UiDevice.flingElement(
    element: UiObject2,
    directions: List<Direction> = listOf(DOWN, RIGHT, UP, LEFT)
) {
    // Set some margin from the sides to prevent triggering system navigation
    element.setGestureMargin(displayWidth / 10)

    directions.forEach {
        element.fling(it)
        waitForIdle()
    }
}

fun UiDevice.dragElement(
    element: UiObject2,
    maxOffset: Point,
    numberOfDrags: Int = 10
) {
    with(element) {
        repeat(numberOfDrags) {
            drag(
                Point(
                    Random.nextInt(from = maxOffset.x / 4, until = maxOffset.x),
                    Random.nextInt(from = maxOffset.y / 4, until = maxOffset.y),
                )
            )
            waitForIdle()
        }
    }
}

fun UiDevice.selectRandomElements(
    selector: BySelector,
    numberOfSelects: Int = 10
) {
    repeat(numberOfSelects) {
        val elements = findObjects(selector)
        // sometimes the elements become stale (?)
        runCatching { elements[Random.nextInt(until = elements.size)].click() }
        waitForIdle()
    }
}
