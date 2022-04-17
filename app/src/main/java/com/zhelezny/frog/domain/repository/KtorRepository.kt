package com.zhelezny.frog.domain.repository

interface KtorRepository {

    fun getUid(nickName: String): String

    fun gameRequest(nickName: String): String
}