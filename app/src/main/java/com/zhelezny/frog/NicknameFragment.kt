package com.zhelezny.frog

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zhelezny.frog.databinding.FragmentNicknameBinding

const val APP_PREFERENCES = "APP_PREFERENCES"
const val PREF_NICKNAME = "PREF_NICKNAME"

class NicknameFragment : Fragment(R.layout.fragment_nickname) {

    private lateinit var binding: FragmentNicknameBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNicknameBinding.bind(view)

        val pref = requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val nickname =  pref.getString(PREF_NICKNAME, "")
        if (nickname?.isNotEmpty() == true)
            binding.etNickname.setText(nickname)

        binding.btCreateNickname.setOnClickListener {

            pref.edit().putString(PREF_NICKNAME, binding.etNickname.text.toString()).apply()

            findNavController().navigate(R.id.action_nicknameFragment_to_menuFragment)
        }

        if (!isNetworkAvailable(requireContext())) {
            Toast.makeText(requireContext(), "Отсутствует соединение с интернетом", Toast.LENGTH_LONG).show()
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }
}