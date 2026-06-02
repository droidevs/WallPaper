package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import io.droidevs.wallpaper.data.datastore.delegate.PreferenceDelegate
import io.droidevs.wallpaper.domain.preferences.ChangeWhenOnPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class ChangeWhenOnDataStore @Inject constructor(
    @Named("stateChangePreferences") private val dataStore: DataStore<Preferences>
) : ChangeWhenOnPreference {

    private val delegate by lazy {
        PreferenceDelegate(
            dataStore = dataStore,
            key = booleanPreferencesKey("change_when_on"),
            defaultValue = false
        )
    }
    override val isChangeWhenOnEnabled: Flow<Result<Boolean, PreferenceError>> =
        delegate.flow

    override suspend fun setChangeWhenOnEnabled(enabled: Boolean): Result<Boolean, PreferenceError> {
        return delegate.set(enabled)
    }
}