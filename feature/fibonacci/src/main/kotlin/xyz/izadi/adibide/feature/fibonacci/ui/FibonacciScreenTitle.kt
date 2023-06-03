package xyz.izadi.adibide.feature.fibonacci.ui

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.izadi.core.designsystem.component.text.ScreenTitle

@Composable
internal fun FibonacciScreenTitle(
    onClick: () -> Unit
) {
    ScreenTitle(
        modifier = Modifier.clickable(onClick = onClick),
        text = "Fibonacci"
    )
}
