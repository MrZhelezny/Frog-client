package com.zhelezny.frog.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.zhelezny.frog.R
import com.zhelezny.frog.data.storage.IpStorage
import com.zhelezny.frog.databinding.FragmentSettingsBinding
import org.koin.android.ext.android.inject

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding

    private val ipStorage: IpStorage by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        binding.editIpServer.setText(ipStorage.getIp())

        binding.btnApply.setOnClickListener {
            Toast.makeText(requireContext(), "Адрес сервера сохранён", Toast.LENGTH_SHORT).show()
            ipStorage.saveIp(binding.editIpServer.text.toString())
        }

    }

}