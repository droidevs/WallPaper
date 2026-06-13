package io.droidevs.wallpaper.data.image.exif

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.exifinterface.media.ExifInterface
import io.droidevs.wallpaper.domain.model.ExifData
import io.droidevs.wallpaper.domain.services.exif.ExifReaderService
import io.droidevs.wallpaper.domain.services.source.ByteSource
import java.io.File
import java.io.InputStream

class AndroidExifReader : AndroidExifIO(), ExifReaderService {

    override fun readExif(byteSource: ByteSource): ExifData? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                byteSource.openStream().use { stream ->
                    readExifFromStream(stream)
                }
            } else {
                val tempFile = File.createTempFile("exif_tmp", null)
                try {
                    byteSource.openStream().use { it.copyTo(tempFile.outputStream()) }
                    readExifFromFile(tempFile)
                } finally {
                    tempFile.delete()
                }
            }
        } catch (e: Exception) {
            null
        }
    }

    override fun readExif(byteSource: ByteArray): ExifData? {
        return try {
            val tempFile = File.createTempFile("exif_tmp", null)
            try {
                tempFile.writeBytes(byteSource)
                readExifFromFile(tempFile)
            } finally {
                tempFile.delete()
            }
        } catch (e: Exception) {
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun readExif(inputStream: InputStream): ExifData? {
        return try {
            readExifFromStream(inputStream)
        } catch (e: Exception) {
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun readExifFromStream(stream: InputStream): ExifData {
        val exif = ExifInterface(stream)
        return parseExif(exif)
    }

    private fun readExifFromFile(file: File): ExifData {
        val exif = ExifInterface(file.absolutePath)
        return parseExif(exif)
    }
}
