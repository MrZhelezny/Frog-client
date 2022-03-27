package com.zhelezny.frog.data.storage

import com.zhelezny.frog.data.storage.models.User

interface UserStorage {

    fun save(user: User)

    fun get(): User
}