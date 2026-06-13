package io.droidevs.wallpaper.domain.usecases

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<in P, out R> {
    abstract fun execute(parameters: P): Flow<R>
    
    operator fun invoke(parameters: P): Flow<R> = execute(parameters)
}
