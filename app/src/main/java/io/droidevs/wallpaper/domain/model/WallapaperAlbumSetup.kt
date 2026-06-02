package io.droidevs.wallpaper.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WallpaperAlbumSetup(
    val albumId: String,
    val intervalMinutes: Int = 60, // for auto mode
    val autoModeEnabled: Boolean = false
)