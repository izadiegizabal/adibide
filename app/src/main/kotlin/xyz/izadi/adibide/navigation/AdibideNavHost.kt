package xyz.izadi.adibide.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import xyz.izadi.adibide.feature.fibonacci.navigation.fibonacciNavigationRoute
import xyz.izadi.adibide.feature.fibonacci.navigation.fibonacciScreen
import xyz.izadi.adibide.ui.AdibideAppState

@Composable
fun AdibideNavHost(
    appState: AdibideAppState,
    modifier: Modifier = Modifier,
    startDestination: String = fibonacciNavigationRoute
) {
    NavHost(
        navController = appState.navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        fibonacciScreen()
    }
}
