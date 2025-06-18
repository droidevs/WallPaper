package io.droidevs.wallpaper.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionCover(
    @SerialName("width")
    val width : Int,
    @SerialName("height")
    val height : Int,
    @SerialName("links")
    val links : CoverLinks
)