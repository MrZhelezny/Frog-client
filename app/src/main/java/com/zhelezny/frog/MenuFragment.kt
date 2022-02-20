package com.zhelezny.frog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zhelezny.frog.databinding.FragmentMenuBinding

class MenuFragment: Fragment(R.layout.fragment_menu) {

    private lateinit var binding: FragmentMenuBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuBinding.bind(view)

        binding.btSearchPlayer.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_playerSearchFragment)
        }

    }
}