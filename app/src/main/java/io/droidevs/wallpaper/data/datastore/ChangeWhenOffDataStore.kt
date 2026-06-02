package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import io.droidevs.wallpaper.data.datastore.delegate.PreferenceDelegate
import io.droidevs.wallpaper.domain.preferences.ChangeWhenOffPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class ChangeWhenOffDataStore @Inject constructor(
    @Named("stateChangePreferences") private val dataStore: DataStore<Preferences>
) : ChangeWhenOffPreference {

    private val delegate by lazy {
        PreferenceDelegate(
            dataStore = dataStore,
            defaultValue = false,
            key = key
        )
    }
    override val isChangeWhenOffEnabled: Flow<Result<Boolean, PreferenceError>> =
        delegate.flow

    override suspend fun setChangeWhenOffEnabled(enabled: Boolean): Result<Boolean, PreferenceError> {
        return delegate.set(enabled)
    }

    companion object {
        private val key = booleanPreferencesKey(
            "change_when_off_key_pref"
        )
    }
}
