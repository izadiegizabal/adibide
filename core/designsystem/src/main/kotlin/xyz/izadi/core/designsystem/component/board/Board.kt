package xyz.izadi.core.designsystem.component.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.testTag
import com.chihsuanwu.freescroll.freeScroll
import xyz.izadi.core.designsystem.theme.Elevation

@Composable
fun Board(
    modifier: Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(Elevation.Container),
    state: BoardState,
    minimap: @Composable BoxScope.(MinimapState) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val boardTestTag = "board"

    Box(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                state.minimapState.setBoardContainerSize(coordinates.size)
                state.setLoadContent(true)
            }
            .background(containerColor)
    ) {
        if (state.loadContent.value) {
            Column(
                modifier = Modifier
                    .freeScroll(state.freeScrollState)
                    .onGloballyPositioned { coordinates ->
                        state.minimapState.setBoardSize(coordinates.size)
                    }
                    .testTag(boardTestTag)
            ) {
                content()
            }
        }
        minimap(state.minimapState)
    }
}
