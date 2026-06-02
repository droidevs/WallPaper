package io.droidevs.wallpaper.data.datastore.delegate

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import io.droidevs.wallpaper.data.datastore.exceptions.flowCatchingPreference
import io.droidevs.wallpaper.data.datastore.exceptions.runCatchingPreference
import io.droidevs.wallpaper.data.datastore.exceptions.runCatchingPreferenceWithResult
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class PreferenceDelegate<T>(
    private val dataStore: DataStore<Preferences>,
    private val key: Preferences.Key<T>,
    private val defaultValue: T,
) {
    val flow : Flow<Result<T, PreferenceError>> by lazy {
        flowCatchingPreference {
            dataStore.data
                .map { it[key] ?: defaultValue }
        }
    }

    suspend fun get(): Result<T, PreferenceError> = runCatchingPreferenceWithResult {
        flow.first()
    }

    suspend fun set(value: T): Result<T, PreferenceError> = runCatchingPreference {
        dataStore.edit { prefs ->
            prefs[key] = value
        }.get(key)?: defaultValue
    }
}