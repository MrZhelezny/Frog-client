package com.zhelezny.frog.domain.usecases

import com.zhelezny.frog.domain.repository.KtorRepository
import kotlinx.coroutines.flow.Flow

class GameRequestUseCase(private val ktorRepository: KtorRepository) {

    fun execute(nickName: String): Flow<String> {
        return ktorRepository.gameRequest(nickName)
    }
}