package xyz.izadi.core.designsystem.layout

import android.util.Size
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

/**
 * attempt to write a custom lazy layout to manage big boards, discarded in favour of
 * com.github.chihsuanwu.compose-free-scroll due to time constraints
 * */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyBoardLayout(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    state: LazyBoardLayoutState = rememberLazyBoardLayoutState(),
    content: LazyBoardListScope.() -> Unit
) {
    val cellProvider = rememberCellProvider(content)
    val density = LocalDensity.current

    val contentOffset = remember(contentPadding) {
        with(density) {
            ContentOffset(
                top = contentPadding.calculateTopPadding().toPx().toInt(),
                bottom = contentPadding.calculateBottomPadding().toPx().toInt(),
                start = contentPadding.calculateStartPadding(LayoutDirection.Ltr).toPx().toInt(),
                end = contentPadding.calculateEndPadding(LayoutDirection.Ltr).toPx().toInt()

            )
        }
    }

    LazyLayout(
        modifier = modifier
            .clipToBounds()
            .lazyLayoutPointerInput(state),
        itemProvider = cellProvider,
    ) { constraints ->
        val boundaries = state.getBoundaries(constraints)
        val cellSize = measure(0, Constraints()).first().let {
            Size(it.width, it.height)
        }
        state.setMaxOffset(cellSize, cellProvider.itemCount, constraints, contentOffset)
        val indexes = cellProvider.getItemIndexesInRange(boundaries, cellSize)
        val indexesWithPlaceables = indexes.associateWith {
            measure(it, Constraints())
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            indexesWithPlaceables.forEach { (index, placeables) ->
                val cell = cellProvider.getCell(index)
                cell?.let {
                    placeCell(
                        state = state,
                        cell = cell,
                        placeables = placeables,
                        offset = contentOffset
                    )
                }
            }
        }
    }
}

data class ContentOffset(
    val top: Int,
    val bottom: Int,
    val start: Int,
    val end: Int
) {
    val horizontal = start + end
    val vertical = top + bottom
}

data class Cell(
    val position: Pair<Int, Int>
)

data class LazyBoardLayoutCell(
    val cell: Cell,
    val cellContent: ComposableCellContent
)

interface LazyBoardListScope {
    fun cells(cells: List<Cell>, cellContent: ComposableCellContent)
}

typealias ComposableCellContent = @Composable (Cell) -> Unit

@OptIn(ExperimentalFoundationApi::class)
class CellProvider(
    private val cellsState: State<List<LazyBoardLayoutCell>>
) : LazyLayoutItemProvider {

    override val itemCount
        get() = cellsState.value.size

    @Composable
    override fun Item(index: Int) {
        val item = cellsState.value.getOrNull(index)
        item?.cellContent?.invoke(item.cell)
    }

    fun getItemIndexesInRange(boundaries: ViewBoundaries, cellSize: Size): List<Int> {
        val result = mutableListOf<Int>()

        cellsState.value.forEachIndexed { index, cellContent ->
            val cell = cellContent.cell
            if (cell.position.first * cellSize.width in boundaries.fromX..boundaries.toX &&
                cell.position.second * cellSize.height in boundaries.fromY..boundaries.toY
            ) {
                result.add(index)
            }
        }

        return result
    }

    fun getCell(index: Int): Cell? {
        return cellsState.value.getOrNull(index)?.cell
    }
}

class LazyBoardListScopeImpl : LazyBoardListScope {
    private val _items = mutableListOf<LazyBoardLayoutCell>()
    val items: List<LazyBoardLayoutCell> = _items

    override fun cells(cells: List<Cell>, cellContent: ComposableCellContent) {
        cells.forEach { _items.add(LazyBoardLayoutCell(it, cellContent)) }
    }
}

@Composable
fun rememberCellProvider(
    lazyBoardListScope: LazyBoardListScope.() -> Unit,
): CellProvider {
    val customLazyListScopeState = remember { mutableStateOf(lazyBoardListScope) }.apply {
        value = lazyBoardListScope
    }

    return remember {
        CellProvider(
            derivedStateOf {
                val layoutScope = LazyBoardListScopeImpl().apply(customLazyListScopeState.value)
                layoutScope.items
            }
        )
    }
}

@Composable
fun rememberLazyBoardLayoutState(): LazyBoardLayoutState {
    return remember { LazyBoardLayoutState() }
}

data class ViewBoundaries(
    val fromX: Int,
    val toX: Int,
    val fromY: Int,
    val toY: Int
)

@Stable
class LazyBoardLayoutState {
    private val _offsetState = mutableStateOf(IntOffset(0, 0))
    val offsetState = _offsetState

    private var maxOffset = IntOffset(0, 0)

    fun setMaxOffset(
        cellSize: Size,
        itemCount: Int,
        constraints: Constraints,
        offset: ContentOffset
    ) {
        val boardSize = sqrt(itemCount.toDouble()).toInt()

        maxOffset = IntOffset(
            x = cellSize.width * boardSize - (constraints.maxWidth) + offset.horizontal,
            y = cellSize.height * boardSize - (constraints.maxHeight) + offset.vertical
        )
    }

    fun onDrag(offset: IntOffset) {
        val x = minOf(
            maxOffset.x,
            (_offsetState.value.x - offset.x).coerceAtLeast(0)
        )
        val y = minOf(
            maxOffset.y,
            (_offsetState.value.y - offset.y).coerceAtLeast(0)
        )

        _offsetState.value = IntOffset(x, y)
    }

    fun getBoundaries(
        constraints: Constraints,
        threshold: Int = 500
    ): ViewBoundaries {
        return ViewBoundaries(
            fromX = offsetState.value.x - threshold,
            toX = constraints.maxWidth + offsetState.value.x + threshold,
            fromY = offsetState.value.y - threshold,
            toY = constraints.maxHeight + offsetState.value.y + threshold
        )
    }
}

private fun Modifier.lazyLayoutPointerInput(
    state: LazyBoardLayoutState
): Modifier {
    return pointerInput(Unit) {
        detectDragGestures { change, dragAmount ->
            change.consume()
            state.onDrag(IntOffset(dragAmount.x.toInt(), dragAmount.y.toInt()))
        }
    }
}

private fun Placeable.PlacementScope.placeCell(
    state: LazyBoardLayoutState,
    cell: Cell,
    offset: ContentOffset,
    placeables: List<Placeable>
) {
    val cellSize = placeables.first().let {
        Size(it.width, it.height)
    }
    val xPosition =
        cell.position.first * cellSize.width - state.offsetState.value.x + offset.start
    val yPosition =
        cell.position.second * cellSize.height - state.offsetState.value.y + offset.top

    placeables.forEach { placeable ->
        placeable.placeRelative(xPosition, yPosition)
    }
}
