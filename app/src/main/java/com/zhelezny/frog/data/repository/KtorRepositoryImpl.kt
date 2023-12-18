package com.zhelezny.frog.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.zhelezny.frog.domain.repository.KtorRepository
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import java.lang.reflect.Type
import java.util.*


class KtorRepositoryImpl : KtorRepository {

    private val TAG = "KtorRepositoryImpl"
    val gson = Gson()
    val json = JsonObject()

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

    override fun joinGame(nickName: String): Flow<List<String>> = flow {
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
                    val element: JsonElement = gson.fromJson(message.readText(), JsonElement::class.java)
                    val jsonObj = element.asJsonObject
                    val list = jsonObj.getAsJsonArray("name")
                    val listType: Type = object : TypeToken<List<String?>?>() {}.type
                    val target: MutableList<String> = LinkedList<String>()
                    target.add("blah")
                    val listStr = mutableListOf<String>()
                    list.forEach{
                        listStr.add(it.toString())
                    }
                    emit(listStr)
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
        private const val HOST_ADDRESS = "192.168.100.139"
        private const val PORT = 8080
    }
}