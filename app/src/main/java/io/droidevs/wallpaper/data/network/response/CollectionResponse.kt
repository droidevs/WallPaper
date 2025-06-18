package io.droidevs.wallpaper.data.network.response

import io.droidevs.wallpaper.data.network.dtos.CollectionDto
import io.droidevs.wallpaper.data.network.dtos.TopicDto
import kotlinx.serialization.SerialName

data class CollectionResponse(
    val results: List<CollectionDto>,
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int
)