package io.droidevs.wallpaper.domain.usecases

import io.droidevs.wallpaper.dispatchers.AppDispatchers
import io.droidevs.wallpaper.domain.model.ApplyEffectData
import io.droidevs.wallpaper.domain.model.ImageData
import io.droidevs.wallpaper.domain.services.ImageProcessorService
import io.droidevs.wallpaper.domain.model.Effect
import io.droidevs.wallpaper.domain.services.ColorMatrixUseCasesService
import javax.inject.Inject

class ApplyEffectUseCase @Inject constructor(
    private val imageProcessor: ImageProcessorService,
    private val appDispatchers: AppDispatchers,
    private val colorMatrixUseCases: ColorMatrixUseCasesService,
   //private val bitmapBlurProcessor: BitmapBlurProcessor,
) : SuspendingUseCase<ApplyEffectData, ImageData>(appDispatchers.io) , ColorMatrixUseCasesService by colorMatrixUseCases, /*BitmapBlurProcessor by bitmapBlurProcessor,*/ ImageProcessorService by imageProcessor{

    override suspend fun execute(parameters: ApplyEffectData): ImageData {
        val effect = parameters.effect
        val image = parameters.imageData

        val colorMatrices = mutableListOf<FloatArray>().apply {
            add(createContrastMatrix(effect.contrastValue))
            add(createBrightnessMatrix(effect.brightnessValue))
            add(createSaturationMatrix(effect.saturationValue))
            add(createHueMatrix(
                effect.hueRedValue,
                effect.hueGreenValue,
                effect.hueBlueValue
            ))
            add(createScaleMatrix(
                effect.scaleRedValue,
                effect.scaleGreenValue,
                effect.scaleBlueValue
            ))
        }


        // 2. Apply all color operations in one pass
        val processedImage = applyColorMatrix(image, colorMatrices)

        // 3. Apply blur separately (non-matrix operation)
        return if (effect.blurValue > 0) {
            applyBlur(processedImage, effect.blurValue)
        } else {
            processedImage
        }
    }
}