package io.droidevs.wallpaper.domain.preferences

import io.droidevs.wallpaper.domain.model.ContentFilter
import io.droidevs.wallpaper.domain.model.PhotoColor
import io.droidevs.wallpaper.domain.model.PhotoFilter
import io.droidevs.wallpaper.domain.model.PhotoOrderBy
import io.droidevs.wallpaper.domain.model.PhotoOrientation
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow

interface PhotoFilterPreference {
    // Basic operations
    val photoFilter : Flow<Result<PhotoFilter, PreferenceError>>
    suspend fun updatePhotoFilter(photoFilter: PhotoFilter) : Result<PhotoFilter, PreferenceError>
    suspend fun clearPhotoFilter() : Result<PhotoFilter, PreferenceError>

    // Partial updates
    suspend fun updateOrderBy(orderBy: PhotoOrderBy) : Result<PhotoFilter, PreferenceError>
    suspend fun updateContentFilter(contentFilter: ContentFilter) : Result<PhotoFilter, PreferenceError>
    suspend fun updateColor(color: PhotoColor?) : Result<PhotoFilter, PreferenceError>
    suspend fun updateOrientation(orientation: PhotoOrientation?) : Result<PhotoFilter, PreferenceError>


    // Utility
    suspend fun hasNonDefaultFilter(): Result<Boolean, PreferenceError>
    suspend fun getCurrentOrderBy(): Flow<Result<PhotoOrderBy, PreferenceError>>
    suspend fun getCurrentColor(): Flow<Result<PhotoColor?, PreferenceError>>
}