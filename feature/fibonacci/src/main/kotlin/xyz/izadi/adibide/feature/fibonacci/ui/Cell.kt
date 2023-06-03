package xyz.izadi.adibide.feature.fibonacci.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.izadi.core.designsystem.theme.Elevation

@Composable
internal fun Cell(
    value: Double,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(48.dp)
            .padding(2.dp),
        shape = MaterialTheme.shapes.extraSmall,
        onClick = onClick,
        tonalElevation = Elevation.ContainerLowest
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = String.format("%.0f", value), style = MaterialTheme.typography.bodySmall)
        }
    }
}
