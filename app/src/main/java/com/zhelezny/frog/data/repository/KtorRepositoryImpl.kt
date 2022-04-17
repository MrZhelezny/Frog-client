package com.zhelezny.frog.data.repository

import com.zhelezny.frog.domain.repository.KtorRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class KtorRepositoryImpl : KtorRepository {

    override fun getUid(nickName: String): String {
        var userUid = ""
        GlobalScope.launch {
            val client = HttpClient(Android) {
                install(JsonFeature)
            }
            val response: HttpResponse = client.get("http://192.168.100.122:8080/api/addUser") {
//                    method = HttpMethod.Post

                parameter("user", nickName)

//                    body = UUID.randomUUID().toString()
//                    contentType(ContentType.Application.Json)
//                    body = User(UUID.randomUUID().toString(), UserStatus.ONLINE)
            }
            println(response.status)
            val byteArrayBody: ByteArray = response.receive()
            userUid = String(byteArrayBody)
            println(userUid)

            client.close()
        }
        return userUid
    }

    override fun gameRequest(nickName: String): String {
        var result = ""
        runBlocking {
            val client = HttpClient(Android) {
                install(JsonFeature)
            }
            val response: HttpResponse = client.get("http://192.168.100.122:8080/api/goPlay") {
//                    method = HttpMethod.Post

                parameter("user", nickName)

//                    body = UUID.randomUUID().toString()
//                    contentType(ContentType.Application.Json)
//                    body = User(UUID.randomUUID().toString(), UserStatus.ONLINE)
            }
            println(response.status)
            val byteArrayBody: ByteArray = response.receive()
            result = String(byteArrayBody)
            println(result)

            client.close()

        }
        return result
    }
}