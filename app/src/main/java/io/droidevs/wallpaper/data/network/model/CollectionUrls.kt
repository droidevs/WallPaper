package io.droidevs.wallpaper.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CollectionUrls(

    @SerialName("full")
    val full: String,
)