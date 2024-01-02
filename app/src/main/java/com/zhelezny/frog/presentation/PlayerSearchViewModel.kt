package com.zhelezny.frog.presentation

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhelezny.frog.domain.usecases.GetUserUseCase
import com.zhelezny.frog.domain.usecases.JoinGameSessionUseCase
import kotlinx.coroutines.launch

class PlayerSearchViewModel(
    private val joinGameUseCase: JoinGameSessionUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val playerListMutable = MutableLiveData<List<String>>()
    val playerListLive: LiveData<List<String>> = playerListMutable

    private val timerDataMutable = MutableLiveData<String>()
    val timerData: LiveData<String> = timerDataMutable

    init {
        startTimeCounter()
        getWaitingPlayers()
    }

    private fun getWaitingPlayers() {
        viewModelScope.launch {
            // получаем имя игрока
            val nickname = getUserUseCase.execute().nickName

            // подписываемся на список игроков в ожидании для отображения на экране
            joinGameUseCase.currentPlayers(nickname).collect { namesList ->
                playerListMutable.postValue(namesList)
            }
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