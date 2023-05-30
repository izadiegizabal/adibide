package xyz.izadi.adibide.feature.fibonacci

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chihsuanwu.freescroll.FreeScrollState
import com.chihsuanwu.freescroll.rememberFreeScrollState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FibonacciUIState(
    val freeScrollState: FreeScrollState,
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
            if (isVerticallyScrolled) 16.dp else 2.dp,
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
    scope: CoroutineScope = rememberCoroutineScope()
) = remember(
    freeScrollState,
    scope
) {
    FibonacciUIState(
        freeScrollState = freeScrollState,
        scope = scope
    )
}
