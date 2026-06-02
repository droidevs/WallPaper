package io.droidevs.wallpaper.data.image.internal


import android.graphics.ColorMatrix
import io.droidevs.wallpaper.domain.services.ColorMatrixUseCasesService
import javax.inject.Inject

class ColorMatrixUseCasesImpl @Inject constructor() : ColorMatrixUseCasesService{

    override fun createHueMatrix(red: Float, green: Float, blue: Float): FloatArray {
        return ColorMatrix(floatArrayOf(
            1f + red, green, blue, 0f, 0f,
            red, 1f + green, blue, 0f, 0f,
            red, green, 1f + blue, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        )).array
    }

    override fun createScaleMatrix(red: Float, green: Float, blue: Float): FloatArray {
        return ColorMatrix().apply {
            setScale(red, green, blue, 1f)
        }.array
    }

    override fun createBrightnessMatrix(value: Float): FloatArray {
        return ColorMatrix(floatArrayOf(
            1f, 0f, 0f, 0f, value,
            0f, 1f, 0f, 0f, value,
            0f, 0f, 1f, 0f, value,
            0f, 0f, 0f, 1f, 0f
        )).array
    }

    override fun createContrastBrightnessMatrix(contrast: Float, brightness: Float): FloatArray {
        val translate = (-0.5f * contrast + 0.5f + brightness / 255f) * 255f
        return ColorMatrix(
            floatArrayOf(
                contrast, 0f, 0f, 0f, translate,
                0f, contrast, 0f, 0f, translate,
                0f, 0f, contrast, 0f, translate,
                0f, 0f, 0f, 1f, 0f
            )
        ).array
    }

    override fun createContrastMatrix(value: Float): FloatArray {
        val translate = (-0.5f * value + 0.5f) * 255f
        return ColorMatrix(floatArrayOf(
            value, 0f, 0f, 0f, translate,
            0f, value, 0f, 0f, translate,
            0f, 0f, value, 0f, translate,
            0f, 0f, 0f, 1f, 0f
        )).array
    }

    override fun createSaturationMatrix(value: Float): FloatArray {
        return ColorMatrix().apply {
            setSaturation(value)
        }.array
    }

    override fun combineMatrices(matrices: List<FloatArray>): FloatArray {
        val combined = ColorMatrix()
        matrices.forEach { matrix ->
            combined.postConcat(ColorMatrix(matrix))
        }
        return combined.array
    }
}