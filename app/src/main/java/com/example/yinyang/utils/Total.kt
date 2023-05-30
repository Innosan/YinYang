package com.example.yinyang.utils

import com.example.yinyang.models.CartItem

data class Total(
    val price: Int,
    val weight: Int,
    val quantity: Int,
)

fun getTotal(cart: List<CartItem>?) : Total? {
    return cart?.fold(Total(0, 0, 0)) { acc, cartItem ->
        Total(
            price = acc.price + (cartItem.product_id.price * cartItem.quantity),
            weight = acc.weight + (cartItem.product_id.weight * cartItem.quantity),
            quantity = acc.quantity + (cartItem.quantity)
        )
    }
}