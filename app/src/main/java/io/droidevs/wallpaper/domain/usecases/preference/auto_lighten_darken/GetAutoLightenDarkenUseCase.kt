package io.droidevs.wallpaper.domain.usecases.preference.auto_lighten_darken

import io.droidevs.wallpaper.domain.preferences.AutoLightenDarkenPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow

class GetAutoLightenDarkenUseCase(
    private val preference: AutoLightenDarkenPreference
) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> =
        preference.isEnabled
}
