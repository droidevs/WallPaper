package io.droidevs.wallpaper.data.image.internal

import android.graphics.Bitmap
import io.droidevs.wallpaper.data.image.decoder.BitmapDecoder
import io.droidevs.wallpaper.data.image.encoder.BitmapEncoder
import io.droidevs.wallpaper.domain.model.ExifData
import io.droidevs.wallpaper.domain.model.ImageData
import io.droidevs.wallpaper.domain.services.exif.ExifProcessorService

interface BitmapProcessorService {

    suspend fun processBitmap(
        image: ImageData,
        process: (bitmap: Bitmap, exifData: ExifData?) -> Bitmap
    ): ImageData

}

class BitmapProcessorServiceImpl(
    private val exifProcessor: ExifProcessorService,
    private val bitmapDecoder: BitmapDecoder,
    private val bitmapEncoder: BitmapEncoder
) : BitmapProcessorService {


    override suspend fun processBitmap(
        image: ImageData,
        process: (bitmap:Bitmap, exifData: ExifData?) -> Bitmap
    ): ImageData {
        val originalExif = exifProcessor.extractExif(image)

        val bitmap = bitmapDecoder.decode(image.bytes)
        val processed = process(bitmap,originalExif)
        val processedImage = ImageData(
            bytes = bitmapEncoder.encode(processed, image.format),
            format = image.format,
            metadata = image.metadata?.copy(
                width = processed.width,
                height = processed.height
            )
        )
        originalExif?.let {
            return exifProcessor.injectExif(processedImage, it)
        }?: return processedImage
    }
}