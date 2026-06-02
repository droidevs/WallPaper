package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.DataStore
import io.droidevs.wallpaper.data.datastore.delegate.ProtoPreferenceDelegate
import io.droidevs.wallpaper.data.datastore.delegate.ProtoWriteDelegate
import io.droidevs.wallpaper.data.datastore.delegate.ProtoWriteDelegateImpl
import io.droidevs.wallpaper.domain.model.Effect
import io.droidevs.wallpaper.domain.preferences.WallpaperEffectsPreference
import io.droidevs.wallpaper.domain.model.WallpaperEffects
import io.droidevs.wallpaper.domain.model.Screen
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow


class WallpaperEffectsDataStore(private val dataStore: DataStore<WallpaperEffects>) : WallpaperEffectsPreference , ProtoWriteDelegate<WallpaperEffects> by ProtoWriteDelegateImpl(
    dataStore = dataStore
) {

    private suspend fun updateEffectValue(
        screen: Screen,
        update: (Effect, Float) -> Effect,
        value: Float,
    ): Result<WallpaperEffects, PreferenceError> {
        return set(
            update = { root ->
                when (screen) {
                    Screen.HOME -> root.copy(homeEffect = update(root.homeEffect, value))
                    Screen.LOCK -> root.copy(lockEffect = update(root.lockEffect, value))
                    Screen.BOTH -> root.copy(
                        homeEffect = update(root.homeEffect, value),
                        lockEffect = update(root.lockEffect, value)
                    )
                    Screen.NONE -> root
                }
            }
        )
    }

    override val effect : Flow<Result<WallpaperEffects, PreferenceError>> =
        ProtoPreferenceDelegate(dataStore, WallpaperEffects()).flow

    override suspend fun saveEffect(effect: WallpaperEffects): Result<WallpaperEffects, PreferenceError> {
        return ProtoPreferenceDelegate(dataStore, WallpaperEffects()).set(effect)
    }

    override suspend fun setBlurValue(screen: Screen, value: Float): Result<WallpaperEffects, PreferenceError> =
        updateEffectValue(screen, { e, v -> e.copy(blurValue = v) }, value)

    override suspend fun setBrightnessValue(screen: Screen, value: Float): Result<WallpaperEffects, PreferenceError> =
        updateEffectValue(screen, { e, v -> e.copy(brightnessValue = v) }, value)

    override suspend fun setContrastValue(screen: Screen, value: Float): Result<WallpaperEffects, PreferenceError> =
        updateEffectValue(screen, { e, v -> e.copy(contrastValue = v) }, value)

    override suspend fun setSaturationValue(screen: Screen, value: Float): Result<WallpaperEffects, PreferenceError> =
        updateEffectValue(screen, { e, v -> e.copy(saturationValue = v) }, value)

    override suspend fun setHueRedValue(screen: Screen, value: Float): Result<WallpaperEffects, PreferenceError> =
        updateEffectValue(screen, { e, v -> e.copy(hueRedValue = v) }, value)

    override suspend fun setHueGreenValue(screen: Screen, value: Float): Result<WallpaperEffects, PreferenceError> =
        updateEffectValue(screen, { e, v -> e.copy(hueGreenValue = v) }, value)

    override suspend fun setHueBlueValue(screen: Screen, value: Float): Result<WallpaperEffects, PreferenceError> =
        updateEffectValue(screen, { e, v -> e.copy(hueBlueValue = v) }, value)

    override suspend fun setScaleRedValue(screen: Screen, value: Float): Result<WallpaperEffects, PreferenceError> =
        updateEffectValue(screen, { e, v -> e.copy(scaleRedValue = v) }, value)

    override suspend fun setScaleGreenValue(screen: Screen, value: Float): Result<WallpaperEffects, PreferenceError> =
        updateEffectValue(screen, { e, v -> e.copy(scaleGreenValue = v) }, value)

    override suspend fun setScaleBlueValue(screen: Screen, value: Float): Result<WallpaperEffects, PreferenceError> =
        updateEffectValue(screen, { e, v -> e.copy(scaleBlueValue = v) }, value)

    private suspend fun update(block: (WallpaperEffects) -> WallpaperEffects) {
        dataStore.updateData { current -> block(current) }
    }
}
