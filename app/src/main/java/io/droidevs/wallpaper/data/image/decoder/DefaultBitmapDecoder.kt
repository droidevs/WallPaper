package io.droidevs.wallpaper.data.image.decoder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import javax.inject.Inject

// Default implementations
class DefaultBitmapDecoder @Inject constructor() : BitmapDecoder {
    override fun decode(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            ?: throw IllegalArgumentException("Invalid image data")
    }
}