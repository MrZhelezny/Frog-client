package com.zhelezny.frog.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import com.zhelezny.frog.R
import com.zhelezny.frog.data.repository.KtorRepositoryImpl
import com.zhelezny.frog.databinding.FragmentGameFieldBinding
import com.zhelezny.frog.domain.repository.KtorRepository
import com.zhelezny.frog.domain.usecases.StartGameUseCase
import kotlinx.coroutines.launch

class GameFieldFragment : Fragment(R.layout.fragment_game_field) {

    private lateinit var binding: FragmentGameFieldBinding
    private lateinit var color: String

    private val repository = KtorRepositoryImpl()
    private val startGameUseCase = StartGameUseCase(repository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        color = arguments?.getString("bundleColorKey")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGameFieldBinding.bind(view)
        setYourColor(color)
        viewLifecycleOwner.lifecycleScope.launch {
            startGameUseCase.execute("123").collect{

            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setYourColor(color: String) {
        var yourColor = R.color.white
        when (color) {
            "BLUE" -> yourColor = R.color.blue_card
            "YELLOW" -> yourColor = R.color.yellow_card
            "RED" -> yourColor = R.color.red_card
            "GREEN" -> yourColor = R.color.green_card
            "PURPLE" -> yourColor = R.color.purple_card
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.cardView.setBackgroundColor(ContextCompat.getColor(requireActivity(), yourColor))
        } else {
            binding.cardView.setBackgroundColor(resources.getColor(yourColor))
        }
    }
}