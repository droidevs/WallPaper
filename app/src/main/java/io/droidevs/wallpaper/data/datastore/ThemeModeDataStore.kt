package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.Preferences
import io.droidevs.wallpaper.models.ThemeMode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ThemeModeDataStore(
    private val dataStore: DataStore<Preferences>
) : ThemeModePreference {

    private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")

    override suspend fun saveThemeMode(mode: ThemeMode) {
        dataStore.edit { prefs ->
            prefs[THEME_MODE_KEY] = mode.nameText
        }
    }

    override suspend fun getThemeMode(): ThemeMode {
        val name = dataStore.data
            .map { prefs -> prefs[THEME_MODE_KEY] }
            .first()

        return ThemeMode.fromName(name)
    }
}
