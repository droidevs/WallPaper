package io.droidevs.wallpaper.domain.model

sealed class ImageOperation {
    data class AdjustContrast(val value: Float) : ImageOperation()
    data class AdjustBrightness(val value: Float) : ImageOperation()
    data class Rotate(val degrees: Float) : ImageOperation()

    data class AdjustScale(
        val redScale: Float,
        val greenScale: Float,
        val blueScale: Float,
    ) : ImageOperation()

    data class AdjustSaturation(val value: Float) : ImageOperation()

    data class AdjustHue(
        val redHue: Float,
        val greenHue: Float,
        val blueHue: Float,
    ) : ImageOperation()

    data class AdjustBlur(val value: Float) : ImageOperation()
}