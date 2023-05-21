package xyz.izadi.adibide.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Stable
class AdibideAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination
}

@Composable
fun rememberAdibideAppState(
    navController: NavHostController = rememberNavController(),
): AdibideAppState {
    return remember(
        navController
    ) {
        AdibideAppState(
            navController = navController
        )
    }
}
