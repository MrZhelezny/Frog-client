package com.zhelezny.frog.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zhelezny.frog.R
import com.zhelezny.frog.data.storage.models.User
import com.zhelezny.frog.data.storage.models.UserStatus
import com.zhelezny.frog.databinding.FragmentNicknameBinding
import com.zhelezny.frog.domain.repository.UserRepository
import com.zhelezny.frog.domain.usecases.GetUidFromServerUseCase
import com.zhelezny.frog.domain.usecases.SaveUserUseCase
import org.koin.android.ext.android.inject

class NicknameFragment : Fragment(R.layout.fragment_nickname) {

    private lateinit var binding: FragmentNicknameBinding

    private val userRepository: UserRepository by inject()
    private val saveUserUseCase: SaveUserUseCase by inject()
    private val getUidFromServerUserUseCase: GetUidFromServerUseCase by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNicknameBinding.bind(view)

        val savedUser = userRepository.get()
        val nickname = savedUser.nickName
        if (nickname.isNotEmpty())
            binding.etNickname.setText(nickname)

        binding.btCreateNickname.setOnClickListener {
            val id = getUidFromServerUserUseCase.execute(binding.etNickname.text.toString() + savedUser.id)
            saveUserUseCase.execute(User(id, binding.etNickname.text.toString(), UserStatus.ONLINE))

            findNavController().navigate(R.id.action_nicknameFragment_to_menuFragment)
        }

        if (!isNetworkAvailable(requireContext())) {
            Toast.makeText(
                requireContext(),
                "Отсутствует соединение с интернетом",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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