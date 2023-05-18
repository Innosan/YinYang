package com.example.yinyang.managers

import androidx.compose.runtime.MutableState
import com.example.yinyang.repository.AddressRepository
import com.example.yinyang.viewmodels.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AddressManager(
    private val viewModelScope: CoroutineScope,
    private val addressRepository: AddressRepository,
    private val profile: MutableState<ProfileViewModel.Profile>
) {
    fun addAddress(userId: Int, addressMessage: String) {
        viewModelScope.launch {
            addressRepository.addAddress(userId, addressMessage)
            updateAddresses()
        }
    }

    fun deleteAddress(addressId: Int) {
        viewModelScope.launch {
            addressRepository.deleteAddress(addressId)
            updateAddresses()
        }
    }

    fun updateAddress(addressId: Int, newAddress: String) {
        viewModelScope.launch {
            addressRepository.updateAddress(addressId, newAddress)
            updateAddresses()
        }
    }

    private suspend fun updateAddresses() {
        val updatedAddresses = addressRepository.getUserAddresses(profile.value.userInfo?.value?.id)
        profile.value = profile.value.copy(userAddresses = updatedAddresses)
    }
}