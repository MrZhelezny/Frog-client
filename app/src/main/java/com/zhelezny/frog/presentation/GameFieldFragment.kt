package com.zhelezny.frog.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zhelezny.frog.R
import com.zhelezny.frog.databinding.FragmentGameFieldBinding

class GameFieldFragment: Fragment(R.layout.fragment_game_field) {

    private lateinit var binding: FragmentGameFieldBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGameFieldBinding.bind(view)
    }
}