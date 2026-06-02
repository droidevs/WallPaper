package io.droidevs.wallpaper.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.droidevs.wallpaper.data.local.dao.LocalWallpaperDao
import io.droidevs.wallpaper.data.repository.LocalWallpaperRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideWallpaperRepository(localWallpaperDao : LocalWallpaperDao) : LocalWallpaperRepository {
        return LocalWallpaperRepository(localWallpaperDao)
    }

}