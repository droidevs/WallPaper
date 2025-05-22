package io.droidevs.wallpaper.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

class TestAppDispatchers(
    private val testDispatcher: CoroutineDispatcher = TODO()
) : AppDispatchers {
    override val main: CoroutineDispatcher = testDispatcher
    override val default: CoroutineDispatcher = testDispatcher
    override val io: CoroutineDispatcher = testDispatcher
    override val unconfined: CoroutineDispatcher = testDispatcher
}