package io.droidevs.wallpaper.domain.usecases

import io.droidevs.wallpaper.domain.model.ImageData
import io.droidevs.wallpaper.domain.model.ResizeMethod
import io.droidevs.wallpaper.domain.preferences.ResizeMethodPreference
import io.droidevs.wallpaper.domain.services.ImageResizerService
import kotlinx.coroutines.flow.first

class ImageResizerUseCase(
    private val resizeMethodPrefs: ResizeMethodPreference,
    private val imageResizer: ImageResizerService
) : SuspendingUseCase<ImageData, ImageData>() {

    override suspend fun execute(parameters: ImageData): ImageData {
        val result = resizeMethodPrefs.resizedMethod.first()
        val method = result.getOrNull() ?: ResizeMethod.NONE
        return imageResizer.resize(parameters, method)
    }
}