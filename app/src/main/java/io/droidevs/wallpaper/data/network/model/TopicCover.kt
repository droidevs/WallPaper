package io.droidevs.wallpaper.data.network.model

import kotlinx.serialization.SerialName
import java.util.Date



data class TopicCover(
    val id: String,
    @SerialName("created_at") val createdAt: Date,
    @SerialName("updated_at") val updatedAt: Date,
    @SerialName("promoted_at") val promotedAt: Date?,
    val width: Int,
    val height: Int,
    val color: String,
    @SerialName("blur_hash") val blurHash: String?,
    val description: String?,
    @SerialName("alt_description") val altDescription: String?,
    val urls: PhotoUrls,
    val links: PhotoLinks
)