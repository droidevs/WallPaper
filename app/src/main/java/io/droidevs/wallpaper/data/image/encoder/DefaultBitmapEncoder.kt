package io.droidevs.wallpaper.data.image.encoder

import android.graphics.Bitmap
import io.droidevs.wallpaper.domain.model.ImageData
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class DefaultBitmapEncoder @Inject constructor() : BitmapEncoder {
    override fun encode(bitmap: Bitmap, format: ImageData.ImageFormat): ByteArray {
        val stream = ByteArrayOutputStream()
        val compressFormat = when (format) {
            ImageData.ImageFormat.JPEG -> Bitmap.CompressFormat.JPEG
            ImageData.ImageFormat.PNG -> Bitmap.CompressFormat.PNG
            ImageData.ImageFormat.WEBP -> Bitmap.CompressFormat.WEBP
        }
        bitmap.compress(compressFormat, 100, stream)
        return stream.toByteArray()
    }
}