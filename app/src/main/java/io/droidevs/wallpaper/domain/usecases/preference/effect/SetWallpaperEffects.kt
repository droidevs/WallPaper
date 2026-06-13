package io.droidevs.wallpaper.domain.usecases.preference.effect

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.WallpaperEffects
import io.droidevs.wallpaper.domain.preferences.WallpaperEffectsPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.withContext

class SetWallpaperEffectUseCase(
    private val preference: WallpaperEffectsPreference,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(effect: WallpaperEffects): Result<WallpaperEffects, PreferenceError> =
        withContext(dispatchers.io) {
            preference.saveEffect(effect)
        }
}