package com.zhelezny.frog.domain.repository

import kotlinx.coroutines.flow.Flow

interface KtorRepository {

    fun getUid(nickName: String): String

    fun joinGame(nickName: String): Flow<List<String>>

    fun startGame(nickName: String): Flow<String>
}