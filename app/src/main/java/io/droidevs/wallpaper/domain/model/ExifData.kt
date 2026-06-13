package io.droidevs.wallpaper.domain.model

// domain/model/ExifData.kt
data class ExifData(
    val orientation: ImageData.Orientation = ImageData.Orientation.NORMAL,
    val dateTaken: Long? = null,
    val gpsLatitude: Double? = null,
    val gpsLongitude: Double? = null,
    val make: String? = null,
    val model: String? = null,
    // Add other EXIF fields as needed
) {
}