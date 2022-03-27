package com.zhelezny.frog.domain.usecases

import com.zhelezny.frog.data.storage.models.User
import com.zhelezny.frog.domain.repository.UserRepository

class GetUserUseCase(private val userRepo: UserRepository) {

    fun execute(): User {
        return userRepo.get()
    }
}