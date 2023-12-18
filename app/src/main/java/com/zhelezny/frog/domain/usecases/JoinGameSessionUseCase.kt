package com.zhelezny.frog.domain.usecases

import com.zhelezny.frog.domain.repository.KtorRepository
import kotlinx.coroutines.flow.Flow

class JoinGameSessionUseCase(private val ktorRepository: KtorRepository) {

    fun execute(nickName: String): Flow<List<String>> {
        return ktorRepository.joinGame(nickName)
    }
}