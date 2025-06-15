package io.droidevs.wallpaper.data.network.model

import kotlinx.serialization.SerialName
import java.util.Date

data class PreviewPhoto(
    val id: String,
    @SerialName("created_at") val createdAt: Date,
    @SerialName("updated_at") val updatedAt: Date,
    val urls: PreviewPhotoUrls
)