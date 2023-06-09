package xyz.izadi.adibide.feature.fibonacci

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.Dp
import com.chihsuanwu.freescroll.FreeScrollState
import com.chihsuanwu.freescroll.rememberFreeScrollState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import xyz.izadi.core.designsystem.component.board.MinimapState
import xyz.izadi.core.designsystem.component.board.rememberMinimapState
import xyz.izadi.core.designsystem.theme.Elevation

class FibonacciUIState(
    val freeScrollState: FreeScrollState,
    val minimapState: MinimapState,
    private val scope: CoroutineScope
) {
    private val isVerticallyScrolled: Boolean
        @Composable get() = remember(this) {
            derivedStateOf {
                freeScrollState.verticalScrollState.value > 0
            }
        }.value

    val appBarElevation: Dp
        @Composable get() = animateDpAsState(
            if (isVerticallyScrolled) Elevation.ContainerHigh else Elevation.Container,
            label = "appbar tonal elevation"
        ).value

    fun resetBoardPosition() {
        scope.launch {
            freeScrollState.animateScrollTo(0, 0)
        }
    }
}

@Composable
fun rememberFibonacciUIState(
    freeScrollState: FreeScrollState = rememberFreeScrollState(),
    scope: CoroutineScope = rememberCoroutineScope(),
    minimapState: MinimapState = rememberMinimapState(
        freeScrollState = freeScrollState,
        scope = scope
    ),
) = remember(
    freeScrollState,
    minimapState,
    scope
) {
    FibonacciUIState(
        freeScrollState = freeScrollState,
        minimapState = minimapState,
        scope = scope
    )
}
