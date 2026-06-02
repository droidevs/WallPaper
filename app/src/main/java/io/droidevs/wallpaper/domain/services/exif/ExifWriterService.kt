package io.droidevs.wallpaper.domain.services.exif

import io.droidevs.wallpaper.domain.model.ExifData
import io.droidevs.wallpaper.domain.services.source.ByteSource
import java.io.File

interface ExifWriterService {
    fun writeExif(byteSource: ByteSource, exifData: ExifData) : ByteArray

    fun writeExif(imageBytes: ByteArray, exifData: ExifData): ByteArray

//    fun modifyExifViaTempFile(imageBytes: ByteArray, exifData: ExifData): ByteArray
//
//    fun modifyExifViaTempFile(file: File, exifData: ExifData)
}