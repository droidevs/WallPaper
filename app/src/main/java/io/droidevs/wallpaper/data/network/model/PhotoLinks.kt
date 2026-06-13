package io.droidevs.wallpaper.data.network.model

import kotlinx.serialization.SerialName

data class PhotoLinks(
    val self: String,
    val html: String,
    val download: String,
    @SerialName("download_location") val downloadLocation: String
)