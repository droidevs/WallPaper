package io.droidevs.wallpaper.models

import kotlinx.serialization.Serializable

@Serializable
data class AutoWallpaperSettings(
    val homeScreen: WallpaperAlbumSetup? = null,
    val lockScreen: WallpaperAlbumSetup? = null,
)