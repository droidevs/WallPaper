package io.droidevs.wallpaper.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WallpaperEffects(
    val homeEffect: Effect = Effect(),
    val lockEffect: Effect = Effect()
)