package io.droidevs.wallpaper.ui.nav

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController


@Composable
fun DashboardNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState? = null,
) {
    val appstate = rememberMultiNavigationAppState(
        navController = rememberNavController(),
        startDestination = Screen.Home
    )
    NavHost(
        navController = appstate.navController,
        startDestination = appstate.startDestination
    ) {
        dashboardNavGraph(appstate)
    }
}