package io.droidevs.wallpaper.infrastructure.datastore

import androidx.compose.material3.ScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScreenDataStore @Inject constructor (
    private val datastore : DataStore<Preferences>
) : ScreenPreference {

    override suspend fun screen(): Flow<Screen> {
        return datastore.data
                .catch {
                    emit(emptyPreferences())
                }
                .map {
                    preferences ->
                    var strScreen = preferences[key]
                    when(strScreen){
                        Values.home -> Screen.Home
                        Values.lock -> Screen.Lock
                        else -> Screen.Both
                    }
                }
    }

    override suspend fun changeScreen(screen: Screen) {
        datastore.edit {
            preferences ->
            preferences[key] =
                when(screen){
                    Screen.Home -> Values.home
                    Screen.Lock -> Values.lock
                    Screen.Both -> Values.both
                }
        }
    }

    object Values {
        val home = "home"
        val lock = "lock"
        val both = "both"
    }

    protected val key : Preferences.Key<String> = stringPreferencesKey("auto_wallpaper_screen")
}