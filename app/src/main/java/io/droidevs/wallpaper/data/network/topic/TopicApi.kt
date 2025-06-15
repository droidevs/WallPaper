package io.droidevs.wallpaper.data.network.topic


import io.droidevs.wallpaper.data.network.dtos.TopicDto
import io.droidevs.wallpaper.data.network.ktorClient
import io.droidevs.wallpaper.domain.result.Result
import io.droidevs.wallpaper.domain.result.errors.NetworkError

interface TopicApi {
    suspend fun listTopics(
        ids: String? = null,
        page: Int? = null,
        perPage: Int? = null,
        orderBy: String? = null
    ): Result<List<TopicDto>, NetworkError>

    suspend fun getTopic(
        idOrSlug: String
    ): Result<TopicDto, NetworkError>

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"

        fun create(): TopicApi {
            return TopicApiImpl(
                ktorClient
            )
        }
    }
}