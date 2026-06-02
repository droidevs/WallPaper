package io.droidevs.wallpaper.domain.preferences

import io.droidevs.wallpaper.domain.model.ThemeMode
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow

interface ThemeModePreference {
    suspend fun saveThemeMode(mode: ThemeMode) : Result<ThemeMode, PreferenceError>
    val themeMode : Flow<Result<ThemeMode, PreferenceError>>
}
