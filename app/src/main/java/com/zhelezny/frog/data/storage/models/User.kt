package com.zhelezny.frog.data.storage.models

data class User(val id: String, val nickName: String, var status: Enum<UserStatus>)

data class UserForWaiting(val nickName: String)

enum class UserStatus {
    ONLINE
}
