package xyz.izadi.core.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

object ButtonDefaults {
    val IconButtonPadding = PaddingValues(0.dp)
    val iconButtonShape: Shape
        @Composable get() = MaterialTheme.shapes.small

    @Composable
    fun iconButtonColors(
        containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer
    ): ButtonColors = ButtonDefaults.filledTonalButtonColors(
        containerColor = containerColor
    )

}
