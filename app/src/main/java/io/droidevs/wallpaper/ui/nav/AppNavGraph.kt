package io.droidevs.wallpaper.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavGraph(navController: NavHostController){
    val appState = remember {
        MultiNavigationAppState(
            navController = navController,
            startDestination = Graph.Dashboard)
    } // Create an instance

    NavHost (
        navController = navController,
        startDestination = Graph.App

    ) {
        appNavGraph(appState) // Pass the appState here
    }
}