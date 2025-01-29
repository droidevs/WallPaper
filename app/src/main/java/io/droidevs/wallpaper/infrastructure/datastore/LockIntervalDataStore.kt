package io.droidevs.wallpaper.infrastructure.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LockIntervalDataStore @Inject constructor(
    val dataStore: DataStore<Preferences>
) : LockIntervalPreference {
    override suspend fun interval(): Flow<TimeInterval> {
        return dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map {
                val longInterval = it[key]
                timeIntervales.forEach {
                    if (it.interval == longInterval)
                        it
                }
                TimeInterval.Off
            }
    }

    override suspend fun changeInterval(interval: TimeInterval) {
        dataStore.edit {
            it[key] = interval.interval
        }
    }


    val key : Preferences.Key<Long> = longPreferencesKey("auto_wallpaper_lock_interval")
}