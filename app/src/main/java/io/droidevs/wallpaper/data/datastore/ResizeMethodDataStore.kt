package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.droidevs.wallpaper.data.datastore.delegate.PreferenceDelegate
import io.droidevs.wallpaper.domain.preferences.ResizeMethodPreference
import io.droidevs.wallpaper.domain.model.ResizeMethod
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import io.droidevs.wallpaper.domain.result.map
import io.droidevs.wallpaper.domain.result.mapResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class ResizeMethodDataStore(private val dataStore: DataStore<Preferences>) :
    ResizeMethodPreference {

    private val delegate by lazy {
        PreferenceDelegate(
            dataStore = dataStore,
            defaultValue = ResizeMethod.NONE.nameText,
            key = stringPreferencesKey("resize_method")
        )
    }


    override suspend fun saveResizeMethod(method: ResizeMethod): Result<ResizeMethod, PreferenceError> {
        return delegate.set(method.nameText)
            .map {
                ResizeMethod.fromString(it)
            }
    }

    override val resizedMethod: Flow<Result<ResizeMethod, PreferenceError>>
        get() = delegate.flow
            .mapResult {
                ResizeMethod.fromString(it)
            }

}
