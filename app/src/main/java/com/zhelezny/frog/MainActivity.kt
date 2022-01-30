package com.zhelezny.frog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.InetSocketAddress
import java.net.Proxy

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tx = findViewById<TextView>(R.id.tv)
        var string = ""
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            GlobalScope.launch {
                val client = HttpClient(Android) {
//                    engine {
//                        // this: AndroidEngineConfig
//                        connectTimeout = 100_000
//                        socketTimeout = 100_000
//                        proxy = Proxy(Proxy.Type.SOCKS, InetSocketAddress("localhost", 8080))
//                    }
                }
                val response: HttpResponse = client.request("http://10.0.2.2:8080/api/") {
                    method = HttpMethod.Get
                }
                println(response.status)
                val byteArrayBody: ByteArray = response.receive()
                string = String(byteArrayBody)
                println(string)

                client.close()
            }
            tx.text = string
        }
    }
}