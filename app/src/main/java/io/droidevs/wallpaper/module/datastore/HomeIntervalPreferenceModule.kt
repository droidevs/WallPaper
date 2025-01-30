package io.droidevs.wallpaper.module.datastore

import dagger.Binds
import dagger.Module
import io.droidevs.wallpaper.infrastructure.datastore.HomeIntervalDataStore
import io.droidevs.wallpaper.infrastructure.datastore.HomeIntervalPreference


@Module
abstract class HomeIntervalPreferenceModule {

    @Binds
    abstract fun binHomeIntervalPreference(impl : HomeIntervalDataStore) : HomeIntervalPreference
}