package io.droidevs.wallpaper.domain.usecases.preference.auto_wallpaper_settings

import io.droidevs.wallpaper.domain.model.AutoWallpaperSettings
import io.droidevs.wallpaper.domain.preferences.AutoWallpaperSettingsPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow


class ObserveWallpaperSettingsUseCase(
    private val preference: AutoWallpaperSettingsPreference
) {
    operator fun invoke(): Flow<Result<AutoWallpaperSettings, PreferenceError>> =
        preference.settings
}