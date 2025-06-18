package io.droidevs.wallpaper.data.network.dtos

import io.droidevs.wallpaper.data.network.model.CollectionCover
import io.droidevs.wallpaper.data.network.model.CollectionUrls
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionDto(
    val id : Int,
    val name : String,
    val description : String,
    @SerialName("published_at")
    val publishedAt : String,
    @SerialName("updated_at")
    val updatedAt : String,
    @SerialName("total_photos")
    val totalPhotos : Int,

    @SerialName("cover_photo")
    val cover : CollectionCover,
    @SerialName("urls")
    val links : CollectionUrls
)