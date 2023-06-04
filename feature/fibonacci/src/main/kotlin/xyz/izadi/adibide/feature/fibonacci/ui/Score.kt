package xyz.izadi.adibide.feature.fibonacci.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import xyz.adibide.feature.fibonacci.R
import xyz.izadi.core.designsystem.theme.Elevation

@Composable
fun Score(
    modifier: Modifier = Modifier,
    score: Double
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        tonalElevation = Elevation.ContainerLowest
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.fibonacci_h_score).uppercase(),
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = String.format("%.0f", score),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
