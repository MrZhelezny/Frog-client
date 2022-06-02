package com.zhelezny.frog.presentation

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhelezny.frog.domain.usecases.GameRequestUseCase

class PlayerSearchViewModel(private val gameRequestUseCase: GameRequestUseCase) : ViewModel() {

    private val playerListMutable = MutableLiveData<String>()
    val playerListLive: LiveData<String> = playerListMutable

    private val timerDataMutable = MutableLiveData<String>()
    val timerData: LiveData<String> = timerDataMutable

    init {
        startTimeCounter()
    }

    suspend fun gameRequest(nickname: String) {
        gameRequestUseCase.execute(nickname).collect {
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