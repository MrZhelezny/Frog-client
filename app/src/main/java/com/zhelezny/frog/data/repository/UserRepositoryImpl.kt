package com.zhelezny.frog.data.repository

import com.zhelezny.frog.data.storage.UserStorage
import com.zhelezny.frog.data.storage.models.User
import com.zhelezny.frog.domain.repository.UserRepository

class UserRepositoryImpl(private val userStorage: UserStorage) : UserRepository {

    override fun save(user: User) {
        userStorage.save(user)
    }

    override fun get(): User {
        return userStorage.get()
    }
}