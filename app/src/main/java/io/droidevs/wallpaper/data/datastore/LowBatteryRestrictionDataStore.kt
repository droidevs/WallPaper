package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import io.droidevs.wallpaper.data.datastore.delegate.PreferenceDelegate
import io.droidevs.wallpaper.domain.preferences.LowBatteryRestrictionPreference
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named


class LowBatteryRestrictionDataStore @Inject constructor(
    @Named("batteryPreferences") private val dataStore: DataStore<Preferences>
) : LowBatteryRestrictionPreference {

    private val delegate by lazy {
        PreferenceDelegate(
            dataStore = dataStore,
            key = booleanPreferencesKey(
                "low_battery_restriction_key_pref"
            ),
            defaultValue = false
        )
    }
    override val isLowBatteryRestrictionEnabled: Flow<Result<Boolean, PreferenceError>> =
        delegate.flow

    override suspend fun setLowBatteryRestrictionEnabled(enabled: Boolean): Result<Boolean, PreferenceError> {
        return delegate.set(enabled)
    }
}