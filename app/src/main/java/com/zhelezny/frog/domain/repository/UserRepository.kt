package com.zhelezny.frog.domain.repository

import com.zhelezny.frog.data.storage.models.User

interface UserRepository {

    fun save(user: User)

    fun get(): User
}