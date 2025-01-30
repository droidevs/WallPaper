package io.droidevs.wallpaper.infrastructure.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import io.droidevs.wallpaper.infrastructure.datasource.dao.AlbumDao
import javax.inject.Inject

class HomeAlbumDatastore @Inject constructor(
    dataStore: DataStore<Preferences>,
    albumDao: AlbumDao
) : BaseAlbumDatastore(dataStore,albumDao) {

    override val albumKey : Preferences.Key<Long> = longPreferencesKey("home_screen_album")

}