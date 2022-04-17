package com.zhelezny.frog.presentation

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zhelezny.frog.R
import com.zhelezny.frog.data.repository.KtorRepositoryImpl
import com.zhelezny.frog.data.repository.UserRepositoryImpl
import com.zhelezny.frog.data.storage.SharedPrefUserStorage
import com.zhelezny.frog.databinding.FragmentPlayerSearchBinding
import com.zhelezny.frog.domain.usecases.GameRequestUseCase
import com.zhelezny.frog.domain.usecases.GetUserUseCase
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerSearchFragment : Fragment(R.layout.fragment_player_search) {

    private lateinit var binding: FragmentPlayerSearchBinding
    private var searchTimer: CountDownTimer? = null
    private var gameRequestsTimer: CountDownTimer? = null
    private var countRequest = 0

    private val userRepository by lazy {
        UserRepositoryImpl(
            userStorage = SharedPrefUserStorage(
                context = requireContext()
            )
        )
    }
    private val getUserUseCase by lazy { GetUserUseCase(userRepository) }

    private val gameRequestUseCase = GameRequestUseCase(KtorRepositoryImpl())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayerSearchBinding.bind(view)

        val nickname = getUserUseCase.execute().nickName
        binding.firstPlayer.text = nickname

        binding.cancel.setOnClickListener {
            searchTimer?.cancel()
            gameRequestsTimer?.cancel()
            findNavController().popBackStack()
        }

        startTimeCounter()
        gameRequest(nickname = nickname)
    }

    private fun startTimeCounter() {
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

    private fun gameRequest(nickname: String) {
        gameRequestsTimer = object : CountDownTimer(60 * 1000, 2 * 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val resultRequest = gameRequestUseCase.execute(nickname)
                binding.textView.text = "$resultRequest Запрос №${countRequest++}"
                if (resultRequest == "Нанчинаем игру!") {
                    findNavController().navigate(R.id.action_playerSearchFragment_to_gameFieldFragment)
                    cancel()
                }
            }

            override fun onFinish() {
                countRequest = 0
            }
        }
        gameRequestsTimer?.start()
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