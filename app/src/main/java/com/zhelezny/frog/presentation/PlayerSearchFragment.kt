package com.zhelezny.frog.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.zhelezny.frog.R
import com.zhelezny.frog.databinding.FragmentPlayerSearchBinding
import com.zhelezny.frog.domain.usecases.GetUserUseCase
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerSearchFragment : Fragment(R.layout.fragment_player_search) {

    private lateinit var binding: FragmentPlayerSearchBinding
    private val searchViewModel by viewModel<PlayerSearchViewModel>()
    private val getUserUseCase: GetUserUseCase by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayerSearchBinding.bind(view)

        val nickname = getUserUseCase.execute().nickName

        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.gameRequest(nickname = nickname)
        }

        searchViewModel.playerListLive.observe(requireActivity()) {
            val user = it.split(":")
            binding.firstPlayer.text = user[0]
            if (user[1].isNotEmpty()) {
                binding.secondPlayer.text = user[1]
            }
        }
    }

    override fun onStart() {
        super.onStart()
        searchViewModel.timerData.observe(this) {
            binding.tvTimer.text = getString(R.string.timer, it)
        }
    }
}