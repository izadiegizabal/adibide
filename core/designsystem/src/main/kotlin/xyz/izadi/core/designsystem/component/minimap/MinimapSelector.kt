package xyz.izadi.core.designsystem.component.minimap

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset

@Composable
internal fun MinimapSelector(
    offset: IntOffset,
    borderStroke: BorderStroke,
    size: DpSize,
    minimapState: MinimapState
) {
    Box(
        modifier = Modifier
            .offset { offset }
            .border(
                border = borderStroke,
                shape = MinimapDefaults.selectorShape
            )
            .size(size)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        minimapState.setBeingUsed(true)
                    },
                    onDragEnd = {
                        minimapState.setBeingUsed(false)
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        minimapState.changeOffset(dragAmount)
                    }
                )
            }
    ) {}
}
