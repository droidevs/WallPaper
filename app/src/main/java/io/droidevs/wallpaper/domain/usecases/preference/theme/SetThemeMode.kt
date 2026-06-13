package io.droidevs.wallpaper.domain.usecases.preference.theme

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.ThemeMode
import io.droidevs.wallpaper.domain.preferences.ThemeModePreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.withContext

class SetThemeModeUseCase(
    private val preference: ThemeModePreference,
    private val dispatchers: AppDispatchers
) {
    suspend operator fun invoke(mode: ThemeMode): Result<ThemeMode, PreferenceError> =
        withContext(dispatchers.io) {
            preference.saveThemeMode(mode)
        }
}