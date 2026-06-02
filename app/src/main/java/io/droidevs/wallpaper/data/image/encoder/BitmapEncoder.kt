package io.droidevs.wallpaper.data.image.encoder

import android.graphics.Bitmap
import io.droidevs.wallpaper.domain.model.ImageData

interface BitmapEncoder {
    fun encode(bitmap: Bitmap, format: ImageData.ImageFormat): ByteArray
}