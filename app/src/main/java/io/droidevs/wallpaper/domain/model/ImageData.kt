package io.droidevs.wallpaper.domain.model

data class ImageData(
    val bytes: ByteArray,
    val format: ImageFormat = ImageFormat.PNG,
    val metadata: ImageMetadata? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageData

        if (!bytes.contentEquals(other.bytes)) return false
        if (format != other.format) return false
        if (metadata != other.metadata) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bytes.contentHashCode()
        result = 31 * result + format.hashCode()
        result = 31 * result + (metadata?.hashCode() ?: 0)
        return result
    }

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