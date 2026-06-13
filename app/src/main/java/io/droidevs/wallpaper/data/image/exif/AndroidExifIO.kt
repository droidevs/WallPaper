package io.droidevs.wallpaper.data.image.exif

import androidx.exifinterface.media.ExifInterface
import io.droidevs.wallpaper.domain.model.ExifData
import io.droidevs.wallpaper.domain.model.ImageData


open class AndroidExifIO {

    protected fun parseExif(exif: ExifInterface): ExifData {
        return ExifData(
            orientation = when (exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )) {
                ExifInterface.ORIENTATION_ROTATE_90  -> ImageData.Orientation.ROTATE_90
                ExifInterface.ORIENTATION_ROTATE_180 -> ImageData.Orientation.ROTATE_180
                ExifInterface.ORIENTATION_ROTATE_270 -> ImageData.Orientation.ROTATE_270
                else -> ImageData.Orientation.NORMAL
            },
            dateTaken = exif.getAttributeDouble(ExifInterface.TAG_DATETIME, -1.0)
                .takeIf { it != -1.0 }?.toLong(),
        )
    }
}