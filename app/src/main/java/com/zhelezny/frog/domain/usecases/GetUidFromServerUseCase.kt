package com.zhelezny.frog.domain.usecases

import com.zhelezny.frog.domain.repository.KtorRepository

class GetUidFromServerUseCase(private val ktorRepo: KtorRepository) {

    fun execute(nickName: String): String {
        return ktorRepo.getUid(nickName)
    }
}