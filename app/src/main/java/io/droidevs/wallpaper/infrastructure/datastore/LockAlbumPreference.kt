package io.droidevs.wallpaper.infrastructure.datastore

import io.droidevs.wallpaper.infrastructure.model.AlbumEntity
import kotlinx.coroutines.flow.Flow

interface LockAlbumPreference {

    suspend fun setLockAlbum(album: AlbumEntity)

    suspend fun retrieveLockAlbum() : Flow<AlbumEntity>

}