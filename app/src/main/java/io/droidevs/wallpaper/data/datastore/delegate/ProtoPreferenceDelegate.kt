package io.droidevs.wallpaper.data.datastore.delegate

import androidx.datastore.core.DataStore
import io.droidevs.wallpaper.data.datastore.exceptions.flowCatchingPreference
import io.droidevs.wallpaper.data.datastore.exceptions.runCatchingPreference
import io.droidevs.wallpaper.data.datastore.exceptions.runCatchingPreferenceWithResult
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import io.droidevs.wallpaper.domain.result.Result
import kotlinx.coroutines.flow.first

class ProtoPreferenceDelegate<T>(
    private val dataStore: DataStore<T>,
    private val defaultValue: T
) {
    val flow: Flow<Result<T, PreferenceError>> by lazy {
        flowCatchingPreference {
            dataStore.data.map { it ?: defaultValue }
        }
    }

    suspend fun get(): Result<T, PreferenceError> = runCatchingPreferenceWithResult {
        flow.first()
    }

    suspend fun set(value: T): Result<T, PreferenceError> = runCatchingPreference {
        dataStore.updateData { prefs ->
            value
        }
    }
}
