package com.zhelezny.frog.domain.usecases

import com.zhelezny.frog.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow

class StartGameUseCase(private val playerRepository: PlayerRepository) {

    fun connect(nickName: String): Flow<String> {
        return playerRepository.startGame(nickName)
    }
}