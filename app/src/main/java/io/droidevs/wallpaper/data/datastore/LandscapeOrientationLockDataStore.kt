package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import io.droidevs.wallpaper.data.datastore.delegate.PreferenceDelegate
import io.droidevs.wallpaper.domain.preferences.LandscapeOrientationLockPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class LandscapeOrientationLockDataStore @Inject constructor(
    @Named("orientationPreferences") private val dataStore: DataStore<Preferences>
) : LandscapeOrientationLockPreference {

    private val delegate by lazy {
        PreferenceDelegate(
            dataStore = dataStore,
            key = booleanPreferencesKey(
                "landscape_orientation_lock_pref"
            ),
            defaultValue = false
        )
    }
    override val isLandscapeOrientationLockEnabled: Flow<Result<Boolean, PreferenceError>> =
        delegate.flow

    override suspend fun setLandscapeOrientationLockEnabled(enabled: Boolean): Result<Boolean, PreferenceError> {
        return delegate.set(enabled)
    }
}