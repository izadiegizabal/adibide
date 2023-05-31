package xyz.izadi.adibide.feature.fibonacci

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.chihsuanwu.freescroll.FreeScrollState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MinimapState(
    private val localDensity: Density,
    private val connectedScrollState: FreeScrollState,
    private val scope: CoroutineScope,
    private val size: Dp
) {
    private var boardContainerSize: DpSize? = null
    private var boardSize: DpSize? = null

    private var limit: IntOffset = IntOffset.Zero

    private val _selectorOffset: MutableState<IntOffset> = mutableStateOf(IntOffset.Zero)
    val selectorOffset: State<IntOffset> = _selectorOffset

    private val _selectorSize: MutableState<DpSize?> = mutableStateOf(null)
    val selectorSize: State<DpSize?> = _selectorSize

    // to sync the minimap with the movements of the connection
    init {
        scope.launch {
            snapshotFlow { connectedScrollState.xValue }.collectLatest { changedConnectedX ->
                onConnectionOffsetChanged(newX = changedConnectedX)
            }
        }
        scope.launch {
            snapshotFlow { connectedScrollState.yValue }.collectLatest { changedConnectedY ->
                onConnectionOffsetChanged(newY = changedConnectedY)
            }
        }
    }

    fun setBoardContainerSize(coordinates: LayoutCoordinates) {
        boardContainerSize = coordinates.size.toDpSize(localDensity)
        onUpdateSize()
    }

    fun setBoardSize(coordinates: LayoutCoordinates) {
        boardSize = coordinates.size.toDpSize(localDensity)
        onUpdateSize()
    }

    fun changeOffset(offsetChange: Offset) {
        _selectorOffset.value = selectorOffset.value.plusWithLimits(
            other = offsetChange.toIntOffset(),
            limit = limit
        )
        scope.launch {
            with(localDensity) {
                boardSize?.let { board ->
                    connectedScrollState.scrollTo(
                        x = (board.width.roundToPx() * selectorOffset.value.x) / size.roundToPx(),
                        y = (board.height.roundToPx() * selectorOffset.value.y) / size.roundToPx(),
                    )
                }
            }
        }
    }

    private fun onConnectionOffsetChanged(newX: Int? = null, newY: Int? = null) {
        val updatedOffset = _selectorOffset.value.let {
            with(localDensity) {
                IntOffset(
                    x = newX?.let {
                        (size.roundToPx() * newX) / (boardSize?.width?.roundToPx() ?: 1)
                    } ?: it.x,
                    y = newY?.let {
                        (size.roundToPx() * newY) / (boardSize?.height?.roundToPx() ?: 1)
                    } ?: it.y
                )
            }
        }

        _selectorOffset.value = selectorOffset.value.setWithLimits(
            other = updatedOffset,
            limit = limit
        )
    }

    private fun onUpdateSize() {
        _selectorSize.value = getSelectorSize()
        limit = getLimit()
    }

    private fun getSelectorSize(): DpSize? = boardContainerSize?.let { container ->
        boardSize?.let { board ->
            DpSize(
                width = size * (container.width / board.width),
                height = size * (container.height / board.height)
            )
        }
    }

    private fun getLimit(): IntOffset = getSelectorSize()?.let { selector ->
        with(localDensity) {
            IntOffset(
                x = (size - selector.width).roundToPx(),
                y = (size - selector.height).roundToPx()
            )
        }
    } ?: IntOffset.Zero
}

private fun Offset.toIntOffset(): IntOffset = IntOffset(
    x = x.roundToInt(),
    y = y.roundToInt()
)

private fun IntSize.toDpSize(density: Density): DpSize {
    with(density) {
        return DpSize(
            width = width.toDp(),
            height = height.toDp()
        )
    }
}

@Composable
fun rememberMinimapState(
    localDensity: Density = LocalDensity.current,
    freeScrollState: FreeScrollState,
    scope: CoroutineScope,
    size: Dp
) = remember(
    localDensity,
    freeScrollState,
    scope,
    size
) {
    MinimapState(
        localDensity = localDensity,
        connectedScrollState = freeScrollState,
        scope = scope,
        size = size
    )
}
