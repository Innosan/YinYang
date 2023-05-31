package com.example.yinyang.managers

import com.example.yinyang.models.CartItem
import com.example.yinyang.models.DeliveryAddress
import com.example.yinyang.repository.OrderRepository
import com.example.yinyang.viewmodels.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class OrderManager(
    private val viewModelScope: CoroutineScope,
    private val orderRepository: OrderRepository,
    private val cartManager: CartManager,
) {
    fun createNewOrder(
        userId: Int,
        cart: List<CartItem>,
        totalPrice: Int,
        deliveryAddress: String,
        deliveryNote: String,
    ) {
        viewModelScope.launch {
            orderRepository.createNewOrder(
                userId, cart, totalPrice, deliveryAddress, deliveryNote
            )

            cartManager.deleteUserCart(userId)
        }
    }
}