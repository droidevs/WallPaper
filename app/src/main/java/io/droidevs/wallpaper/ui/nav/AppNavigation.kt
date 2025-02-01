package io.droidevs.wallpaper.ui.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import io.droidevs.wallpaper.ui.layouts.HomeDashboard


//----------Wallpapers nav graph -----------------------


fun NavGraphBuilder.appNavGraph(
    appState: MultiNavigationAppState
) {
    composable<Graph.Dashboard> {
        HomeDashboard(appState.navController)
    }
    //todo about / licences / rating / settings
}


fun NavGraphBuilder.dashboardNavGraph(
    appState: MultiNavigationAppState,
){
    navigation<Graph.Albums>(
        startDestination = appState.startDestination,
    ){
        albumListScreen()
        albumScreen()
        wallpaperScreen()
    }
}


fun NavGraphBuilder.wallpaperScreen() {
    composable<Screen.Wallpaper> {
        //todo : WallpaperScreen()
    }
}

fun NavGraphBuilder.albumListScreen(){
    composable<Screen.Albums>{
        //todo : Albums Screen
    }
}

fun NavGraphBuilder.albumScreen(){
    composable<Screen.Album>{

    }
}