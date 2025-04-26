package io.droidevs.wallpaper.data.network.dtos


import io.droidevs.wallpaper.data.network.model.PreviewPhoto
import io.droidevs.wallpaper.data.network.model.TopicCover

import kotlinx.serialization.SerialName
import java.util.*



data class TopicDto(
    val id: String,
    val slug: String,
    val title: String,
    val description: String?,
    @SerialName("published_at") val publishedAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("starts_at") val startsAt: String?,
    @SerialName("ends_at") val endsAt: String?,
    @SerialName("only_submissions_after") val onlySubmissionsAfter: Date?,
    val visibility: String,
    val featured: Boolean,
    @SerialName("total_photos") val totalPhotos: Int,
    val status: String,
    @SerialName("cover_photo") val coverPhoto: TopicCover,
    @SerialName("preview_photos") val previewPhotos: List<PreviewPhoto>
)









