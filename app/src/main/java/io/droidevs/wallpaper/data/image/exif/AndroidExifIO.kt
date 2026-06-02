package io.droidevs.wallpaper.data.image.exif

import androidx.exifinterface.media.ExifInterface
import io.droidevs.wallpaper.domain.model.ExifData


open class AndroidExifIO {

    protected fun parseExif(exif: ExifInterface): ExifData {
        return ExifData(
            orientation = when (exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )) {
                ExifInterface.ORIENTATION_ROTATE_90 -> ExifData.Orientation.ROTATE_90
                ExifInterface.ORIENTATION_ROTATE_180 -> ExifData.Orientation.ROTATE_180
                ExifInterface.ORIENTATION_ROTATE_270 -> ExifData.Orientation.ROTATE_270
                else -> ExifData.Orientation.NORMAL
            },
            dateTaken = exif.getAttributeDouble(ExifInterface.TAG_DATETIME, -1.0).takeIf { it != -1.0 } as Long,
            // Parse other EXIF attributes
        )
    }

}