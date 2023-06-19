package com.example.yinyang.ui.screens.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yinyang.R
import com.example.yinyang.ui.screens.orders.components.OrderItemCard
import com.example.yinyang.ui.shared.components.containers.ScreenContainer
import com.example.yinyang.viewmodels.OrderViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun OrderView(
    orderId: Int,
    orderStatusTitle: String,
    orderStatusId: Int,
    orderTotal: Int,
) {
    val viewModel = viewModel<OrderViewModel>()
    val orderItems = viewModel.getOrderItems(orderId = orderId)

    val statusMessage = when (orderStatusId) {
        1 -> R.string.new_status
        2 -> R.string.delivering_status
        3 -> R.string.cooking_status
        4 -> R.string.finished_status
        
        else -> {R.string.error_warning}
    }

    ScreenContainer(
        contentSpacing = 20
    ) {
        Text(text = "${stringResource(id = R.string.order_screen)} №$orderId")
        Text(
            text = orderStatusTitle.uppercase(),
        )

        Text(text = stringResource(id = statusMessage))

        LazyColumn(
            modifier = Modifier
                .height(400.dp),

            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(
                orderItems,

                key = {orderItem -> orderItem.product_id.id}
            ) { orderItem ->
                OrderItemCard(orderItem = orderItem)
            }
        }

        Text(text = "$orderTotal ₽")
    }
}