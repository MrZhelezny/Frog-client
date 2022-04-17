package com.zhelezny.frog.domain.usecases

import com.zhelezny.frog.domain.repository.KtorRepository

class GameRequestUseCase(private val ktorRepository: KtorRepository) {

    fun execute(nickName: String): String {
        return ktorRepository.gameRequest(nickName)
    }
}