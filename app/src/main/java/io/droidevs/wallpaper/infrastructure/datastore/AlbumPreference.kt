package io.droidevs.wallpaper.infrastructure.datastore

import io.droidevs.wallpaper.infrastructure.model.AlbumEntity
import kotlinx.coroutines.flow.Flow

interface AlbumPreference {

    suspend fun saveAlbum(album: AlbumEntity)

    suspend fun retrieveAlbum() : Flow<AlbumEntity>

}