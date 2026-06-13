package io.droidevs.wallpaper.data.image.exif

import androidx.exifinterface.media.ExifInterface
import android.os.Build
import androidx.annotation.RequiresApi

import io.droidevs.wallpaper.domain.model.ExifData
import io.droidevs.wallpaper.domain.model.ImageData
import io.droidevs.wallpaper.domain.services.exif.ExifWriterService
import io.droidevs.wallpaper.domain.services.source.ByteSource
import java.io.File
import javax.inject.Inject


class AndroidExifWriter @Inject constructor() : AndroidExifIO() ,ExifWriterService{

    override fun writeExif(byteSource: ByteSource, exifData: ExifData): ByteArray {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            writeExif(byteSource.toByteArray(), exifData)
        } else {
            modifyExifViaTempFile(byteSource.toByteArray(), exifData)
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun writeExif(imageBytes: ByteArray, exifData: ExifData): ByteArray {
        val tempFile = java.io.File.createTempFile("exif", ".tmp").apply {
            writeBytes(imageBytes)
        }
        modifyExifViaTempFile(tempFile, exifData)
        return tempFile.readBytes()
    }

    private fun modifyExifViaTempFile(imageBytes: ByteArray, exifData: ExifData): ByteArray {
        val tempFile = java.io.File.createTempFile("exif", ".tmp").apply {
            writeBytes(imageBytes)
        }
        modifyExifViaTempFile(tempFile, exifData)
        return tempFile.readBytes()
    }

    private fun modifyExifViaTempFile(file: File, exifData: ExifData) {
        val exif = ExifInterface(file.absolutePath).apply {
            setAttribute(
                ExifInterface.TAG_ORIENTATION,
                when (exifData.orientation) {
                    ImageData.Orientation.ROTATE_90  -> ExifInterface.ORIENTATION_ROTATE_90.toString()
                    ImageData.Orientation.ROTATE_180 -> ExifInterface.ORIENTATION_ROTATE_180.toString()
                    ImageData.Orientation.ROTATE_270 -> ExifInterface.ORIENTATION_ROTATE_270.toString()
                    else -> ExifInterface.ORIENTATION_NORMAL.toString()
                }
            )
            // Set other EXIF attributes
            saveAttributes()
        }
    }

}