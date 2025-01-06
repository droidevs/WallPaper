package io.droidevs.wallpaper.infrastructure.datastore

import kotlinx.coroutines.flow.Flow


interface HomeIntervalPreference {

    suspend fun homeInterval() : Flow<TimeInterval>


    suspend fun homeChangeInterval(interval : TimeInterval)
}