package io.droidevs.wallpaper.domain.usecases.preference.auto_wallpaper_settings

import io.droidevs.wallpaper.domain.model.AutoWallpaperSettings
import io.droidevs.wallpaper.domain.model.Screen
import io.droidevs.wallpaper.domain.preferences.AutoWallpaperSettingsPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError

class SetAutoModeUseCase(
    private val preference: AutoWallpaperSettingsPreference
) {
    suspend operator fun invoke(
        target: Screen,
        enabled: Boolean
    ): Result<AutoWallpaperSettings, PreferenceError> =
        preference.setAutoMode(target, enabled)

}