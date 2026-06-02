package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import io.droidevs.wallpaper.data.datastore.delegate.PreferenceDelegate
import io.droidevs.wallpaper.domain.preferences.PortraitOrientationLockPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class PortraitOrientationLockDataStore @Inject constructor(
    @Named("orientationPreferences") private val dataStore: DataStore<Preferences>
) : PortraitOrientationLockPreference {

    private val delegate by lazy {
        PreferenceDelegate(
            dataStore = dataStore,
            key = booleanPreferencesKey(
                "portrait_orientation_lock_key_pref"
            ),
            defaultValue = false
        )
    }

    override val isPortraitOrientationLockEnabled: Flow<Result<Boolean, PreferenceError>> =
        delegate.flow

    override suspend fun setPortraitOrientationLockEnabled(enabled: Boolean): Result<Boolean, PreferenceError> {
        return delegate.set(enabled)
    }
}