package com.zhelezny.frog.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zhelezny.frog.R
import com.zhelezny.frog.data.storage.models.User
import com.zhelezny.frog.data.storage.models.UserStatus
import com.zhelezny.frog.databinding.FragmentNicknameBinding
import com.zhelezny.frog.domain.repository.PlayerRepository
import com.zhelezny.frog.domain.usecases.GetUidFromServerUseCase
import com.zhelezny.frog.domain.usecases.SaveUserUseCase
import org.koin.android.ext.android.inject

class NicknameFragment : Fragment(R.layout.fragment_nickname) {

    private lateinit var binding: FragmentNicknameBinding

    //TODO: перенести SaveUserUseCase и GetUidFromServerUseCase во viewModel
    private val playerRepo: PlayerRepository by inject()
    private val saveUserUseCase: SaveUserUseCase by inject()
    private val getUidFromServerUserUseCase: GetUidFromServerUseCase by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNicknameBinding.bind(view)

        val savedUser = playerRepo.get()
        val nickname = savedUser.nickName
        if (nickname.isNotEmpty())
            binding.etNickname.setText(nickname)

        binding.btCreateNickname.setOnClickListener {
            //TODO: не нужно ходить на хост. только сохраняем
            val id = /*getUidFromServerUserUseCase.execute(binding.etNickname.text.toString() + savedUser.id)*/ ""
            saveUserUseCase.execute(User(id, binding.etNickname.text.toString(), UserStatus.ONLINE))

            findNavController().navigate(R.id.action_nicknameFragment_to_menuFragment)
        }
    }
}