package io.droidevs.wallpaper.data.datastore

import io.droidevs.wallpaper.models.ContentFilter
import io.droidevs.wallpaper.models.PhotoColor
import io.droidevs.wallpaper.models.PhotoFilter
import io.droidevs.wallpaper.models.PhotoOrderBy
import io.droidevs.wallpaper.models.PhotoOrientation
import kotlinx.coroutines.flow.Flow

interface PhotoFilterPreference {
    // Basic operations
    suspend fun getPhotoFilter(): Flow<PhotoFilter>
    suspend fun updatePhotoFilter(photoFilter: PhotoFilter)
    suspend fun clearPhotoFilter()

    // Partial updates
    suspend fun updateOrderBy(orderBy: PhotoOrderBy)
    suspend fun updateContentFilter(contentFilter: ContentFilter)
    suspend fun updateColor(color: PhotoColor?)
    suspend fun updateOrientation(orientation: PhotoOrientation?)


    // Utility
    suspend fun hasNonDefaultFilter(): Boolean
    suspend fun getCurrentOrderBy(): PhotoOrderBy
    suspend fun getCurrentColor(): PhotoColor?
}