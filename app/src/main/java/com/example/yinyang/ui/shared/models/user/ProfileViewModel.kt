package com.example.yinyang.ui.shared.models.user

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yinyang.ui.shared.models.DeliveryAddress
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    data class Profile(
        val userInfo: User?,
        val userSession: UserInfo?,
        val userAddresses: List<DeliveryAddress>?,
    )

    private val _profile = mutableStateOf(Profile(null, null, null))
    val profile: MutableState<Profile> = _profile

    init {
        viewModelScope.launch {
            val userInfoDeffered = async { userRepository.getUserInfo() }
            val userSessionDeffered = async { userRepository.getUserSession() }
            val deliveryAddressesDeffered = async {
                userRepository.getUserAddresses(userInfoDeffered.await()?.id)
            }

            _profile.value = Profile(
                userInfoDeffered.await(),
                userSessionDeffered.await(),
                deliveryAddressesDeffered.await()
            )
        }
    }
}