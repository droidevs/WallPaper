package io.droidevs.wallpaper.data.datastore.delegate

import androidx.datastore.core.DataStore
import io.droidevs.wallpaper.data.datastore.exceptions.flowCatchingPreference
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import io.droidevs.wallpaper.domain.result.Result

interface ProtoReadDelegate<D> {

    fun <T> get(
        defaultValue: T,
        read: (D) -> T
    ): Flow<Result<T, PreferenceError>>


}
class ProtoReadDelegateImpl<D>(
    private val dataStore: DataStore<D>
) : ProtoReadDelegate<D> {

    override fun <T> get(
        defaultValue: T,
        read: (D) -> T
    ): Flow<Result<T, PreferenceError>> = flowCatchingPreference {
        dataStore.data.map { read(it) ?: defaultValue }
    }
}
