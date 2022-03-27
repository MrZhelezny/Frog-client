package com.zhelezny.frog.presentation

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zhelezny.frog.R
import com.zhelezny.frog.data.repository.UserRepositoryImpl
import com.zhelezny.frog.data.storage.SharedPrefUserStorage
import com.zhelezny.frog.databinding.FragmentPlayerSearchBinding
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class PlayerSearchFragment : Fragment(R.layout.fragment_player_search) {

    private lateinit var binding: FragmentPlayerSearchBinding
    var searchTimer: CountDownTimer? = null

    private val userStorage by lazy { SharedPrefUserStorage(context = requireContext()) }
    private val userRepository by lazy { UserRepositoryImpl(userStorage = userStorage) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayerSearchBinding.bind(view)

        binding.firstPlayer.text = userRepository.get().nickName

        startTimeCounter()

        var string = ""
        binding.button.setOnClickListener {
            GlobalScope.launch {
                val client = HttpClient(Android) {
                    install(JsonFeature)
                }
                val response: HttpResponse = client.get("http://192.168.100.122:8080/api/") {
//                    method = HttpMethod.Post

                    parameter("user", UUID.randomUUID().toString())

//                    body = UUID.randomUUID().toString()
//                    contentType(ContentType.Application.Json)
//                    body = User(UUID.randomUUID().toString(), UserStatus.ONLINE)
                }
                println(response.status)
                val byteArrayBody: ByteArray = response.receive()
                string = String(byteArrayBody)
                println(string)

                client.close()

            }
            binding.textView.text = string
//            val response = createRequest()
//            println(response.status)
////            val byteArrayBody: ByteArray = response.receive()
////            string = String(byteArrayBody)
////            println(string)
//            binding.textView.text = string
        }

        binding.cancel.setOnClickListener {
            searchTimer?.cancel()
            findNavController().popBackStack()
        }
    }

    fun startTimeCounter() {
        searchTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text =
                    getString(R.string.timer, (millisUntilFinished / 1000).toString())
            }

            override fun onFinish() {
                binding.fifthPlayer.text = "FINISH"
            }
        }
        searchTimer?.start()
    }

    var count = 0
    fun gameRequest() {
        val timer = object : CountDownTimer(60 * 1000, 2 * 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("!!!!", "count: ${count++}")
            }

            override fun onFinish() {
                count = 0
            }
        }
        timer.start()
    }

    fun createRequest(): HttpResponse {
        lateinit var response: HttpResponse
        GlobalScope.launch {
            val client = HttpClient(Android) {
//                    engine {
//                        // this: AndroidEngineConfig
//                        connectTimeout = 100_000
//                        socketTimeout = 100_000
//                        proxy = Proxy(Proxy.Type.SOCKS, InetSocketAddress("localhost", 8080))
//                    }
            }
            response = client.request("http://10.0.2.2:8080/api/") {
                method = HttpMethod.Get
            }
            client.close()

        }
        return response
    }
}