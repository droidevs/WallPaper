package io.droidevs.wallpaper.ui.ex

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import io.droidevs.wallpaper.domain.model.ImageData
import java.io.ByteArrayOutputStream

// presentation/extensions/ImageExtensions.kt
fun ImageView.loadImage(imageData: ImageData, imageProcessor: ImageProcessor) {
    val bitmap = BitmapFactory.decodeByteArray(imageData.bytes, 0, imageData.bytes.size)
    setImageBitmap(bitmap)
}

fun ImageData.toBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

fun Bitmap.toImageData(format: ImageData.ImageFormat = ImageData.ImageFormat.PNG): ImageData {
    val stream = ByteArrayOutputStream()
    compress(
        when (format) {
            ImageData.ImageFormat.JPEG -> Bitmap.CompressFormat.JPEG
            ImageData.ImageFormat.PNG -> Bitmap.CompressFormat.PNG
            ImageData.ImageFormat.WEBP -> Bitmap.CompressFormat.WEBP
        },
        100,
        stream
    )
    return ImageData(
        bytes = stream.toByteArray(),
        format = format,
        metadata = ImageData.ImageMetadata(width, height)
    )
}