package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.Preferences
import io.droidevs.wallpaper.data.datastore.delegate.PreferenceDelegate
import io.droidevs.wallpaper.domain.preferences.ThemeModePreference
import io.droidevs.wallpaper.domain.model.ThemeMode
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import io.droidevs.wallpaper.domain.result.map
import io.droidevs.wallpaper.domain.result.mapResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ThemeModeDataStore(
    private val dataStore: DataStore<Preferences>
) : ThemeModePreference {

    private val delegate by lazy {
        PreferenceDelegate(
            dataStore = dataStore,
            defaultValue = ThemeMode.AUTO.nameText,
            key = stringPreferencesKey("theme_mode")
        )
    }

    override suspend fun saveThemeMode(mode: ThemeMode): Result<ThemeMode, PreferenceError> {
        return delegate.set(mode.nameText)
            .map {
                ThemeMode.fromName(it)
            }
    }

    override val themeMode : Flow<Result<ThemeMode, PreferenceError>>
        = delegate.flow
            .mapResult {
                ThemeMode.fromName(it)
            }
}
