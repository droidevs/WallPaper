package io.droidevs.wallpaper.ui.nav

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberMultiNavigationAppState(
    startDestination: AppDestination,
    navController: NavHostController = rememberNavController()
) = remember(navController, startDestination) {
    MultiNavigationAppState(navController, startDestination)
}

class MultiNavigationAppState(
    var navController: NavHostController ,
    val startDestination: AppDestination,
) {

    fun setNavController(_navController: NavHostController) {
        navController = _navController
    }

    val currentDestination: AppDestination?
        get() = navController.currentBackStackEntry?.destination?.route?.let { route ->
            AppDestinations.allDestinations.find { it.route == route }
        }

    fun navigateTo(destination: AppDestination, popUpTo: Boolean = false, inclusive: Boolean = false) {
        navController.navigate(destination) {
            if (popUpTo) {
                popUpTo(startDestination) {
                    this.inclusive = inclusive
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateBack() {
        if (navController.popBackStack().not()) {
            navController.navigate(startDestination) {
                launchSingleTop = true
            }
        }
    }

    @SuppressLint("RestrictedApi")
    fun printBackStack() {
        println("------- Navigation BackStack -------")
        navController.currentBackStack.value.forEach {
            println("Screen: ${it.destination.route}")
        }
        println("------------------------------------")
    }

    @Composable
    fun isRouteActive(route: String): Boolean {
        var navHostController = navController

        return if (navHostController != null) {
            val destination = navHostController.getDestination()
            return destination?.any {
                (it.route.equals(route))
            } ?: false
        } else {
            false
        }
    }

}
@Composable
fun NavHostController.getDestination() : Sequence<NavDestination>? {
    val navBackStackEntry by this.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    return currentDestination?.hierarchy
}

/*
data class MultiNavigationStates(
    var rootNavigation: MultiNavigationAppState = MultiNavigationAppState(),

    var appNavigation: MultiNavigationAppState = MultiNavigationAppState(),
    var homeNavigation: MultiNavigationAppState = MultiNavigationAppState(),
)

lateinit var LocalNavigationState: MultiNavigationStates
 */