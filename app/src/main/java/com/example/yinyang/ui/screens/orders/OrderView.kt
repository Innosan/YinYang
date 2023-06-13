package com.example.yinyang.ui.screens.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.viewmodels.OrderViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun OrderView(
    orderId: Int,
    orderStatus: String
) {
    val viewModel = viewModel<OrderViewModel>()
    val orderItems = viewModel.getOrderItems(orderId = orderId)

    ScreenContainer(
        contentSpacing = 20
    ) {
        Text(text = orderId.toString())
        Text(text = orderStatus.uppercase())

        LazyColumn(
            modifier = Modifier
                .height(400.dp),

            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(
                orderItems,

                key = {orderItem -> orderItem.product_id.id}
            ) { orderItem ->
                Text(text = orderItem.product_id.id.toString())
            }
        }
    }
}