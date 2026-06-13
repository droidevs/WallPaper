package io.droidevs.wallpaper.domain.services

import io.droidevs.wallpaper.domain.model.ImageData
import io.droidevs.wallpaper.domain.model.ResizeMethod

interface ImageResizerService {
    suspend fun resize(bitmap: ImageData, method: ResizeMethod): ImageData
}