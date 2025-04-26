package io.droidevs.wallpaper.domain.usecases

import io.droidevs.wallpaper.data.local.TopicEntity
import io.droidevs.wallpaper.data.repository.topic.TopicRepository
import javax.inject.Inject

class GetTopicByIdUseCase @Inject constructor(
    private val repository: TopicRepository
) {
    suspend operator fun invoke(id: String): TopicEntity? = repository.getTopicById(id)
}