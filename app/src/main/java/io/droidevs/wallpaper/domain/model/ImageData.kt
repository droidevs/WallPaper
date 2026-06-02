package io.droidevs.wallpaper.domain.model

data class ImageData(
    val bytes: ByteArray,
    val format: ImageFormat = ImageFormat.PNG,
    val metadata: ImageMetadata? = null,
) {
    enum class ImageFormat { PNG, JPEG, WEBP }
    data class ImageMetadata(
        val width: Int,
        val height: Int,
        val exifData: ExifData? = null,
    )

    enum class Orientation {
        NORMAL, ROTATE_90, ROTATE_180, ROTATE_270
    }
}