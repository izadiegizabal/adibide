package xyz.izadi.feature.fibonacci

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import com.chihsuanwu.freescroll.FreeScrollState
import org.junit.Rule
import org.junit.Test
import xyz.izadi.adibide.feature.fibonacci.BoardState
import xyz.izadi.adibide.feature.fibonacci.FibonacciScreen
import xyz.izadi.adibide.feature.fibonacci.FibonacciUIState
import xyz.izadi.core.designsystem.component.board.MinimapState
import xyz.izadi.core.designsystem.theme.AdibideTheme


class FibonacciScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun fibonacciScreenIsDisplayed(): Unit = with(composeTestRule) {
        setContent {
            AdibideTheme {
                FibonacciScreen(
                    boardState = BoardState(),
                    fibonacciUIState = FibonacciUIState(
                        freeScrollState = FreeScrollState(
                            horizontalScrollState = ScrollState(0),
                            verticalScrollState = ScrollState(0)
                        ),
                        minimapState = MinimapState(
                            localDensity = LocalDensity.current,
                            scope = rememberCoroutineScope(),
                            connectedScrollState = FreeScrollState(
                                horizontalScrollState = ScrollState(0),
                                verticalScrollState = ScrollState(0)
                            ),
                            minimapSize = 128.dp
                        ),
                        scope = rememberCoroutineScope()
                    ),
                    onCellSelected = {},
                    onRestart = {}
                )
            }
        }

        onNodeWithText("FIBONACCI").apply {
            assertIsDisplayed()
            assert(hasClickAction())
        }
        onNodeWithText("SCORE").assertIsDisplayed()
        onNodeWithTag("restartButton").apply {
            assertIsDisplayed()
            assert(hasClickAction())
        }
        onNodeWithTag("minimapSelector").assertIsDisplayed()
        onNodeWithTag("board").assertIsDisplayed()
        onNodeWithTag("minimap").apply {
            assertIsDisplayed()
            assertHeightIsEqualTo(128.dp)
            assertWidthIsEqualTo(128.dp)
        }
        onAllNodesWithTag("cell").apply {
            assertAll(hasClickAction())
            assertCountEquals(50 * 50)
        }
    }
}
