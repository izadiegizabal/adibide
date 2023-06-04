package xyz.izadi.core.designsystem.component.board

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun Minimap(
    modifier: Modifier,
    state: MinimapState,
    content: @Composable () -> Unit
) {
    val minimapTestTag = "minimap"
    Surface(
        modifier = modifier.size(state.minimapSize).testTag(minimapTestTag),
        color = MinimapDefaults.containerColor,
        shape = MinimapDefaults.containerShape,
        border = state.border
    ) {
        Box {
            content()
            AnimatedVisibility(visible = state.selectorSize.value != null) {
                state.selectorSize.value?.let { selectorSize ->
                    MinimapSelector(
                        offset = state.selectorOffset.value,
                        borderStroke = state.border,
                        size = selectorSize,
                        minimapState = state
                    )
                }
            }

        }
    }
}
