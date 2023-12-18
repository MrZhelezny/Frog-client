package com.zhelezny.frog.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
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

    private val tvNames by lazy {
        mutableListOf<TextView>().apply {
            add(binding.firstPlayer)
            add(binding.secondPlayer)
            add(binding.thirdPlayer)
            add(binding.fourthPlayer)
            add(binding.fifthPlayer)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayerSearchBinding.bind(view)
        // получить список ожидающих игроков
        val nickname = getUserUseCase.execute().nickName

        binding.btCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.joinGame(nickname = nickname)
        }

        searchViewModel.playerListLive.observe(requireActivity()) {
            for (i in it.indices) {
                tvNames[i].text = it[i]
            }
//            if (user[0] == "Color") {
//                val color = user[1]
//                findNavController().navigate(R.id.action_playerSearchFragment_to_gameFieldFragment, bundleOf("bundleColorKey" to color))
//            }
        }
    }

    override fun onStart() {
        super.onStart()
        searchViewModel.timerData.observe(this) {
            binding.tvTimer.text = getString(R.string.timer, it)
        }
    }
}