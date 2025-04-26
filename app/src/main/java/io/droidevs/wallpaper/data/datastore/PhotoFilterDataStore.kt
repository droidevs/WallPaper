package io.droidevs.wallpaper.data.datastore


import androidx.datastore.core.DataStore
import io.droidevs.wallpaper.models.ContentFilter
import io.droidevs.wallpaper.models.PhotoColor
import io.droidevs.wallpaper.models.PhotoFilter
import io.droidevs.wallpaper.models.PhotoOrderBy
import io.droidevs.wallpaper.models.PhotoOrientation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class PhotoFilterDataStore @Inject constructor(
    private val dataStore: DataStore<PhotoFilter>,
) : PhotoFilterPreference {

    override suspend fun getPhotoFilter(): Flow<PhotoFilter> {
        return dataStore.data.catch { emit(PhotoFilter.DEFAULT) }
    }

    override suspend fun updatePhotoFilter(photoFilter: PhotoFilter) {
        dataStore.updateData { photoFilter }
    }

    override suspend fun clearPhotoFilter() {
        dataStore.updateData { PhotoFilter.DEFAULT }
    }

    override suspend fun updateOrderBy(orderBy: PhotoOrderBy) {
        dataStore.updateData { current ->
            current.copy(orderBy = orderBy)
        }
    }

    override suspend fun updateContentFilter(contentFilter: ContentFilter) {
        dataStore.updateData { current ->
            current.copy(contentFilter = contentFilter)
        }
    }

    override suspend fun updateColor(color: PhotoColor?) {
        dataStore.updateData { current ->
            current.copy(color = color)
        }
    }

    override suspend fun updateOrientation(orientation: PhotoOrientation?) {
        dataStore.updateData { current ->
            current.copy(orientation = orientation)
        }
    }

    override suspend fun hasNonDefaultFilter(): Boolean {
        return dataStore.data.first() != PhotoFilter.DEFAULT
    }

    override suspend fun getCurrentOrderBy(): PhotoOrderBy {
        return dataStore.data.first().orderBy
    }

    override suspend fun getCurrentColor(): PhotoColor? {
        return dataStore.data.first().color
    }
}