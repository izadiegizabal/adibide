package xyz.izadi.adibide.feature.fibonacci.ui

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import xyz.adibide.feature.fibonacci.R
import xyz.izadi.core.designsystem.component.text.ScreenTitle

@Composable
internal fun FibonacciScreenTitle(
    onClick: () -> Unit
) {
    ScreenTitle(
        modifier = Modifier.clickable(onClick = onClick),
        text = stringResource(R.string.fibonacci_h_fibonacci)
    )
}
