package xyz.izadi.core.designsystem.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.math.roundToInt

fun Offset.toIntOffset(): IntOffset = IntOffset(
    x = x.roundToInt(),
    y = y.roundToInt()
)

fun IntSize.toDpSize(density: Density): DpSize {
    with(density) {
        return DpSize(
            width = width.toDp(),
            height = height.toDp()
        )
    }
}

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
