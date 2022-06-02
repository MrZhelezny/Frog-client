package com.zhelezny.frog.domain.repository

import kotlinx.coroutines.flow.Flow

interface KtorRepository {

    fun getUid(nickName: String): String

    fun gameRequest(nickName: String): Flow<String>
}