package io.droidevs.wallpaper.ui.nav.navigators

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import io.droidevs.wallpaper.ui.nav.roots.Destination
import kotlinx.coroutines.flow.MutableStateFlow

open class DefaultNavigator(
    val navController: NavHostController,
    val startDestination: Destination,
) : Navigator {
    final override var currentStartDestination by mutableStateOf(startDestination)
        private set

    // Navigate to a screen while maintaining back stack
    override fun <T : Destination> navigateTo(destination: T) {
        navController.navigate(destination ?: error("Unknown destination"))
    }

    // Reset to start destination
    override fun resetToStart() {
        navController.popBackStack(
            currentStartDestination,
            inclusive = false
        )
    }

    override fun navigateUp() {
        navController.navigateUp()
    }

    // Change the active start destination (for multi-graph apps)
    fun <T : Destination> updateStartDestination(newStart: T) {
        currentStartDestination = newStart
        resetToStart()
    }
}