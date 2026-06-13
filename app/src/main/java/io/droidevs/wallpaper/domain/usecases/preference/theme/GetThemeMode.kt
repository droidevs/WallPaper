package io.droidevs.wallpaper.domain.usecases.preference.theme

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.ThemeMode
import io.droidevs.wallpaper.domain.preferences.ThemeModePreference
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import io.droidevs.wallpaper.domain.result.Result
import kotlinx.coroutines.flow.flowOn

class GetThemeModeUseCase(
    private val preference: ThemeModePreference,
    private val dispatchers: AppDispatchers
) {
    operator fun invoke(): Flow<Result<ThemeMode, PreferenceError>> =
        preference.themeMode
            .flowOn(dispatchers.io)
}