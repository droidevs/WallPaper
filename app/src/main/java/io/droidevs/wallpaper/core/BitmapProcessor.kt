package io.droidevs.wallpaper.core

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import com.google.android.renderscript.Toolkit
import io.droidevs.wallpaper.core.constant.Misc
import io.droidevs.wallpaper.core.utils.MatrixUtils.multiplyMatrices
import io.droidevs.wallpaper.core.utils.StackBlur

import io.droidevs.wallpaper.domain.model.Effect

object BitmapProcessor {


    private fun getColorMatrix(effect: Effect): ColorMatrix {
        // Base identity matrix
        val colorMatrix = ColorMatrix()

        val scaleMatrix = ColorMatrix().apply {
            setScale(effect.scaleRedValue, effect.scaleGreenValue, effect.scaleBlueValue, 1f)
        }.array

        val saturationMatrix = ColorMatrix().apply {
            setSaturation(effect.saturationValue)
        }.array


        val translate = (-0.5f * effect.contrastValue + 0.5f + effect.brightnessValue / 255f) * 255f
        val contrastMatrix = floatArrayOf(
            effect.contrastValue, 0f, 0f, 0f, translate,
            0f, effect.contrastValue, 0f, 0f, translate,
            0f, 0f, effect.contrastValue, 0f, translate,
            0f, 0f, 0f, 1f, 0f
        )


        // Manually combine the matrices
        val combinedMatrix = FloatArray(20) // Array to hold the combined matrix
        val tempMatrix = FloatArray(20) // Temporary array for intermediate results


        // Multiply the result with the saturation matrix and store in tempMatrix
        multiplyMatrices(combinedMatrix, saturationMatrix, tempMatrix)

        // Multiply the result with the scale matrix and store in combinedMatrix
        multiplyMatrices(tempMatrix, scaleMatrix, combinedMatrix)

        // Multiply the result with the contrast matrix and store in tempMatrix
        multiplyMatrices(combinedMatrix, contrastMatrix, tempMatrix)

        // Apply hue rotation
        if (effect.hueRedValue != 0f || effect.hueGreenValue != 0f || effect.hueBlueValue != 0f) {
            val hueMatrix = getHueMatrix(effect.hueRedValue, effect.hueGreenValue, effect.hueBlueValue)
            multiplyMatrices(combinedMatrix, hueMatrix, tempMatrix)
        }
        colorMatrix.set(tempMatrix)
        return colorMatrix
    }

    private fun getHueMatrix(hueRed: Float, hueGreen: Float, hueBlue: Float): FloatArray {
        return floatArrayOf(
            1f + hueRed, hueGreen, hueBlue, 0f, 0f,
            hueRed, 1f + hueGreen, hueBlue, 0f, 0f,
            hueRed, hueGreen, 1f + hueBlue, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        )
    }


    fun Bitmap.blur(radius: Float): Bitmap {
        if (radius <= 0f) return this
        return Toolkit.blur(this, radius.toInt())
    }

    fun applyEffect(bitmap: Bitmap, effect: Effect): Bitmap {
        // Apply blur first


        // Apply color matrix effects
        return if (effect.needsColorMatrix()) {
            val matrix = getColorMatrix(effect)
            val paint = Paint()
            paint.colorFilter = ColorMatrixColorFilter(matrix)
            val bitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config ?: Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.drawBitmap(bitmap, 0f, 0f, paint)
            try {
                StackBlur().blurRgb(bitmap,effect.blurValue.times(Misc.BLUR_TIMES).toInt())
            } catch (_: Exception) {
            }
            bitmap
        } else {
            bitmap.blur(effect.blurValue)
        }
    }

    private fun Effect.needsColorMatrix(): Boolean {
        return brightnessValue != 1f ||
                contrastValue != 1f ||
                saturationValue != 1f ||
                hueRedValue != 0f ||
                hueGreenValue != 0f ||
                hueBlueValue != 0f ||
                scaleRedValue != 1f ||
                scaleGreenValue != 1f ||
                scaleBlueValue != 1f
    }


    
}