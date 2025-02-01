package io.droidevs.wallpaper.ui.nav

import kotlinx.serialization.Serializable


interface AppDestination {
    val route: String
}

@Serializable
sealed class Screen(override val route: String) : AppDestination {

    @Serializable
    object WallpaperList : Screen("wallpaper_list")

    object Wallpaper : Screen("wallpaper")

    @Serializable
    object Albums : Screen("albums")

    @Serializable
    object Album : Screen("album")

    @Serializable
    object LiveWallpapers : Screen("live_wallpapers")

    @Serializable
    object AutoWallpaper : Screen("auto_wallpaper")

    @Serializable
    object Licences : Screen("licences")

    @Serializable
    object Settings : Screen("settings")

    @Serializable
    object StartUp : Screen("start_up")

    @Serializable
    object Privacy : Screen("privacy")

    @Serializable
    object SetUp : Screen("set_up")

    @Serializable
    object About : Screen("about")

    companion object {
        val allScreens = listOf(
            WallpaperList, Albums, Album, LiveWallpapers, AutoWallpaper,
            Licences, Settings, StartUp, Privacy, SetUp, About
        )
    }
}

@Serializable
open class Graph(override val route: String) : AppDestination {

    @Serializable
    object App : Graph("app")

    @Serializable
    object Home : Graph("home")

    @Serializable
    object Wallpapers : Graph("wallpapers")

    @Serializable
    object Albums : Graph("albums")

    companion object {
        val allGraphs = listOf(App, Home, Wallpapers, Albums)
    }
}

object AppDestinations {
    val allDestinations = Screen.allScreens + Graph.allGraphs
}
