package io.droidevs.wallpaper.domain.services

interface ColorMatrixUseCasesService {
    fun createHueMatrix(red: Float, green: Float, blue: Float): FloatArray
    fun createScaleMatrix(red: Float, green: Float, blue: Float): FloatArray
    fun createBrightnessMatrix(value: Float): FloatArray
    fun createContrastBrightnessMatrix(contrast: Float, brightness: Float): FloatArray
    fun createContrastMatrix(value: Float): FloatArray
    fun createSaturationMatrix(value: Float): FloatArray
    fun combineMatrices(matrices: List<FloatArray>): FloatArray
}
