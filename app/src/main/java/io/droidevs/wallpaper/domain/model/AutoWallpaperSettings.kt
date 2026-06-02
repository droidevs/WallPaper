package io.droidevs.wallpaper.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AutoWallpaperSettings(
    val homeScreen: WallpaperAlbumSetup? = null,
    val lockScreen: WallpaperAlbumSetup? = null,
)