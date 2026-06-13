package io.droidevs.wallpaper.data.image.decoder

import android.graphics.Bitmap

interface BitmapDecoder {
    fun decode(bytes: ByteArray): Bitmap
}