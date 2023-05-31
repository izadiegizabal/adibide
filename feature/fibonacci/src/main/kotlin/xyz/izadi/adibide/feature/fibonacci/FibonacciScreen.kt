package xyz.izadi.adibide.feature.fibonacci

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FibonacciScreen(
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
        Box(
            modifier = Modifier
                .consumeWindowInsets(padding)
                .padding(top = padding.calculateTopPadding())
                .onGloballyPositioned { coordinates ->
                    fibonacciUIState.minimapState.setBoardContainerSize(coordinates)
                }
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
                    .freeScroll(fibonacciUIState.freeScrollState)
                    .onGloballyPositioned { coordinates ->
                        fibonacciUIState.minimapState.setBoardSize(coordinates)
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
                        .padding(bottom = 16.dp + 128.dp + 16.dp)
                        .height(padding.calculateBottomPadding())
                )
            }
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = padding.calculateBottomPadding() + 16.dp, start = 16.dp)
                    .size(128.dp),
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.85f),
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Box {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        repeat(50) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(0.75.dp)
                                    .background(color = MaterialTheme.colorScheme.surface)
                            ) {}
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        repeat(50) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(0.75.dp)
                                    .background(color = MaterialTheme.colorScheme.surface)
                            ) {}
                        }
                    }
                    AnimatedVisibility(visible = fibonacciUIState.minimapState.selectorSize.value != null) {
                        fibonacciUIState.minimapState.selectorSize.value?.let { selectorSize ->
                            Box(
                                modifier = Modifier
                                    .offset { fibonacciUIState.minimapState.selectorOffset.value }
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.tertiary,
                                        shape = MaterialTheme.shapes.extraSmall
                                    )
                                    .size(selectorSize)
                                    .pointerInput(Unit) {
                                        detectDragGestures { change, dragAmount ->
                                            change.consume()
                                            fibonacciUIState.minimapState.changeOffset(dragAmount)
                                        }
                                    }
                            ) {}
                        }
                    }

                }
            }
        }
    }
}

@Stable
fun IntOffset.plusWithLimits(
    other: IntOffset,
    limit: IntOffset
) = IntOffset(
    x = when {
        x + other.x < 0 -> 0
        x + other.x > limit.x -> limit.x
        else -> x + other.x
    },
    y = when {
        y + other.y < 0 -> 0
        y + other.y > limit.y -> limit.y
        else -> y + other.y
    }
)

@Stable
fun IntOffset.setWithLimits(
    other: IntOffset,
    limit: IntOffset
) = IntOffset(
    x = when {
        other.x < 0 -> 0
        other.x > limit.x -> limit.x
        else -> other.x
    },
    y = when {
        other.y < 0 -> 0
        other.y > limit.y -> limit.y
        else -> other.y
    }
)

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

@Composable
private fun RestartButton(
    modifier: Modifier = Modifier,
    onRestart: () -> Unit
) {
    Button(
        modifier = modifier,
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

@Composable
private fun ScreenTitle(
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier.clickable(onClick = onClick),
        text = "Fibonacci".uppercase(),
        style = MaterialTheme.typography.displaySmall
    )
}

@Composable
private fun Score(
    modifier: Modifier = Modifier,
    score: Double
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier,
        onClick = { /*TODO: show high score*/ }
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Score".uppercase(),
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = String.format("%.0f", score),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FibonacciScreenTopAppBar(
    onTitleClick: () -> Unit,
    score: Double,
    onRestart: () -> Unit,
    tonalElevation: Dp
) {
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
                ScreenTitle(onClick = onTitleClick)
                Spacer(modifier = Modifier.width(16.dp))
                Score(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    score = score
                )
                Spacer(modifier = Modifier.width(8.dp))
                RestartButton(
                    modifier = Modifier.fillMaxHeight(),
                    onRestart = onRestart
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(tonalElevation)
        )
    )
}
