package com.zhelezny.frog.domain.usecases

import com.zhelezny.frog.domain.repository.KtorRepository
import kotlinx.coroutines.flow.Flow

class StartGameUseCase(private val ktorRepository: KtorRepository) {

    fun execute(nickName: String): Flow<String> {
        return ktorRepository.startGame(nickName)
    }
}