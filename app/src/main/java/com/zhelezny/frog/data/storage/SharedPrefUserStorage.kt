package com.zhelezny.frog.data.storage

import android.content.Context
import com.zhelezny.frog.data.storage.models.User
import com.zhelezny.frog.data.storage.models.UserStatus

private const val APP_PREFERENCES = "APP_PREFERENCES"
private const val PREF_NICKNAME = "PREF_NICKNAME"
private const val DEFAULT_NAME = "defaultName"

class SharedPrefUserStorage(context: Context) : UserStorage {

    private val pref = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    override fun save(user: User) {
        pref.edit().putString(PREF_NICKNAME, user.nickName).apply()
    }

    override fun get(): User {
        val nickname = pref.getString(PREF_NICKNAME, "") ?: DEFAULT_NAME
        return User(id = "user", nickName = nickname, UserStatus.ONLINE)
    }
}