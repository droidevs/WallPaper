package io.droidevs.wallpaper.data.system

import android.graphics.Bitmap
import android.graphics.Rect
import io.droidevs.wallpaper.data.image.internal.BitmapProcessorService
import io.droidevs.wallpaper.domain.model.ImageData
import io.droidevs.wallpaper.domain.services.DeviceMetricsService
import io.droidevs.wallpaper.domain.services.ImageResizerService
import io.droidevs.wallpaper.domain.model.ResizeMethod

class ImageResizerServiceImpl(
    private val bitmapProcessorService: BitmapProcessorService,
    private val deviceMetricsService: DeviceMetricsService,
) : ImageResizerService , BitmapProcessorService by bitmapProcessorService, DeviceMetricsService by deviceMetricsService {
    override suspend fun resize(image: ImageData, method: ResizeMethod): ImageData {
        val (width, height) = getScreenDimensions()

        return processBitmap(image){ bitmap,_ ->
            when (method) {
                ResizeMethod.CROP -> getResizedBitmap(bitmap, width, height)
                ResizeMethod.ZOOM -> getZoomedBitmap(bitmap, width, height)
                ResizeMethod.FIT_WIDTH -> getBitmapFitWidth(bitmap, width)
                ResizeMethod.FIT_HEIGHT -> getBitmapFitHeight(bitmap, height)
                ResizeMethod.NONE -> bitmap
            }
        }
    }

    private fun getBitmapFitWidth(bitmap: Bitmap, width: Int): Bitmap {
        val heightRatio = width.toFloat() / bitmap.width.toFloat()

        return getResizedBitmap(bitmap, width, (bitmap.height * heightRatio).toInt())
    }

    private fun getBitmapFitHeight(bitmap: Bitmap, height: Int): Bitmap {
        val widthRatio = height.toFloat() / bitmap.height.toFloat()

        return getResizedBitmap(bitmap, (bitmap.width * widthRatio).toInt(), height)
    }

    private fun getZoomedBitmap(bitmap: Bitmap, screenWidth: Int, screenHeight: Int): Bitmap {
        val bitmapRatio = bitmap.height.toFloat() / bitmap.width.toFloat()
        val screenRatio = screenHeight.toFloat() / screenWidth.toFloat()
        val scaleRatio = (bitmapRatio / screenRatio)

        var (newWidth, newHeight) = bitmap.width to bitmap.height
        if (bitmapRatio > screenRatio) {
            newHeight = (scaleRatio * bitmap.height).toInt()
        } else {
            newWidth = (scaleRatio * bitmap.width).toInt()
        }

        val gapX = (bitmap.width - newWidth) / 2
        val gapY = (bitmap.height - newHeight) / 2
        val centeredBitmap = Bitmap.createBitmap(bitmap, gapX, gapY, newWidth, newHeight)

        return getResizedBitmap(centeredBitmap, screenWidth, screenHeight)
    }


    private fun getResizedBitmap(
        bitmap: Bitmap,
        width: Int,
        height: Int,
    ): Bitmap {
        return cropAndScale(bitmap, width, height)
    }

    private fun cropAndScale(bitmap: Bitmap, reqWidth: Int, reqHeight: Int): Bitmap {
        val cropRect = calculateCropRect(bitmap, reqWidth, reqHeight)
        val cropped = Bitmap.createBitmap(bitmap, cropRect.left, cropRect.top, cropRect.width(), cropRect.height())
        bitmap.recycle() // Free original
        return Bitmap.createScaledBitmap(cropped, reqWidth, reqHeight, true)
    }

    private fun calculateCropRect(bitmap: Bitmap, displayWidth: Int, displayHeight: Int): Rect {
        // Calculate the aspect ratios
        val aspectRatio = displayWidth.toFloat() / displayHeight.toFloat()
        val bitmapAspectRatio = bitmap.width.toFloat() / bitmap.height.toFloat()

        // Fit the image entirely within the display
        val (cropWidth, cropHeight) = if (bitmapAspectRatio > aspectRatio) {
            // Bitmap is wider: scale height to fit, crop sides
            val width = (bitmap.height * aspectRatio).toInt()
            width to bitmap.height
        } else {
            // Bitmap is taller: scale width to fit, crop top/bottom
            bitmap.width to (bitmap.width / aspectRatio).toInt()
        }

        // Ensure there is no loss of any part of the image by calculating the crop from the center
        val left = (bitmap.width - cropWidth) / 2
        val top = (bitmap.height - cropHeight) / 2
        val right = left + cropWidth
        val bottom = top + cropHeight

        return Rect(left, top, right, bottom)
    }
}