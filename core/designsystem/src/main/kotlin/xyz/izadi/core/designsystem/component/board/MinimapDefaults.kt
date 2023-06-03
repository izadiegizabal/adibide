package xyz.izadi.core.designsystem.component.board

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object MinimapDefaults {
    val BorderWidth: Dp = 1.dp

    val FocusedBorderWidth: Dp = 2.dp

    val ContainerSize: Dp = 128.dp

    val containerColor: Color
        @Composable get() = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.85f)

    val containerShape: Shape
        @Composable get() = MaterialTheme.shapes.extraSmall

    val selectorShape: Shape
        @Composable get() = MaterialTheme.shapes.extraSmall
}
