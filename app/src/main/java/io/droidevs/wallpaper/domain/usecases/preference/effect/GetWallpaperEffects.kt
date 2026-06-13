package io.droidevs.wallpaper.domain.usecases.preference.effect

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.WallpaperEffects
import io.droidevs.wallpaper.domain.preferences.WallpaperEffectsPreference
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import io.droidevs.wallpaper.domain.result.Result
import kotlinx.coroutines.flow.flowOn

class GetWallpaperEffectsUseCase(
    private val preference: WallpaperEffectsPreference,
    private val dispatchers: AppDispatchers
) {
    operator fun invoke(): Flow<Result<WallpaperEffects, PreferenceError>> =
        preference.effect
            .flowOn(dispatchers.io)
}