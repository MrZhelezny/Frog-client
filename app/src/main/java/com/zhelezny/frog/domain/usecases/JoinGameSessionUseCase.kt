package com.zhelezny.frog.domain.usecases

import com.zhelezny.frog.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow

class JoinGameSessionUseCase(private val playerRepository: PlayerRepository) {
    fun currentPlayers(nickName: String): Flow<List<String>> = playerRepository.getCurrentPlayers(nickName)
}