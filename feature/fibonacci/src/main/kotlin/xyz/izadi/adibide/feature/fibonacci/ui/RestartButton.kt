package xyz.izadi.adibide.feature.fibonacci.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.izadi.core.designsystem.component.button.IconButton

@Composable
internal fun RestartButton(
    modifier: Modifier = Modifier,
    onRestart: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onRestart,
        icon = Icons.TwoTone.Refresh
    )
}
