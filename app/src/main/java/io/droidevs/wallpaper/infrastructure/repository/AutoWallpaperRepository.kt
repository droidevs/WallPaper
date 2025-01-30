package io.droidevs.wallpaper.infrastructure.repository

import io.droidevs.wallpaper.infrastructure.datastore.HomeAlbumPreference
import io.droidevs.wallpaper.infrastructure.datastore.HomeIntervalPreference
import io.droidevs.wallpaper.infrastructure.datastore.LockAlbumPreference
import io.droidevs.wallpaper.infrastructure.datastore.LockIntervalPreference
import io.droidevs.wallpaper.infrastructure.datastore.ScreenPreference
import io.droidevs.wallpaper.infrastructure.datastore.TimeInterval
import io.droidevs.wallpaper.infrastructure.model.AlbumEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Singleton

@Singleton
class AutoWallpaperRepository @Inject constructor(
    private val screenPreference: ScreenPreference,
    private val homeAlbumPreference: HomeAlbumPreference,
    private val lockAlbumPreference: LockAlbumPreference,
    private val homeIntervalPreference: HomeIntervalPreference,
    private val lockIntervalPreference: LockIntervalPreference,
    private val ioScope: CoroutineScope // Injecting a CoroutineScope for structured concurrency
) {

    suspend fun getHomeAlbum(): Flow<AlbumEntity> =
        homeAlbumPreference.retrieveHomeAlbum().flowOn(Dispatchers.IO)

    suspend fun getLockAlbum(): Flow<AlbumEntity> =
        lockAlbumPreference.retrieveLockAlbum().flowOn(Dispatchers.IO)

    suspend fun getHomeInterval(): Flow<TimeInterval> =
        homeIntervalPreference.homeInterval().flowOn(Dispatchers.IO)

    suspend fun getLockInterval(): Flow<TimeInterval> =
        lockIntervalPreference.interval().flowOn(Dispatchers.IO)

    suspend fun setHomeAlbum(album: AlbumEntity) {
        withContext(Dispatchers.IO) {
            homeAlbumPreference.setHomeAlbum(album)
        }
    }

    suspend fun setLockAlbum(album: AlbumEntity) {
        withContext(Dispatchers.IO) {
            lockAlbumPreference.setLockAlbum(album)
        }
    }

    suspend fun setHomeInterval(interval: TimeInterval) {
        withContext(Dispatchers.IO) {
            homeIntervalPreference.homeChangeInterval(interval)
        }
    }

    suspend fun setLockInterval(interval: TimeInterval) {
        withContext(Dispatchers.IO) {
            lockIntervalPreference.changeInterval(interval)
        }
    }

    /**
     * Combines all wallpaper preferences into a single Flow
     */
    suspend fun getAllPreferences(): Flow<AutoWallpaperPreferences> {
        return combine(
            getHomeAlbum(),
            getLockAlbum(),
            getHomeInterval(),
            getLockInterval()
        ) { homeAlbum, lockAlbum, homeInterval, lockInterval ->
            AutoWallpaperPreferences(homeAlbum, lockAlbum, homeInterval, lockInterval)
        }.flowOn(Dispatchers.IO)
    }
}

data class AutoWallpaperPreferences(
    val homeAlbum: AlbumEntity,
    val lockAlbum: AlbumEntity,
    val homeInterval: TimeInterval,
    val lockInterval: TimeInterval
)
