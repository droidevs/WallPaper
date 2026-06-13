package io.droidevs.wallpaper.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.droidevs.wallpaper.data.local.AppDatabase
import io.droidevs.wallpaper.data.local.dao.LocalWallpaperDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    @Singleton
    fun provideWallpaperDao(appDatabase: AppDatabase) : LocalWallpaperDao {
        return appDatabase.wallpaperDao()
    }

}