package io.droidevs.wallpaper.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Effect(
    val blurValue: Float = 0f,           // No blur
    val brightnessValue: Float = 1f,     // Normal brightness
    val contrastValue: Float = 1f,       // Normal contrast
    val saturationValue: Float = 1f,     // Normal saturation
    val hueRedValue: Float = 0f,         // No hue shift for red
    val hueGreenValue: Float = 0f,       // No hue shift for green
    val hueBlueValue: Float = 0f,        // No hue shift for blue
    val scaleRedValue: Float = 1f,       // Normal red scaling
    val scaleGreenValue: Float = 1f,     // Normal green scaling
    val scaleBlueValue: Float = 1f       // Normal blue scaling
)


fun Effect.needsColorMatrix(): Boolean {
    return brightnessValue != 1f ||
            contrastValue != 1f ||
            saturationValue != 1f ||
            hueRedValue != 0f ||
            hueGreenValue != 0f ||
            hueBlueValue != 0f ||
            scaleRedValue != 1f ||
            scaleGreenValue != 1f ||
            scaleBlueValue != 1f
}
