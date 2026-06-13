package io.droidevs.wallpaper.data.image

import android.graphics.Color
import android.graphics.ColorMatrix
import io.droidevs.wallpaper.data.image.decoder.BitmapDecoder
import io.droidevs.wallpaper.data.image.internal.BitmapBlurProcessor
import io.droidevs.wallpaper.data.image.internal.BitmapManipulator
import io.droidevs.wallpaper.data.image.internal.BitmapProcessorService
import io.droidevs.wallpaper.domain.model.ImageData
import io.droidevs.wallpaper.domain.model.PaletteColors
import io.droidevs.wallpaper.domain.model.RectDomain
import io.droidevs.wallpaper.domain.services.ImageProcessorService
import javax.inject.Inject

class AndroidImageProcessor @Inject constructor(
    //private val context: Context,
    private val bitmapDecoder: BitmapDecoder,
    private val bitmapProcessor: BitmapProcessorService,
    private val bitmapManipulator: BitmapManipulator,
    private val blurProcessor: BitmapBlurProcessor
) : ImageProcessorService, BitmapManipulator by bitmapManipulator ,BitmapProcessorService by bitmapProcessor, BitmapBlurProcessor by blurProcessor {
    override suspend fun applyColorMatrix(image: ImageData, colorMatrix: FloatArray): ImageData {
        return processBitmap(image){ bitmap,_->
            applyColorMatrix(bitmap, ColorMatrix(colorMatrix))
        }
    }

    override suspend fun applyColorMatrix(image: ImageData, colorMatrix: List<FloatArray>): ImageData {
        return processBitmap(image){ bitmap,_->
            applyColorMatrix(bitmap,
                colorMatrix.map {
                    ColorMatrix(it)
                }
            )
        }
    }


    override suspend fun applyHue(
        image: ImageData,
        red: Float,
        green: Float,
        blue: Float
    ): ImageData {
        return processBitmap(image){ bitmap,_->
            applyHue(bitmap, red, green, blue)
        }
    }

    override suspend fun applyScale(
        image: ImageData,
        red: Float,
        green: Float,
        blue: Float
    ): ImageData {
        return processBitmap(image){ bitmap,_->
            applyScale(bitmap, red, green, blue)
        }
    }

    override suspend fun applyBlur(image: ImageData, radius: Float): ImageData {
        return processBitmap(image) { bitmap, _ ->
            blur(bitmap, radius)
        }
    }

    override suspend fun applyContrast(image: ImageData, contrast: Float): ImageData {
        return processBitmap(image) { bitmap,_ ->
            applyContrast(bitmap, contrast)
        }
    }

    override suspend fun applySaturation(image: ImageData, saturation: Float): ImageData {
        return processBitmap(image) { bitmap,_ ->
            applySaturation(bitmap, saturation)
        }
    }

    override suspend fun applyBrightness(image: ImageData, brightness: Float): ImageData {
        return processBitmap(image) { bitmap,_ ->
            applyBrightness(bitmap, brightness)
        }
    }

    override suspend fun crop(image: ImageData, rect: RectDomain): ImageData {
        return processBitmap(image) { bitmap,_ ->
            cropBitmap(
                bitmap,
                android.graphics.Rect(rect.left, rect.top, rect.right, rect.bottom)
            )
        }
    }

    override suspend fun correctOrientation(image: ImageData): ImageData {

        return processBitmap(image) { bitmap, exif ->
            if (exif?.orientation == ImageData.Orientation.NORMAL) {
                bitmap
            } else {
                when (exif?.orientation) {
                    ImageData.Orientation.ROTATE_90 -> rotateImage(bitmap, 90f)
                    ImageData.Orientation.ROTATE_180 -> rotateImage(bitmap, 180f)
                    ImageData.Orientation.ROTATE_270 -> rotateImage(bitmap, 270f)
                    else -> bitmap
                }
            }
        }
    }

    override suspend fun rotate(image: ImageData, degrees: Float): ImageData {
        return processBitmap(image) { bitmap,_ ->
            rotateImage(bitmap, degrees)
        }
    }

    override suspend fun generatePalette(image: ImageData): PaletteColors {
        val bitmap = bitmapDecoder.decode(image.bytes)
        val palette = generatePalette(bitmap)
        return PaletteColors(
            dominantColor = palette.getDominantColor(Color.BLACK),
            vibrantColor = palette.getVibrantColor(Color.BLACK),
            mutedColor = palette.getMutedColor(Color.BLACK),
            darkVibrantColor = palette.getDarkVibrantColor(Color.BLACK),
            lightVibrantColor = palette.getLightVibrantColor(Color.BLACK)
        )
    }

    override suspend fun estimateBrightness(image: ImageData, pixelSpacing: Int): Float {
        val bitmap = bitmapDecoder.decode(image.bytes)
        return calculateBrightnessEstimate(bitmap, pixelSpacing)
    }

}