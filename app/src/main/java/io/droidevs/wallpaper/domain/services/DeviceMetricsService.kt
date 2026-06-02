package io.droidevs.wallpaper.domain.services

import io.droidevs.wallpaper.domain.LocalWallpaper

interface DeviceMetricsService {
    fun getScreenDimensions(): Pair<Int, Int>

    fun getScreenDensity(): String

    fun getRefreshRate(): Float

    fun getOrientation(): String

    fun isLandscape(): Boolean

    fun isPortrait(): Boolean

    fun isDeviceLocked(): Boolean

    fun isDeviceSleeping(): Boolean

    fun isFittingScreen(wallpaper: LocalWallpaper): Boolean
}