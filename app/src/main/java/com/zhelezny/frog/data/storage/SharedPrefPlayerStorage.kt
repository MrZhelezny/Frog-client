package com.zhelezny.frog.data.storage

import android.content.Context
import com.zhelezny.frog.data.storage.models.User
import com.zhelezny.frog.data.storage.models.UserStatus

class SharedPrefPlayerStorage(context: Context) : PlayerStorage, IpStorage {

    private val pref = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    override fun save(user: User) {
        pref.edit().putString(PREF_ID, user.id).apply()
        pref.edit().putString(PREF_NICKNAME, user.nickName).apply()
    }

    override fun get(): User {
        val id = pref.getString(PREF_ID, "") ?: DEFAULT_PARAM
        val nickname = pref.getString(PREF_NICKNAME, "") ?: DEFAULT_PARAM
        return User(id = id, nickName = nickname, UserStatus.ONLINE)
    }

    override fun getIp(): String {
        return pref.getString(PREF_IP, "") ?: DEFAULT_PARAM
    }

    override fun saveIp(ip: String) {
        pref.edit().putString(PREF_IP, ip).apply()
    }

    companion object {
        private const val APP_PREFERENCES = "APP_PREFERENCES"
        private const val PREF_ID = "PREF_ID"
        private const val PREF_NICKNAME = "PREF_NICKNAME"
        private const val DEFAULT_PARAM = "defaultParam"
        private const val PREF_IP = "PREF_IP"
    }
}