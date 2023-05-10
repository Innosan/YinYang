package com.example.yinyang.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yinyang.models.DeliveryAddress
import com.example.yinyang.models.User
import com.example.yinyang.repository.AddressRepository
import com.example.yinyang.repository.UserRepository
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val addressRepository: AddressRepository
    ) : ViewModel() {
    data class Profile(
        val userInfo: User?,
        val userSession: UserInfo?,
        val userAddresses: MutableState<List<DeliveryAddress>>?,
    )

    private val _profile = mutableStateOf(Profile(null, null, null))
    val profile: MutableState<Profile> = _profile

    fun deleteAddress(addressId: Int) {
        viewModelScope.launch {
            addressRepository.deleteAddress(addressId)

            val updatedAddresses = addressRepository.getUserAddresses(profile.value.userInfo?.id)
            _profile.value = profile.value.copy(userAddresses = updatedAddresses)
        }
    }

    fun addAddress(addressMessage: String, userId: Int) {
        viewModelScope.launch {
            addressRepository.addAddress(addressMessage, userId)

            val updatedAddresses = addressRepository.getUserAddresses(profile.value.userInfo?.id)
            _profile.value = profile.value.copy(userAddresses = updatedAddresses)
        }
    }

    init {
        viewModelScope.launch {
            val userInfoDeffered = async { userRepository.getUserInfo() }
            val userSessionDeffered = async { userRepository.getUserSession() }
            val deliveryAddressesDeffered = async {
                addressRepository.getUserAddresses(userInfoDeffered.await()?.id)
            }

            _profile.value = Profile(
                userInfoDeffered.await(),
                userSessionDeffered.await(),
                deliveryAddressesDeffered.await()
            )
        }
    }
}