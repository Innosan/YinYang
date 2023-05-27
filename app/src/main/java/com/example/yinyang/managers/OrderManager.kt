package com.example.yinyang.managers

import com.example.yinyang.models.CartItem
import com.example.yinyang.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class OrderManager(
    private val viewModelScope: CoroutineScope,
    private val orderRepository: OrderRepository,
    private val cartManager: CartManager
) {
    fun createNewOrder(
        userId: Int,
        cart: List<CartItem>,
        totalPrice: Int,
    ) {
        viewModelScope.launch {
            orderRepository.createNewOrder(
                userId, cart, totalPrice
            )

            cartManager.deleteUserCart(userId)
        }
    }
}