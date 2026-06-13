package io.droidevs.wallpaper.util

import android.graphics.Bitmap
import androidx.palette.graphics.Palette

object BitmapUtils {
    fun Bitmap.generatePalette(): Palette {
        return Palette.from(this).generate()
    }
}
