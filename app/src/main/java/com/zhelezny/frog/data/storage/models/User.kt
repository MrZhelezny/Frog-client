package com.zhelezny.frog.data.storage.models

data class User(val id: String, val nickName: String, var status: Enum<UserStatus>)

enum class UserStatus {
    ONLINE
}
