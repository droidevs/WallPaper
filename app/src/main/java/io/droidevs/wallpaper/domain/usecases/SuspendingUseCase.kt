package io.droidevs.wallpaper.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class SuspendingUseCase<in P, out R>(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(parameters: P): R {
        return withContext(coroutineDispatcher) {
            execute(parameters)
        }
    }

    protected abstract suspend fun execute(parameters: P): R
}
