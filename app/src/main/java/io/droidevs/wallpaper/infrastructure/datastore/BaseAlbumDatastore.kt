package io.droidevs.wallpaper.infrastructure.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import io.droidevs.wallpaper.infrastructure.datasource.dao.AlbumDao
import io.droidevs.wallpaper.infrastructure.model.AlbumEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

abstract class BaseAlbumDatastore(
    protected val dataStore: DataStore<Preferences>,
    protected val albumDao: AlbumDao
) {

    abstract val albumKey: Preferences.Key<Long>

    suspend fun saveAlbum(album: AlbumEntity) {
        dataStore.edit {
            it[albumKey] = album.id
        }
    }

    suspend fun retrieveAlbum(): Flow<AlbumEntity> {
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
