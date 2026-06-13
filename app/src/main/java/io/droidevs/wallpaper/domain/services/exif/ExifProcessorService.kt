package io.droidevs.wallpaper.domain.services.exif

import io.droidevs.wallpaper.domain.model.ExifData
import io.droidevs.wallpaper.domain.model.ImageData

interface ExifProcessorService {
    suspend fun extractExif(image: ImageData): ExifData?
    suspend fun injectExif(image: ImageData, exif: ExifData): ImageData
}

class ExifProcessorImpl(
    private val exifReader: ExifReaderService,
    private val exifWriter: ExifWriterService
) : ExifProcessorService{


    override suspend fun extractExif(image: ImageData): ExifData? {
        return exifReader.readExif(image.bytes)
    }


    override suspend fun injectExif(image: ImageData, exif: ExifData): ImageData {
        val bytes = exifWriter.writeExif(image.bytes, exif)

        return image.copy(
            bytes = bytes,
            metadata = image.metadata?.copy(exifData = exif)
        )
    }
}