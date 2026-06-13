package io.droidevs.wallpaper.domain.services

import android.graphics.ColorMatrix
import io.droidevs.wallpaper.domain.model.ImageData
import io.droidevs.wallpaper.domain.model.PaletteColors
import io.droidevs.wallpaper.domain.model.RectDomain

// domain/service/ImageProcessor.kt
interface ImageProcessorService {

    suspend fun applyColorMatrix(image: ImageData, colorMatrix: FloatArray): ImageData

    suspend fun applyColorMatrix(image: ImageData, colorMatrix: List<FloatArray>): ImageData

    suspend fun applyHue(image: ImageData, red: Float, green: Float, blue: Float): ImageData
    suspend fun applyScale(image: ImageData, red: Float, green: Float, blue: Float): ImageData
    suspend fun applyBlur(image: ImageData, radius: Float): ImageData
    suspend fun applyContrast(image: ImageData, contrast: Float): ImageData
    suspend fun applySaturation(image: ImageData, saturation: Float): ImageData
    suspend fun applyBrightness(image: ImageData, brightness: Float): ImageData
    suspend fun crop(image: ImageData, rect: RectDomain): ImageData
    suspend fun correctOrientation(image: ImageData): ImageData
    suspend fun rotate(image: ImageData, degrees: Float): ImageData
    suspend fun generatePalette(image: ImageData): PaletteColors
    suspend fun estimateBrightness(image: ImageData, pixelSpacing: Int = 20): Float
}