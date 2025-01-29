package io.droidevs.wallpaper.infrastructure.datastore.module

import dagger.Binds
import dagger.Module
import io.droidevs.wallpaper.infrastructure.datastore.LockIntervalDataStore
import io.droidevs.wallpaper.infrastructure.datastore.LockIntervalPreference


@Module
abstract class LockIntervalPreferenceModule {

    @Binds
    abstract fun provideLockIntervalPreference(impl: LockIntervalDataStore): LockIntervalPreference
}