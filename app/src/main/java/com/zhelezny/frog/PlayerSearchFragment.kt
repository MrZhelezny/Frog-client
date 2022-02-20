package com.zhelezny.frog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zhelezny.frog.databinding.FragmentPlayerSearchBinding
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerSearchFragment: Fragment(R.layout.fragment_player_search) {

    private lateinit var binding: FragmentPlayerSearchBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayerSearchBinding.bind(view)

        var string = ""
        binding.button.setOnClickListener {
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
            binding.textView.text = string
        }
    }
}