package io.droidevs.wallpaper.data.network.topic

import io.droidevs.wallpaper.data.network.ApiClient
import io.droidevs.wallpaper.data.network.dtos.TopicDto
import io.droidevs.wallpaper.domain.model.Topic
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.NetworkError
import io.ktor.client.HttpClient

class TopicApiImpl(private val client: HttpClient) : TopicApi,
    ApiClient(client) {

    override suspend fun listTopics(
        ids: String?,
        page: Int?,
        perPage: Int?,
        orderBy: String?
    ): Result<List<TopicDto>, NetworkError> {
        return get<List<TopicDto>>(
            path = "topics",
            queryParams = mapOf(
                "ids" to ids,
                "page" to page,
                "per_page" to perPage,
                "order_by" to orderBy
            ),
            headers = emptyMap(),
        )
    }

    override suspend fun getTopic(idOrSlug: String): Result<TopicDto, NetworkError> {
        return get<TopicDto>(
            path = "topics/$idOrSlug"
        )
    }
}