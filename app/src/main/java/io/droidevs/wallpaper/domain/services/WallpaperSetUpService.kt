package io.droidevs.wallpaper.domain.services

import io.droidevs.wallpaper.domain.model.Effect
import io.droidevs.wallpaper.domain.model.ImageData
import io.droidevs.wallpaper.domain.model.Screen
import io.droidevs.wallpaper.domain.model.ScreenDimens
import io.droidevs.wallpaper.domain.model.WallpaperSetUpData

interface WallpaperSetUpService {


    suspend fun applyWallpaper(image: ImageData) : Boolean

    suspend fun applyWallpaper(image: ImageData, dimens: ScreenDimens): Boolean

    suspend fun applyWallpaper(image: ImageData, screen: Screen) : Boolean

    suspend fun applyWallpaper(image : ImageData, screen: Screen, dimens: ScreenDimens) : Boolean

}

