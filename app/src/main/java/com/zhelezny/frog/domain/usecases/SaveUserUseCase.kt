package com.zhelezny.frog.domain.usecases

import com.zhelezny.frog.data.storage.models.User
import com.zhelezny.frog.domain.repository.UserRepository

class SaveUserUseCase(private val userRepo: UserRepository) {

    fun execute(user: User) {
        userRepo.save(user)
    }
}