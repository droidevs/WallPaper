package io.droidevs.wallpaper.models

import kotlinx.serialization.Serializable

@Serializable
data class WallpaperEffects(
    val homeEffect: Effect = Effect(),
    val lockEffect: Effect = Effect()
)