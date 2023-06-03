package xyz.izadi.adibide.benchmark.fibonacci

import android.graphics.Point
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import xyz.izadi.adibide.benchmark.BenchmarkDefaults.TIMEOUT
import xyz.izadi.adibide.benchmark.dragElement
import xyz.izadi.adibide.benchmark.flingElement
import xyz.izadi.adibide.benchmark.selectRandomElements

fun MacrobenchmarkScope.fibonacciWaitForContent(): Unit = with(device) {
    // wait until content is loaded by checking if the board & minimap selector are on screen
    wait(Until.findObject(By.res("board")), TIMEOUT)
    wait(Until.findObject(By.res("minimapSelector")), TIMEOUT)
}

fun MacrobenchmarkScope.fibonacciMoveBoard(): Unit = with(device) {
    val board = findObject(By.res("board"))
    flingElement(board)
}

fun MacrobenchmarkScope.fibonacciMoveMinimap(): Unit = with(device) {
    val minimapSelector = findObject(By.res("minimapSelector"))
    dragElement(
        element = minimapSelector,
        maxOffset = Point(500, 500)
    )
}

fun MacrobenchmarkScope.fibonacciSelectNumbers(): Unit = with(device) {
    selectRandomElements(By.text("0"))
}

fun MacrobenchmarkScope.fibonacciRestart(): Unit = with(device) {
    val restartButton = findObject(By.res("restartButton"))
    restartButton.click()
    waitForIdle()
}
