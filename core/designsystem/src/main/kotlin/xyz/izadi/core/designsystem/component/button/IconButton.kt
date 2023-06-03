package xyz.izadi.core.designsystem.component.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.iconButtonColors(),
    icon: ImageVector,
    iconContentDescription: String = icon.name
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = ButtonDefaults.IconButtonPadding,
        shape = ButtonDefaults.iconButtonShape,
        colors = colors
    ) {
        Icon(imageVector = Icons.TwoTone.Refresh, contentDescription = iconContentDescription)
    }
}
