package xyz.izadi.adibide.feature.fibonacci

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chihsuanwu.freescroll.freeScroll

@Composable
fun FibonacciRoute(
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

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class
)
@Composable
fun FibonacciScreen(
    boardState: BoardState,
    fibonacciUIState: FibonacciUIState,
    onCellSelected: (Pair<Int, Int>) -> Unit,
    onRestart: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(IntrinsicSize.Min),
                title = {
                    Row(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .padding(vertical = 8.dp)
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.clickable(onClick = fibonacciUIState::resetBoardPosition),
                            text = "Fibonacci".uppercase(),
                            style = MaterialTheme.typography.displaySmall
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            onClick = { /*TODO: show high score*/ }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Score".uppercase(),
                                    style = MaterialTheme.typography.labelSmall
                                )
                                Text(
                                    text = String.format("%.0f", boardState.score),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            modifier = Modifier.fillMaxHeight(),
                            onClick = onRestart,
                            contentPadding = PaddingValues(0.dp),
                            shape = MaterialTheme.shapes.small,
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        ) {
                            Icon(imageVector = Icons.TwoTone.Refresh, contentDescription = "")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                        fibonacciUIState.appBarElevation
                    )
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
                .consumeWindowInsets(padding)
                .padding(top = padding.calculateTopPadding())
                .freeScroll(fibonacciUIState.freeScrollState)
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
                    .height(padding.calculateBottomPadding())
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
fun Cell(
    value: Double,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(48.dp)
            .padding(2.dp),
        shape = MaterialTheme.shapes.extraSmall,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = String.format("%.0f", value), style = MaterialTheme.typography.bodySmall)
        }
    }
}
