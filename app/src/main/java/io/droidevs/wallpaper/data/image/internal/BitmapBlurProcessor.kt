package io.droidevs.wallpaper.data.image.internal

import android.graphics.Bitmap
import io.droidevs.wallpaper.core.utils.StackBlur
import javax.inject.Inject



interface BitmapBlurProcessor {

    fun blur(bitmap: Bitmap, radius: Float) : Bitmap
}

class BitmapBlurProcessorImpl @Inject constructor() : BitmapBlurProcessor {
    override fun blur(bitmap: Bitmap, radius: Float): Bitmap {
        return if (radius <= 0) bitmap else {
            StackBlur().blurRgb(bitmap, radius.toInt())
            bitmap
        }
    }
}