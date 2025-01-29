package io.droidevs.wallpaper.infrastructure.datastore

import kotlinx.coroutines.flow.Flow


interface LockIntervalPreference {

    suspend fun interval() : Flow<TimeInterval>


    suspend fun changeInterval(interval: TimeInterval)

}