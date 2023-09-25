package com.example.yinyang.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yinyang.models.OrderItem
import com.example.yinyang.models.Payment
import com.example.yinyang.network.client
import com.example.yinyang.repository.OrderRepository
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    private val repository = OrderRepository(client)
    private val orderItems = mutableStateOf(emptyList<OrderItem>())

    //private val orderPayment = MutableLiveData(Payment())

    fun getOrderItems(orderId: Int): List<OrderItem> {
        viewModelScope.launch {
            orderItems.value = repository.getOrderItems(orderId = orderId)
        }

        return orderItems.value
    }

//    fun getPaymentToken(
//        orderId: Int,
//        orderTotalPrice: Int
//    ) : Payment? {
//        viewModelScope.launch {
//            orderPayment.postValue(repository.getPaymentToken(orderId, orderTotalPrice))
//        }
//
//        return orderPayment.value
//    }
}