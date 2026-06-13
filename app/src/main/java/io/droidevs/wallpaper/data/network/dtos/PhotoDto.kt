package io.droidevs.wallpaper.data.network.dtos

import io.droidevs.wallpaper.data.network.model.PhotoUrls
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
data class PhotoDto(

    @SerialName("id")
    val id: String,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String,

    @SerialName("width")
    val width: Int,

    @SerialName("height")
    val height: Int,

    @SerialName("color")
    val color: String,

    @SerialName("blur_hash")
    val blurHash: String,

    @SerialName("downloads")
    val downloads: Int,

    @SerialName("likes")
    val likes: Int,

    @SerialName("description")
    val description: String?,

    @SerialName("urls")
    val urls: PhotoUrls,
)
