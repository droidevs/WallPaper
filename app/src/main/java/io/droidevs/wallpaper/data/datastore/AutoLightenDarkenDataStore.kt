package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import io.droidevs.wallpaper.data.datastore.delegate.PreferenceDelegate
import io.droidevs.wallpaper.domain.preferences.AutoLightenDarkenPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow

class AutoLightenDarkenDataStore(
    private val dataStore: DataStore<Preferences>,
) : AutoLightenDarkenPreference {

    private val delegate by lazy {
        PreferenceDelegate(
            dataStore,
            booleanPreferencesKey("auto_lighten_darken_pref"),
            false
        )
    }

    override val isEnabled: Flow<Result<Boolean, PreferenceError>>
        get() {
            return delegate.flow
        }

    override suspend fun setEnabled(enabled: Boolean): Result<Boolean, PreferenceError> {
        return delegate.set(enabled)
    }
}