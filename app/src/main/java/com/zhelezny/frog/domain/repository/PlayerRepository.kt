package com.zhelezny.frog.domain.repository

import com.zhelezny.frog.data.storage.models.User
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    fun getUid(nickName: String): String

    /**
     * Получение списка игроков для сессии
     * Отправка на сервер запроса на присоединение к игре.
     * @param nickName необходимо передать имя игрока
     */
    fun getCurrentPlayers(nickName: String): Flow<List<String>>

    fun startGame(nickName: String): Flow<String>

    fun saveNickNamePlayer(user: User)

    fun get(): User
}