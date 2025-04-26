package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.droidevs.wallpaper.models.ResizeMethod
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class ResizeMethodDataStore(private val dataStore: DataStore<Preferences>) : ResizeMethodPreference {

    private val key = stringPreferencesKey("resize_method")

    override suspend fun saveResizeMethod(method: ResizeMethod) {
        dataStore.edit { prefs ->
            prefs[key] = method.nameText
        }
    }

    override suspend fun getResizeMethod(): ResizeMethod {
        val value = dataStore.data
            .map { prefs -> prefs[key] }
            .first()

        return ResizeMethod.fromString(value)
    }
}
