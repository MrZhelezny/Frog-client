package com.zhelezny.frog.data.repository

import android.util.Log
import com.zhelezny.frog.data.storage.PlayerStorage
import com.zhelezny.frog.data.storage.models.PlayerName
import com.zhelezny.frog.data.storage.models.User
import com.zhelezny.frog.domain.repository.PlayerRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.net.ConnectException


class IPlayerRepository(private val playerStorage: PlayerStorage) : PlayerRepository {

    private val TAG = "IPlayerRepository"

    override fun saveNickNamePlayer(user: User) {
        playerStorage.save(user)
    }

    override fun get(): User {
        return playerStorage.get()
    }

    override fun getUid(nickName: String): String {
        lateinit var id: String
        val client = HttpClient {
            install(WebSockets)
        }
        runBlocking {
            client.webSocket(
                method = HttpMethod.Get,
                host = HOST_ADDRESS,
                port = PORT,
                path = "/getUid"
            ) {
                try {
                    send(nickName)
                    val msg = incoming.receive() as Frame.Text
                    id = msg.readText()
                    Log.d(TAG, id)
                } catch (e: Exception) {
                    println("Error while receiving: " + e.localizedMessage)
                }
            }
        }
        client.close()
        return id
    }

    override fun getCurrentPlayers(nickName: String): Flow<List<String>> = flow {
        try {
            val client = HttpClient {
                install(WebSockets)
            }
            client.webSocket(method = HttpMethod.Get, host = HOST_ADDRESS, port = PORT, path = "/joinGame") {
                try {
                    Log.d(TAG, "Отправка nickName: $nickName")
                    send(nickName)
                    for (message in incoming) {
                        message as? Frame.Text ?: continue
                        Log.d(TAG, "Входящее сообщение: ${message.readText()}")

                        val playerName = Json.decodeFromString<List<PlayerName>>(message.readText())
                        emit(playerName.map { it.nickname })
                        Log.d(TAG, "emitим значения во Flow")
                        if (message.readText().startsWith("Color")) {
                            client.close()
                        }
                    }
                } catch (e: Exception) {
                    println("Error while receiving: " + e.localizedMessage)
                }
            }
            Log.d(TAG, "joinGame END")
//        client.close()
        } catch (e: ConnectException) {
            Log.d(TAG, e.message, e)
//            Toast.makeText(requireContext(), "Ошибка подключения к серверу игры", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Log.d(TAG, e.message, e)
        }
    }

    override fun startGame(nickName: String): Flow<String> = flow {
        val client = HttpClient {
            install(WebSockets)
        }
        client.webSocket(
            method = HttpMethod.Get,
            host = HOST_ADDRESS,
            port = PORT,
            path = "/joinGame"
        ) {
            try {
                Log.d(TAG, "Отправка nickName: $nickName")
                send(nickName)
                for (message in incoming) {
                    message as? Frame.Text ?: continue
                    Log.d(TAG, "Входящее сообщение: ${message.readText()}")
                    emit(message.readText())
                    Log.d(TAG, "emitим значения во Flow")
                    if (message.readText().startsWith("Color")) {
                        client.close()
                    }
                }
            } catch (e: Exception) {
                println("Error while receiving: " + e.localizedMessage)
            }
        }
        Log.d(TAG, "joinGame END")
//        client.close()
    }

    companion object {
        private const val HOST_ADDRESS = "192.168.0.108"
        private const val PORT = 22222
    }
}