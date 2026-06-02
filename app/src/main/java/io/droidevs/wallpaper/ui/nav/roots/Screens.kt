package io.droidevs.wallpaper.ui.nav.roots

import io.droidevs.wallpaper.data.model.SearchScreenType
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(override val route: String) : Destination {

    object Home : Screen("home")

    @Serializable
    object WallpaperList : Screen("wallpaper_list")

    object Wallpaper : Screen("wallpaper")

    @Serializable
    object Albums : Screen("albums")

    object CollectionList : Screen("collection_list")

    @Serializable
    data class Album(
        val albumId: Long
    ) : Screen("album")

    data class AlbumEdit(
        val albumId: Long
    ) : Screen("album_edit")

    data class Search(
        val query: String,
        val screenType: SearchScreenType
    ) : Screen("search")

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