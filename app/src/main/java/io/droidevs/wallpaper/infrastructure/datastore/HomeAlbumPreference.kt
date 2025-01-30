package io.droidevs.wallpaper.infrastructure.datastore

import io.droidevs.wallpaper.infrastructure.model.AlbumEntity
import kotlinx.coroutines.flow.Flow

interface HomeAlbumPreference {

    suspend fun setHomeAlbum(album: AlbumEntity)

    suspend fun retrieveHomeAlbum() : Flow<AlbumEntity>

}