package io.droidevs.wallpaper

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.droidevs.wallpaper.infrastructure.datasource.dao.WallpaperDao
import io.droidevs.wallpaper.infrastructure.datasource.instances.WallpaperDatabase
import io.droidevs.wallpaper.infrastructure.repository.WallpaperRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWallpapersDatabase(@ApplicationContext context: Context) : WallpaperDatabase?{
        return WallpaperDatabase.getInstance(context)
    }


    @Provides
    @Singleton
    fun provideWallpaperDao(wallpaperDatabase: WallpaperDatabase) : WallpaperDao{
        return wallpaperDatabase.wallpaperDao()
    }

    @Provides
    @Singleton
    fun provideWallpaperRepository(wallpaperDao : WallpaperDao) : WallpaperRepository{
        return WallpaperRepository(wallpaperDao)
    }


}