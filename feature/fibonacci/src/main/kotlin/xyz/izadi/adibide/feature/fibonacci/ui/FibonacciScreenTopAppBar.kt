package xyz.izadi.adibide.feature.fibonacci.ui

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FibonacciScreenTopAppBar(
    onTitleClick: () -> Unit,
    score: Double,
    onRestart: () -> Unit,
    tonalElevation: Dp
) {
    TopAppBar(
        modifier = Modifier.height(IntrinsicSize.Min),
        title = {
            Row(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .padding(vertical = 8.dp)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FibonacciScreenTitle(onClick = onTitleClick)
                Spacer(modifier = Modifier.width(16.dp))
                Score(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    score = score
                )
                Spacer(modifier = Modifier.width(8.dp))
                RestartButton(
                    modifier = Modifier.fillMaxHeight(),
                    onRestart = onRestart
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(tonalElevation)
        )
    )
}
