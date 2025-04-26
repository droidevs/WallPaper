package io.droidevs.wallpaper.data.datastore

import io.droidevs.wallpaper.models.ThemeMode

interface ThemeModePreference {
    suspend fun saveThemeMode(mode: ThemeMode)
    suspend fun getThemeMode(): ThemeMode
}
