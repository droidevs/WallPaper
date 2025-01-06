package io.droidevs.wallpaper.infrastructure.datastore

import androidx.compose.ui.res.booleanResource
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class HomeIntervalDataStore @Inject constructor(
    val dataStore: DataStore<Preferences>
) : HomeIntervalPreference {

    override suspend fun homeInterval(): Flow<TimeInterval> {
        return dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map {
                preferences ->
                val longInterval = preferences[key]
                timeIntervales.forEach {
                    if (it.interval == longInterval)
                        it
                }
                TimeInterval.Off
            }
    }

    override suspend fun homeChangeInterval(duration : TimeInterval) {
        dataStore.edit {
            it[key] = duration.interval
        }
    }

    private val key : Preferences.Key<Long> = longPreferencesKey("home_wallpaper_interval_change")
}