package io.droidevs.wallpaper.data.datastore

import androidx.datastore.core.DataStore
import io.droidevs.wallpaper.models.WallpaperEffects
import io.droidevs.wallpaper.models.Screen
import kotlinx.coroutines.flow.first

class EffectsDataStore(private val dataStore: DataStore<WallpaperEffects>) : WallpaperEffectsPreference {

    override suspend fun getEffect(): WallpaperEffects {
        return dataStore.data.first()
    }

    override suspend fun saveEffect(effect: WallpaperEffects) {
        dataStore.updateData { effect }
    }

    override suspend fun setBlurValue(screen: Screen, value: Float) {
        dataStore.updateData {
            when(screen){
                Screen.HOME ->
                    it.copy(homeEffect = it.homeEffect.copy(blurValue = value))
                Screen.LOCK ->
                    it.copy(lockEffect = it.lockEffect.copy(blurValue = value))
                Screen.BOTH ->
                    it.copy(
                        homeEffect = it.homeEffect.copy(blurValue = value),
                        lockEffect = it.lockEffect.copy(blurValue = value)
                    )

                Screen.NONE -> {
                    dataStore.updateData { it }
                }
            }
        }
    }

    override suspend fun setBrightnessValue(screen: Screen, value: Float) {
        dataStore.updateData {
            when(screen){
                Screen.HOME ->
                    it.copy(homeEffect = it.homeEffect.copy(brightnessValue = value))
                Screen.LOCK ->
                    it.copy(lockEffect = it.lockEffect.copy(brightnessValue = value))
                Screen.BOTH ->
                    it.copy(
                        homeEffect = it.homeEffect.copy(brightnessValue = value),
                        lockEffect = it.lockEffect.copy(brightnessValue = value)
                    )
                Screen.NONE -> {
                    dataStore.updateData { it }
                }
            }
        }
    }

    override suspend fun setContrastValue(screen: Screen, value: Float) {
        dataStore.updateData {
            when(screen){
                Screen.HOME ->
                    it.copy(homeEffect = it.homeEffect.copy(contrastValue = value))
                Screen.LOCK ->
                    it.copy(lockEffect = it.lockEffect.copy(contrastValue = value))
                Screen.BOTH ->
                    it.copy(
                        homeEffect = it.homeEffect.copy(contrastValue = value),
                        lockEffect = it.lockEffect.copy(contrastValue = value)
                    )
                Screen.NONE -> {
                    dataStore.updateData { it }
                }
            }
        }
    }

    override suspend fun setSaturationValue(screen: Screen, value: Float) {
        dataStore.updateData {
            when(screen) {
                Screen.HOME ->
                    it.copy(homeEffect = it.homeEffect.copy(saturationValue = value))

                Screen.LOCK ->
                    it.copy(lockEffect = it.lockEffect.copy(saturationValue = value))

                Screen.BOTH ->
                    it.copy(
                        homeEffect = it.homeEffect.copy(saturationValue = value),
                        lockEffect = it.lockEffect.copy(saturationValue = value)
                    )
                Screen.NONE -> {
                    dataStore.updateData { it }
                }
            }
        }
    }

    override suspend fun setHueRedValue(screen: Screen, value: Float) {
        dataStore.updateData {
            when(screen){
                Screen.HOME ->
                    it.copy(homeEffect = it.homeEffect.copy(hueRedValue = value))
                Screen.LOCK ->
                    it.copy(lockEffect = it.lockEffect.copy(hueRedValue = value))
                Screen.BOTH ->
                    it.copy(
                        homeEffect = it.homeEffect.copy(hueRedValue = value),
                        lockEffect = it.lockEffect.copy(hueRedValue = value)
                    )
                Screen.NONE -> {
                    dataStore.updateData { it }
                }
            }
        }
    }

    override suspend fun setHueGreenValue(screen: Screen, value: Float) {
        dataStore.updateData {
            when (screen) {
                Screen.HOME ->
                    it.copy(homeEffect = it.homeEffect.copy(hueGreenValue = value))

                Screen.LOCK ->
                    it.copy(lockEffect = it.lockEffect.copy(hueGreenValue = value))

                Screen.BOTH ->
                    it.copy(
                        homeEffect = it.homeEffect.copy(hueGreenValue = value),
                        lockEffect = it.lockEffect.copy(hueGreenValue = value)
                    )
                Screen.NONE -> {
                    dataStore.updateData { it }
                }
            }
        }
    }

    override suspend fun setHueBlueValue(screen: Screen, value: Float) {
        dataStore.updateData {
            when(screen){
                Screen.HOME ->
                    it.copy(homeEffect = it.homeEffect.copy(hueBlueValue = value))
                Screen.LOCK ->
                    it.copy(lockEffect = it.lockEffect.copy(hueBlueValue = value))
                Screen.BOTH ->
                    it.copy(
                        homeEffect = it.homeEffect.copy(hueBlueValue = value),
                        lockEffect = it.lockEffect.copy(hueBlueValue = value)
                    )
                Screen.NONE -> {
                    dataStore.updateData { it }
                }
            }
        }
    }

    override suspend fun setScaleRedValue(screen: Screen, value: Float) {
        dataStore.updateData {
            when(screen){
                Screen.HOME ->
                    it.copy(homeEffect = it.homeEffect.copy(scaleRedValue = value))
                Screen.LOCK ->
                    it.copy(lockEffect = it.lockEffect.copy(scaleRedValue = value))
                Screen.BOTH ->
                    it.copy(
                        homeEffect = it.homeEffect.copy(scaleRedValue = value),
                        lockEffect = it.lockEffect.copy(scaleRedValue = value)
                    )
                Screen.NONE -> {
                    dataStore.updateData { it }
                }
            }
        }
    }

    override suspend fun setScaleGreenValue(screen: Screen, value: Float) {
        dataStore.updateData {
            when(screen){
                Screen.HOME ->
                    it.copy(homeEffect = it.homeEffect.copy(scaleGreenValue = value))
                Screen.LOCK ->
                    it.copy(lockEffect = it.lockEffect.copy(scaleGreenValue = value))
                Screen.BOTH ->
                    it.copy(
                        homeEffect = it.homeEffect.copy(scaleGreenValue = value),
                        lockEffect = it.lockEffect.copy(scaleGreenValue = value)
                    )
                Screen.NONE -> {
                    dataStore.updateData { it }
                }
            }
        }
    }

    override suspend fun setScaleBlueValue(screen: Screen, value: Float) {
        dataStore.updateData {
            when(screen){
                Screen.HOME ->
                    it.copy(homeEffect = it.homeEffect.copy(scaleBlueValue = value))
                Screen.LOCK ->
                    it.copy(lockEffect = it.lockEffect.copy(scaleBlueValue = value))
                Screen.BOTH ->
                    it.copy(
                        homeEffect = it.homeEffect.copy(scaleBlueValue = value),
                        lockEffect = it.lockEffect.copy(scaleBlueValue = value)
                    )
                Screen.NONE -> {
                    dataStore.updateData { it }
                }
            }
        }
    }

    private suspend fun update(block: (WallpaperEffects) -> WallpaperEffects) {
        dataStore.updateData { current -> block(current) }
    }
}
