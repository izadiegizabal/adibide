package xyz.izadi.adibide.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import xyz.izadi.adibide.navigation.AdibideNavHost

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AdibideApp(
    appState: AdibideAppState = rememberAdibideAppState(),
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .semantics {
                testTagsAsResourceId = true
            },
        color = MaterialTheme.colorScheme.background
    ) {
        AdibideNavHost(appState = appState)
    }
}
