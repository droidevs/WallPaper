package io.droidevs.wallpaper.data.datastore.delegate

import androidx.datastore.core.DataStore
import io.droidevs.wallpaper.data.datastore.exceptions.flowCatchingPreference
import io.droidevs.wallpaper.data.datastore.exceptions.runCatchingPreference
import io.droidevs.wallpaper.data.datastore.exceptions.runCatchingPreferenceWithResult
import kotlinx.coroutines.flow.Flow
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ProtoDelegateEach<T, D>(
    private val dataStore: DataStore<D>,
    private val defaultValue: T?,
    private val getter: (D) -> T?,
    private val setter: (D, T?) -> D
) {
    val flow: Flow<Result<T?, PreferenceError>> by lazy {
        flowCatchingPreference {
            dataStore.data.map { getter(it) ?: defaultValue }
        }
    }

    suspend fun get(): Result<T?, PreferenceError> = runCatchingPreferenceWithResult {
        flow.first()
    }

    suspend fun set(value: T?): Result<D, PreferenceError> = runCatchingPreference {
        dataStore.updateData { prefs ->
            setter(prefs, value)
        }
    }
}
