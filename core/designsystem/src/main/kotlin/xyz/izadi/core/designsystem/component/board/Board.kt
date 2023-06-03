package xyz.izadi.core.designsystem.component.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.testTag
import com.chihsuanwu.freescroll.FreeScrollState
import com.chihsuanwu.freescroll.freeScroll
import com.chihsuanwu.freescroll.rememberFreeScrollState

@Composable
fun Board(
    modifier: Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    freeScrollState: FreeScrollState = rememberFreeScrollState(),
    minimapState: MinimapState = rememberMinimapState(
        freeScrollState = freeScrollState
    ),
    minimap: @Composable BoxScope.(MinimapState) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val boardTestTag = "board"

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                minimapState.setBoardContainerSize(coordinates.size)
            }
    ) {
        Column(
            modifier = Modifier
                .background(containerColor)
                .freeScroll(freeScrollState)
                .onGloballyPositioned { coordinates ->
                    minimapState.setBoardSize(coordinates.size)
                }
                .testTag(boardTestTag)
        ) {
            content()
        }
        minimap(minimapState)
    }
}
