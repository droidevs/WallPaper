package io.droidevs.wallpaper.data.network.response

import io.droidevs.wallpaper.data.network.dtos.PhotoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PhotoResponse(
    val results: List<PhotoDto>,
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int
)