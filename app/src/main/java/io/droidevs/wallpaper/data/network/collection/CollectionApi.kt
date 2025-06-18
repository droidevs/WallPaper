package io.droidevs.wallpaper.data.network.collection

import io.droidevs.wallpaper.data.network.dtos.CollectionDto
import io.droidevs.wallpaper.data.network.response.CollectionResponse
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.NetworkError

interface CollectionApi {


    suspend fun searchCollection(
        query: String,
        page: Int?,
        perPage: Int?
    ) : Result<CollectionResponse, NetworkError>

    suspend fun listCollections(
        page : Int? = null,
        perPage : Int? = null
    ) : Result<List<CollectionDto>, NetworkError>

    suspend fun getCollection(
        id: Int,
    ) : Result<CollectionDto, NetworkError>

    suspend fun getRelatedCollections(
        id: Int,
    ) : Result<List<CollectionDto>, NetworkError>
}