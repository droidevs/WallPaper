package io.droidevs.wallpaper.module.datastore

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.droidevs.wallpaper.infrastructure.datastore.ScreenDataStore
import io.droidevs.wallpaper.infrastructure.datastore.ScreenPreference


@InstallIn(ViewModelComponent::class)
@Module
abstract class ScreenPreferenceModule {

    @Binds
    abstract fun bindScreenPreference(impl : ScreenDataStore) : ScreenPreference
}