package com.zhelezny.frog.domain.usecases

import com.zhelezny.frog.domain.repository.PlayerRepository

class GetUidFromServerUseCase(private val ktorRepo: PlayerRepository) {

    fun execute(nickName: String): String {
        return ktorRepo.getUid(nickName)
    }
}