package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AutoLightenDarkenDataStore(
    private val dataStore: DataStore<Preferences>
) : AutoLightenDarkenPreference {

    companion object {
        private val ENABLED_KEY = booleanPreferencesKey("auto_lighten_darken_pref")
    }

    override fun isEnabled(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[ENABLED_KEY] ?: false // Default value is false if not set
        }
    }

    override suspend fun setEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[ENABLED_KEY] = enabled
        }
    }
}