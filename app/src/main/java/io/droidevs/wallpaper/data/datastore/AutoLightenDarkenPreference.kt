package io.droidevs.wallpaper.data.datastore

import kotlinx.coroutines.flow.Flow


interface AutoLightenDarkenPreference {

    fun isEnabled(): Flow<Boolean>

    suspend fun  setEnabled(enabled: Boolean)

}