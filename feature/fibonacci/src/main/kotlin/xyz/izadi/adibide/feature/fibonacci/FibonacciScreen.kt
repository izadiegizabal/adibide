package xyz.izadi.adibide.feature.fibonacci

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xyz.izadi.adibide.feature.fibonacci.ui.FibonacciBoard
import xyz.izadi.adibide.feature.fibonacci.ui.FibonacciScreenTopAppBar

@Composable
internal fun FibonacciRoute(
    viewModel: FibonacciViewModel = hiltViewModel(),
) {
    val boardState = viewModel.boardState.collectAsStateWithLifecycle()
    val fibonacciUIState = rememberFibonacciUIState()

    FibonacciScreen(
        boardState = boardState.value,
        fibonacciUIState = fibonacciUIState,
        onCellSelected = viewModel::selectCell,
        onRestart = viewModel::restart
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FibonacciScreen(
    boardState: BoardState,
    fibonacciUIState: FibonacciUIState,
    onCellSelected: (Pair<Int, Int>) -> Unit,
    onRestart: () -> Unit
) {
    Scaffold(
        topBar = {
            FibonacciScreenTopAppBar(
                onTitleClick = fibonacciUIState::resetBoardPosition,
                score = boardState.score,
                onRestart = onRestart,
                tonalElevation = fibonacciUIState.appBarElevation
            )
        }
    ) { padding ->
        FibonacciBoard(
            modifier = Modifier.consumeWindowInsets(padding),
            padding = padding,
            state = fibonacciUIState,
            boardState = boardState,
            onCellSelected = onCellSelected,
        )
    }
}
