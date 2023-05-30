package xyz.izadi.adibide.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.izadi.adibide.navigation.AdibideNavHost

@Composable
fun AdibideApp(
    appState: AdibideAppState = rememberAdibideAppState(),
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AdibideNavHost(appState = appState)
    }
}
