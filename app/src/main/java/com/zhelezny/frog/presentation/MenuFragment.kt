package com.zhelezny.frog.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zhelezny.frog.R
import com.zhelezny.frog.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private lateinit var binding: FragmentMenuBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuBinding.bind(view)

        binding.btSearchPlayer.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_playerSearchFragment)
        }

        doubleClickToExit()
    }

    private fun doubleClickToExit() {

        var doubleBackToExitPressedOnce = false
        val callback = requireActivity().onBackPressedDispatcher.addCallback(requireActivity()) {
            if (doubleBackToExitPressedOnce) {
                requireActivity().finish()
            }

            doubleBackToExitPressedOnce = true
            Toast.makeText(requireActivity(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        }
    }
}