package io.droidevs.wallpaper.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.dispatchers.DefaultAppDispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    fun provideAppDispatchers(): AppDispatchers = DefaultAppDispatchers()
}