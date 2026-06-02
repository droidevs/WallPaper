package io.droidevs.wallpaper.domain.preferences

import io.droidevs.wallpaper.domain.model.WallpaperEffects
import io.droidevs.wallpaper.domain.model.Screen
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow

interface WallpaperEffectsPreference {

    val effect : Flow<Result<WallpaperEffects, PreferenceError>>
    suspend fun saveEffect(effect: WallpaperEffects) : Result<WallpaperEffects, PreferenceError>

    suspend fun setBlurValue(screen : Screen, value: Float) : Result<WallpaperEffects, PreferenceError>
    suspend fun setBrightnessValue(screen : Screen, value: Float) : Result<WallpaperEffects, PreferenceError>
    suspend fun setContrastValue(screen : Screen, value: Float) : Result<WallpaperEffects, PreferenceError>
    suspend fun setSaturationValue(screen : Screen, value: Float) : Result<WallpaperEffects, PreferenceError>
    suspend fun setHueRedValue(screen : Screen, value: Float) : Result<WallpaperEffects, PreferenceError>
    suspend fun setHueGreenValue(screen : Screen, value: Float) : Result<WallpaperEffects, PreferenceError>
    suspend fun setHueBlueValue(screen : Screen, value: Float) : Result<WallpaperEffects, PreferenceError>
    suspend fun setScaleRedValue(screen : Screen, value: Float) : Result<WallpaperEffects, PreferenceError>
    suspend fun setScaleGreenValue(screen : Screen, value: Float) : Result<WallpaperEffects, PreferenceError>
    suspend fun setScaleBlueValue(screen : Screen, value: Float) : Result<WallpaperEffects, PreferenceError>
}
