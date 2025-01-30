package io.droidevs.wallpaper.infrastructure.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import io.droidevs.wallpaper.infrastructure.datasource.dao.AlbumDao
import io.droidevs.wallpaper.infrastructure.model.AlbumEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

abstract class HomeAlbumDatastore(
    protected val dataStore: DataStore<Preferences>,
    protected val albumDao: AlbumDao
) : HomeAlbumPreference {

    val albumKey : Preferences.Key<Long> = longPreferencesKey("home_screen_album")

    override suspend fun setHomeAlbum(album: AlbumEntity) {
        dataStore.edit {
            it[albumKey] = album.id
        }
    }

    override suspend fun retrieveHomeAlbum(): Flow<AlbumEntity> {
        return dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map { preferences ->
                val albumId = preferences[albumKey]
                if (albumId == null) {
                    AlbumEntity() // Return default or empty album if not found
                } else {
                    albumDao.getAlbumById(albumId) ?: AlbumEntity() // Fetch from DAO
                }
            }
    }
}
