package io.droidevs.wallpaper.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.droidevs.wallpaper.data.local.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWallpapersDatabase(@ApplicationContext context: Context): AppDatabase? {
        return AppDatabase.getInstance(context)
    }
}