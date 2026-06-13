package io.droidevs.wallpaper.data.network.photo

import io.droidevs.wallpaper.data.network.ApiClient
import io.droidevs.wallpaper.data.network.dtos.PhotoDto
import io.droidevs.wallpaper.data.network.response.PhotoResponse
import io.droidevs.wallpaper.data.network.result.ApiResult
import io.ktor.client.HttpClient

class PhotoApiImpl(
    client: HttpClient,
) : PhotoApi , ApiClient(client) {
    override suspend fun searchPhotos(
        query: String,
        page: Int,
        perPage: Int,
        orientation: String,
        orderBy: String,
        collections: List<Int>?,
        color: String?
    ): ApiResult<PhotoResponse> {
        return get<PhotoResponse>(
            path = "/search/photos",
            queryParams = mapOf(
                "query" to query,
                "page" to page,
                "per_page" to perPage,
                "orientation" to orientation,
                "order_by" to orderBy,
                "collections" to collections?.joinToString(";"),
                "color" to color
            )
        )
    }

    override suspend fun listPhotos(page: Int, perPage: Int): ApiResult<PhotoResponse> {
        return get<PhotoResponse>(
            path = "/photos",
            queryParams = mapOf(
                "page" to page,
                "per_page" to perPage
            )
        )
    }

    override suspend fun getPhoto(id: String): ApiResult<PhotoDto> {
        return get<PhotoDto>(
            path = "/photos/$id"
        )
    }

    override suspend fun randomPhoto(
        query: String?,
        orientation: String,
        count: Int
    ): ApiResult<List<PhotoDto>> {
        return get<List<PhotoDto>>(
            path = "/photos/random",
            queryParams = mapOf(
                "query" to query,
                "orientation" to orientation,
                "count" to count
            )
        )
    }

    override suspend fun randomPhoto(query: String?, orientation: String): ApiResult<PhotoDto> {
        return get<PhotoDto>(
            path = "/photos/random",
            queryParams = mapOf(
                "query" to query,
                "orientation" to orientation
            )
        )
    }

    override suspend fun randomPhoto(
        collections: List<Int>?,
        topics: List<String>?,
        orientation: String,
        count: Int
    ): ApiResult<List<PhotoDto>> {
        return get<List<PhotoDto>>(
            path = "/photos/random",
            queryParams = mapOf(
                "collections" to collections?.joinToString(";"),
                "topics" to topics?.joinToString(";"),
                "orientation" to orientation,
                "count" to count
            )
        )
    }

    override suspend fun randomPhoto(
        collections: List<Int>?,
        topics: List<String>?,
        orientation: String
    ): ApiResult<PhotoDto> {
        return get<PhotoDto>(
            path = "/photos/random",
            queryParams = mapOf(
                "collections" to collections?.joinToString(";"),
                "topics" to topics?.joinToString(";"),
                "orientation" to orientation
            )
        )
    }


    override suspend fun getDownloadUrl(id: String): ApiResult<String> {
        return get<String>(
            path = "/photos/$id/download"
        )
    }
}