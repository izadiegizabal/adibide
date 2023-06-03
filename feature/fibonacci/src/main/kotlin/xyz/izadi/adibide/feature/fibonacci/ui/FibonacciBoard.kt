package xyz.izadi.adibide.feature.fibonacci.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.izadi.adibide.feature.fibonacci.BoardState
import xyz.izadi.adibide.feature.fibonacci.FibonacciUIState
import xyz.izadi.core.designsystem.component.board.Board
import xyz.izadi.core.designsystem.component.board.Minimap
import xyz.izadi.core.designsystem.theme.Elevation

@Composable
internal fun FibonacciBoard(
    modifier: Modifier,
    padding: PaddingValues,
    state: FibonacciUIState,
    boardState: BoardState,
    onCellSelected: (Pair<Int, Int>) -> Unit
) {
    Board(
        modifier = modifier.padding(top = padding.calculateTopPadding()),
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(Elevation.Container),
        freeScrollState = state.freeScrollState,
        minimapState = state.minimapState,
        minimap = { minimapState ->
            Minimap(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        bottom = padding.calculateBottomPadding() + 16.dp,
                        start = 16.dp
                    ),
                state = minimapState
            ) {
                BoardMinimapContent()
            }
        }
    ) {
        boardState.board.forEachIndexed { x, row ->
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                row.forEachIndexed { y, value ->
                    Cell(value) {
                        onCellSelected(x to y)
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier
                .padding(bottom = 32.dp + state.minimapState.minimapSize)
                .height(padding.calculateBottomPadding())
        )
    }
}
