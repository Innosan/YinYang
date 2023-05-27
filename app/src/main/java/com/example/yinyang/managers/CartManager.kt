package com.example.yinyang.managers

import androidx.compose.runtime.MutableState
import com.example.yinyang.repository.CartRepository
import com.example.yinyang.viewmodels.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CartManager(
    private val viewModelScope: CoroutineScope,
    private val cartRepository: CartRepository,
    private val profile: MutableState<ProfileViewModel.Profile>
) {
    fun deleteCartItem(cartItemId: Int) {
        viewModelScope.launch {
            cartRepository.deleteFromCart(cartItemId)
            updateCart()
        }
    }

    fun deleteUserCart(userId: Int) {
        viewModelScope.launch {
            cartRepository.deleteUserCart(userId)
            updateCart()
        }
    }

    fun addToCart(userId: Int, productId: Int, quantity: Int) {
        viewModelScope.launch {
            cartRepository.addToCart(userId, productId, quantity)
            updateCart()
        }
    }

    private suspend fun updateCart() {
        val updatedCart = cartRepository.getCart(profile.value.userInfo?.value?.id)
        profile.value = profile.value.copy(userCart = updatedCart)
    }
}