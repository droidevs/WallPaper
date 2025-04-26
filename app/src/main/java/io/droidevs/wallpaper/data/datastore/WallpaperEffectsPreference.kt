package io.droidevs.wallpaper.data.datastore

import io.droidevs.wallpaper.models.WallpaperEffects
import io.droidevs.wallpaper.models.Screen

interface WallpaperEffectsPreference {
    suspend fun getEffect(): WallpaperEffects
    suspend fun saveEffect(effect: WallpaperEffects)

    suspend fun setBlurValue(screen : Screen, value: Float)
    suspend fun setBrightnessValue(screen : Screen, value: Float)
    suspend fun setContrastValue(screen : Screen, value: Float)
    suspend fun setSaturationValue(screen : Screen, value: Float)
    suspend fun setHueRedValue(screen : Screen, value: Float)
    suspend fun setHueGreenValue(screen : Screen, value: Float)
    suspend fun setHueBlueValue(screen : Screen, value: Float)
    suspend fun setScaleRedValue(screen : Screen, value: Float)
    suspend fun setScaleGreenValue(screen : Screen, value: Float)
    suspend fun setScaleBlueValue(screen : Screen, value: Float)
}
