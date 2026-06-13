package io.droidevs.wallpaper.data.network.photo

import io.droidevs.wallpaper.data.network.dtos.PhotoDto
import io.droidevs.wallpaper.data.network.response.PhotoResponse
import io.droidevs.wallpaper.data.network.result.ApiResult

interface PhotoApi {


    suspend fun searchPhotos(
        query: String,
        page: Int,
        perPage: Int,
        orientation: String,
        orderBy: String,
        collections: List<Int>? = null,
        color: String? = null,
    ) : ApiResult<PhotoResponse>

    suspend fun listPhotos(
        page: Int,
        perPage: Int,
    ): ApiResult<PhotoResponse>

    suspend fun getPhoto(
        id : String,
    ) : ApiResult<PhotoDto>

    suspend fun randomPhoto(
        query: String? = null,
        orientation: String,
        count: Int,
    ) : ApiResult<List<PhotoDto>>

    suspend fun randomPhoto(
        query: String? = null,
        orientation: String,
    ) : ApiResult<PhotoDto>

    suspend fun randomPhoto(
        collections: List<Int>? = null,
        topics: List<String>? =  null,
        orientation: String,
        count: Int = 1,
    ) : ApiResult<List<PhotoDto>>

    suspend fun randomPhoto(
        collections: List<Int>? = null,
        topics: List<String>? =  null,
        orientation: String,
    ) : ApiResult<PhotoDto>

    suspend fun getDownloadUrl(
        id: String,
    ) : ApiResult<String>

}