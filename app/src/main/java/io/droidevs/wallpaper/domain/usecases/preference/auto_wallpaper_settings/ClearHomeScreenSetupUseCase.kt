package io.droidevs.wallpaper.domain.usecases.preference.auto_wallpaper_settings

import io.droidevs.wallpaper.domain.model.AutoWallpaperSettings
import io.droidevs.wallpaper.domain.model.WallpaperAlbumSetup
import io.droidevs.wallpaper.domain.preferences.AutoWallpaperSettingsPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError

class ClearHomeScreenSetupUseCase(
    private val preference: AutoWallpaperSettingsPreference
) {
    suspend operator fun invoke(): Result<AutoWallpaperSettings, PreferenceError> =
        preference.saveHomeScreen(null)
}