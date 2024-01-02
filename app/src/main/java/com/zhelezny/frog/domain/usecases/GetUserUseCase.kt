package com.zhelezny.frog.domain.usecases

import com.zhelezny.frog.data.storage.models.User
import com.zhelezny.frog.domain.repository.PlayerRepository

class GetUserUseCase(private val userRepo: PlayerRepository) {

    fun execute(): User {
        return userRepo.get()
    }
}