package io.droidevs.wallpaper.data.network.collection

import io.droidevs.wallpaper.data.network.ApiClient
import io.droidevs.wallpaper.data.network.dtos.CollectionDto
import io.droidevs.wallpaper.data.network.response.CollectionResponse
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.NetworkError
import io.ktor.client.HttpClient

class CollectionApiImpl(
    private val client: HttpClient
): CollectionApi , ApiClient(client) {
    override suspend fun searchCollection(
        query: String,
        page: Int?,
        perPage: Int?
    ): Result<CollectionResponse, NetworkError> {
        return get(
            path = "/search/collections",
            queryParams = mapOf(
                "query" to query,
                "page" to page,
                "per_page" to perPage
            )
        )
    }

    override suspend fun listCollections(page: Int?, perPage: Int?): Result<List<CollectionDto>, NetworkError> {
        return get(
            path = "/collections",
            queryParams = mapOf(
                "page" to page,
                "per_page" to perPage
            )
        )
    }

    override suspend fun getCollection(id: Int): Result<CollectionDto, NetworkError> {
        return get<CollectionDto>(
            path = "/collections/$id"
        )
    }

    override suspend fun getRelatedCollections(id: Int): Result<List<CollectionDto>, NetworkError> {
        return get<List<CollectionDto>>(
            path = "/collections/$id/related"
        )
    }
}