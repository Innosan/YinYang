package com.example.yinyang.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yinyang.models.DeliveryAddress
import com.example.yinyang.models.User
import com.example.yinyang.repository.AddressRepository
import com.example.yinyang.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor (
    private val userRepository: UserRepository,
    private val addressRepository: AddressRepository
    ) : ViewModel() {
    data class Profile(
        val userInfo: MutableState<User?>?,
        val userSession: UserInfo?,
        val userAddresses: MutableState<List<DeliveryAddress>>?,
    )

    private val _profile = mutableStateOf(Profile(null, null, null))
    val profile: MutableState<Profile> = _profile

    fun updateUserInfo(userId: Int, newName: String, newLastname: String) {
        viewModelScope.launch {
            userRepository.updateUserInfo(userId, newName, newLastname)

            val updatedUserInfo = userRepository.getUserInfo()
            _profile.value = profile.value.copy(userInfo = updatedUserInfo)
        }
    }

    private suspend fun updateAddresses() {
        val updatedAddresses = addressRepository.getUserAddresses(profile.value.userInfo?.value?.id)
        _profile.value = profile.value.copy(userAddresses = updatedAddresses)
    }

    fun deleteAddress(addressId: Int) {
        viewModelScope.launch {
            addressRepository.deleteAddress(addressId)

            updateAddresses()
        }
    }

    fun addAddress(userId: Int, addressMessage: String) {
        viewModelScope.launch {
            addressRepository.addAddress(userId, addressMessage)

            updateAddresses()
        }
    }

    fun updateAddress(addressId: Int, newAddress: String) {
        viewModelScope.launch {
            addressRepository.updateAddress(addressId, newAddress)

            updateAddresses()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            try {
                val userInfoDeffered = async { userRepository.getUserInfo() }
                val userSessionDeffered = async { userRepository.getUserSession() }
                val deliveryAddressesDeffered = async {
                    addressRepository.getUserAddresses(userInfoDeffered.await().value?.id)
                }

                _profile.value = Profile(
                    userInfoDeffered.await(),
                    userSessionDeffered.await(),
                    deliveryAddressesDeffered.await()
                )
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    init {
        loadProfile()
    }
}