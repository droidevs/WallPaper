package io.droidevs.wallpaper.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class PhotoFilter(
    val orderBy: PhotoOrderBy = PhotoOrderBy.RELEVANT,
    val contentFilter: ContentFilter = ContentFilter.LOW,
    val color: PhotoColor? = null,
    val orientation: PhotoOrientation? = null
) {
    fun toQueryMap(): Map<String, String> {
        return buildMap {
            put("order_by", orderBy.apiValue)
            put("content_filter", contentFilter.apiValue)
            color?.let { put("color", it.apiValue) }
            orientation?.let { put("orientation", it.apiValue) }
        }
    }

    companion object {
        val DEFAULT = PhotoFilter()
    }
}