package com.zhelezny.frog.presentation

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.zhelezny.frog.domain.usecases.JoinGameSessionUseCase

class PlayerSearchViewModel(private val joinGameUseCase: JoinGameSessionUseCase) : ViewModel() {

    private val playerListMutable = MutableLiveData<List<String>>()
    val playerListLive: LiveData<List<String>> = playerListMutable

    private val timerDataMutable = MutableLiveData<String>()
    val timerData: LiveData<String> = timerDataMutable

    val gson = Gson()
    val json = JsonObject()

    init {
        startTimeCounter()
    }

    suspend fun joinGame(nickname: String) {
        joinGameUseCase.execute(nickname).collect {
//            val list = gson.fromJson(it, ArrayList::class.java)
            playerListMutable.postValue(it)
        }
    }

    private fun startTimeCounter() {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerDataMutable.value = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
//                binding.fifthPlayer.text = "FINISH"
            }
        }.start()
    }
}