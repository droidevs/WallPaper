package io.droidevs.wallpaper.infrastructure.datastore

import kotlinx.coroutines.flow.Flow

interface ScreenPreference {

    suspend fun screen() : Flow<Screen>

    suspend fun changeScreen(screen: Screen)

}