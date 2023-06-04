package xyz.izadi.adibide.feature.fibonacci.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import xyz.adibide.feature.fibonacci.R
import xyz.izadi.core.designsystem.component.button.IconButton

@Composable
internal fun RestartButton(
    modifier: Modifier = Modifier,
    onRestart: () -> Unit
) {
    val testTag = "restartButton"
    IconButton(
        modifier = modifier.testTag(testTag),
        onClick = onRestart,
        icon = Icons.TwoTone.Refresh,
        iconContentDescription = stringResource(R.string.fibonacci_btn_cd_restart)
    )
}
