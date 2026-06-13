package io.droidevs.wallpaper.data.datastore


import androidx.compose.runtime.currentComposer
import androidx.datastore.core.DataStore
import io.droidevs.wallpaper.data.datastore.delegate.PreferenceDelegate
import io.droidevs.wallpaper.data.datastore.delegate.ProtoPreferenceDelegate
import io.droidevs.wallpaper.data.datastore.delegate.ProtoReadDelegate
import io.droidevs.wallpaper.data.datastore.delegate.ProtoReadDelegateImpl
import io.droidevs.wallpaper.data.datastore.delegate.ProtoWriteDelegate
import io.droidevs.wallpaper.data.datastore.delegate.ProtoWriteDelegateImpl
import io.droidevs.wallpaper.domain.preferences.PhotoFilterPreference
import io.droidevs.wallpaper.domain.model.ContentFilter
import io.droidevs.wallpaper.domain.model.PhotoColor
import io.droidevs.wallpaper.domain.model.PhotoFilter
import io.droidevs.wallpaper.domain.model.PhotoOrderBy
import io.droidevs.wallpaper.domain.model.PhotoOrientation
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.PreferenceError
import io.droidevs.wallpaper.domain.result.map
import io.droidevs.wallpaper.domain.result.onSuccessWithResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class PhotoFilterDataStore @Inject constructor(
    private val dataStore: DataStore<PhotoFilter>,
) : PhotoFilterPreference, ProtoWriteDelegate<PhotoFilter> by ProtoWriteDelegateImpl(dataStore), ProtoReadDelegate<PhotoFilter> by ProtoReadDelegateImpl(dataStore) {

    private val delegate by lazy {
        ProtoPreferenceDelegate(
            dataStore = dataStore,
            defaultValue = PhotoFilter.DEFAULT
        )
    }
    override val photoFilter : Flow<Result<PhotoFilter, PreferenceError>>
    = delegate.flow

    override suspend fun updatePhotoFilter(photoFilter: PhotoFilter): Result<PhotoFilter, PreferenceError> {
        return delegate.set(photoFilter)
    }

    override suspend fun clearPhotoFilter(): Result<PhotoFilter, PreferenceError> {
        return delegate.set(PhotoFilter.DEFAULT)
    }

    override suspend fun updateOrderBy(orderBy: PhotoOrderBy): Result<PhotoFilter, PreferenceError> {
        return set { current->
            current.copy(orderBy = orderBy)
        }
    }

    override suspend fun updateContentFilter(contentFilter: ContentFilter): Result<PhotoFilter, PreferenceError> {
        return set {current ->
            current.copy(contentFilter = contentFilter)
        }
    }

    override suspend fun updateColor(color: PhotoColor?): Result<PhotoFilter, PreferenceError> {
        return set { current ->
            current.copy(color = color)
        }
    }

    override suspend fun updateOrientation(orientation: PhotoOrientation?): Result<PhotoFilter, PreferenceError> {
        return set { current ->
            current.copy(orientation = orientation)
        }
    }

    override suspend fun hasNonDefaultFilter(): Result<Boolean, PreferenceError> {
        return delegate.get()
            .map {
                it != PhotoFilter.DEFAULT
            }
    }

    override suspend fun getCurrentOrderBy(): Flow<Result<PhotoOrderBy, PreferenceError>> {
        return get(
            defaultValue = PhotoFilter.DEFAULT.orderBy,
            read = { it.orderBy }
        )
    }

    override suspend fun getCurrentColor(): Flow<Result<PhotoColor?, PreferenceError>> {
        return get(
            defaultValue = PhotoFilter.DEFAULT.color,
            read = { it.color }
        )
    }
}