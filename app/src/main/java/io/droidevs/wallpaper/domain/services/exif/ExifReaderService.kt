package io.droidevs.wallpaper.domain.services.exif

import io.droidevs.wallpaper.domain.model.ExifData
import io.droidevs.wallpaper.domain.model.ImageData
import io.droidevs.wallpaper.domain.services.source.ByteSource
import java.io.InputStream

interface ExifReaderService {

    fun readExif(byteSource: ByteSource): ExifData?
    fun readExif(byteSource: ByteArray): ExifData?
    fun readExif(inputStream: InputStream) : ExifData?

}