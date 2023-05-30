package xyz.izadi.adibide.feature.fibonacci.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import xyz.izadi.adibide.feature.fibonacci.FibonacciRoute

const val fibonacciNavigationRoute = "fibonacci"

fun NavController.navigateToFibonacci(navOptions: NavOptions? = null) {
    this.navigate(fibonacciNavigationRoute, navOptions)
}

fun NavGraphBuilder.fibonacciScreen() {
    composable(
        route = fibonacciNavigationRoute
    ) {
        FibonacciRoute()
    }
}
