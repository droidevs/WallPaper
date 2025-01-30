package io.droidevs.wallpaper.infrastructure.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import io.droidevs.wallpaper.infrastructure.datasource.dao.AlbumDao

class LockAlbumDatastore (
    dataStore: DataStore<Preferences>,
    albumDao: AlbumDao,
) : BaseAlbumDatastore(
    dataStore, albumDao
) {

    override val albumKey: Preferences.Key<Long> = longPreferencesKey("lock_album_id")

}