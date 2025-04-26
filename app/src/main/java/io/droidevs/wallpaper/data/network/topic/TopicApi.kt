package io.droidevs.wallpaper.data.network.topic


import io.droidevs.wallpaper.data.network.dtos.TopicDto
import io.droidevs.wallpaper.data.network.ktorClient
import io.droidevs.wallpaper.data.network.result.ApiResult

interface TopicApi {
    suspend fun listTopics(
        ids: String? = null,
        page: Int? = null,
        perPage: Int? = null,
        orderBy: String? = null
    ): ApiResult<List<TopicDto>>

    suspend fun getTopic(
        idOrSlug: String
    ): ApiResult<TopicDto>

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"

        fun create(): TopicApi {
            return TopicApiImpl(
                ktorClient
            )
        }
    }
}