package io.droidevs.wallpaper.domain.usecases

import io.droidevs.wallpaper.domain.model.ImageData
import io.droidevs.wallpaper.domain.preferences.ResizeMethodPreference
import io.droidevs.wallpaper.domain.services.ImageResizerService

class ImageResizerUseCase(
    private val resizeMethodPrefs: ResizeMethodPreference,
    private val imageResizer: ImageResizerService
) : SuspendingUseCase<ImageData, ImageData>() {
}