package xyz.izadi.core.designsystem.component.board

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
import xyz.izadi.core.designsystem.utils.plusWithLimits
import xyz.izadi.core.designsystem.utils.setWithLimits
import xyz.izadi.core.designsystem.utils.toDpSize
import xyz.izadi.core.designsystem.utils.toIntOffset

class MinimapState(
    private val localDensity: Density,
    private val connectedScrollState: FreeScrollState,
    private val scope: CoroutineScope,
    val minimapSize: Dp
) {
    private var boardContainerSize: DpSize? = null
    private var boardSize: DpSize? = null

    private var limit: IntOffset = IntOffset.Zero

    private val _selectorOffset: MutableState<IntOffset> = mutableStateOf(IntOffset.Zero)
    val selectorOffset: State<IntOffset> = _selectorOffset

    private val _selectorSize: MutableState<DpSize?> = mutableStateOf(null)
    val selectorSize: State<DpSize?> = _selectorSize

    private val beingUsed: MutableState<Boolean> = mutableStateOf(false)

    private val borderWidth: Dp
        @Composable get() = animateDpAsState(
            if (beingUsed.value) {
                MinimapDefaults.FocusedBorderWidth
            } else {
                MinimapDefaults.BorderWidth
            },
            label = "minimap border width"
        ).value

    private val borderColor: Color
        @Composable get() = animateColorAsState(
            if (beingUsed.value) {
                MaterialTheme.colorScheme.tertiary
            } else {
                MaterialTheme.colorScheme.outline
            },
            label = "minimap border color"
        ).value

    val border: BorderStroke
        @Composable get() = BorderStroke(
            width = borderWidth,
            color = borderColor
        )

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

    /**
     * sets the value of [beingUsed] to see if the minimap is being actively used
     * @param using state of being used
     * */
    fun setBeingUsed(using: Boolean) {
        beingUsed.value = using
    }

    /**
     * set the size of the visible part or the container of the view we are trying to create a minimap for
     * @param size size of the container
     **/
    fun setBoardContainerSize(size: IntSize) {
        boardContainerSize = size.toDpSize(localDensity)
        onUpdateSize()
    }

    /**
     * set the size of the total view we are trying to create a minimap for
     * @param size size of the container
     **/
    fun setBoardSize(size: IntSize) {
        boardSize = size.toDpSize(localDensity)
        onUpdateSize()
    }

    /**
     * method to update the offset of the selector, and scroll the connected view proportionally
     * @param offsetChange - change in the offset from the previous position
     **/
    fun changeOffset(offsetChange: Offset) {
        _selectorOffset.value = selectorOffset.value.plusWithLimits(
            other = offsetChange.toIntOffset(),
            limit = limit
        )
        scope.launch {
            with(localDensity) {
                boardSize?.let { board ->
                    connectedScrollState.scrollTo(
                        x = (board.width.roundToPx() * selectorOffset.value.x) / minimapSize.roundToPx(),
                        y = (board.height.roundToPx() * selectorOffset.value.y) / minimapSize.roundToPx(),
                    )
                }
            }
        }
    }

    /**
     * method that updates the location of the selector based on the changes of position of the connected view
     * @param newX new horizontal offset of the connected view. if null, it will use the existing value
     * @param newY new vertical offset of the connected view. if null, it will use the existing value
     **/
    private fun onConnectionOffsetChanged(newX: Int? = null, newY: Int? = null) {
        val updatedOffset = _selectorOffset.value.let {
            with(localDensity) {
                IntOffset(
                    x = newX?.let {
                        (minimapSize.roundToPx() * newX) / (boardSize?.width?.roundToPx() ?: 1)
                    } ?: it.x,
                    y = newY?.let {
                        (minimapSize.roundToPx() * newY) / (boardSize?.height?.roundToPx() ?: 1)
                    } ?: it.y
                )
            }
        }

        _selectorOffset.value = selectorOffset.value.setWithLimits(
            other = updatedOffset,
            limit = limit
        )
    }

    /**
     * update the sizes of the elements dependant on the size of other elements
     **/
    private fun onUpdateSize() {
        _selectorSize.value = getSelectorSize()
        limit = getLimit()
    }

    /**
     * method that calculates the size of the selector (the draggable element)
     **/
    private fun getSelectorSize(): DpSize? = boardContainerSize?.let { container ->
        boardSize?.let { board ->
            DpSize(
                width = minimapSize * (container.width / board.width),
                height = minimapSize * (container.height / board.height)
            )
        }
    }

    /**
     * method that calculates the maximum offset that the selector can be dragged up until
     **/
    private fun getLimit(): IntOffset = getSelectorSize()?.let { selector ->
        with(localDensity) {
            IntOffset(
                x = (minimapSize - selector.width).roundToPx(),
                y = (minimapSize - selector.height).roundToPx()
            )
        }
    } ?: IntOffset.Zero
}

@Composable
fun rememberMinimapState(
    localDensity: Density = LocalDensity.current,
    freeScrollState: FreeScrollState,
    scope: CoroutineScope = rememberCoroutineScope(),
    size: Dp = MinimapDefaults.ContainerSize
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
        minimapSize = size
    )
}
