package com.zhelezny.frog.data.repository

import android.util.Log
import com.zhelezny.frog.domain.repository.KtorRepository
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class KtorRepositoryImpl : KtorRepository {

    override fun getUid(nickName: String): String {
        lateinit var id: String
        val client = HttpClient {
            install(WebSockets)
        }
        runBlocking {
            client.webSocket(
                method = HttpMethod.Get,
                host = "192.168.100.122",
                port = 8080,
                path = "/getUid"
            ) {
                try {
                    send(nickName)
                    val msg = incoming.receive() as Frame.Text
                    id = msg.readText()
                    Log.d("!!!", id)
                } catch (e: Exception) {
                    println("Error while receiving: " + e.localizedMessage)
                }
            }
        }
        client.close()
        return id
    }

    override fun gameRequest(nickName: String): Flow<String> = flow {
        val client = HttpClient {
            install(WebSockets)
        }
        client.webSocket(
            method = HttpMethod.Get,
            host = "192.168.100.122",
            port = 8080,
            path = "/goPlay"
        ) {
            try {
                send(nickName)
                for (message in incoming) {
                    message as? Frame.Text ?: continue
                    println(message.readText())
                    emit(message.readText())
                }
            } catch (e: Exception) {
                println("Error while receiving: " + e.localizedMessage)
            }
        }
//        client.close()
    }
}