package com.example.yinyang.ui.screens.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yinyang.R
import com.example.yinyang.ui.screens.orders.components.OrderItemCard
import com.example.yinyang.ui.shared.components.containers.ScreenContainer
import com.example.yinyang.ui.shared.styles.buttonTextStyle
import com.example.yinyang.utils.OrderStatus
import com.example.yinyang.utils.Screen
import com.example.yinyang.viewmodels.OrderViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun OrderView(
    orderId: Int,
    orderStatusTitle: String,
    orderStatusId: Int,
    orderTotal: Int,
    navigator: DestinationsNavigator
) {
    val viewModel = viewModel<OrderViewModel>()
    val orderItems = viewModel.getOrderItems(orderId = orderId)

    val statusMessage: OrderStatus = when (orderStatusId) {
        1 -> OrderStatus(message = R.string.new_status, icon = R.drawable.ic_new_status)
        2 -> OrderStatus(message = R.string.delivering_status, icon = R.drawable.ic_delivering_status)
        3 -> OrderStatus(message = R.string.cooking_status, icon = R.drawable.ic_cooking_status)
        4 -> OrderStatus(message = R.string.finished_status, icon = R.drawable.ic_finished_status)
        
        else -> OrderStatus(message = R.string.error_warning, icon = R.drawable.ic_help)
    }

    ScreenContainer(
        contentSpacing = 20
    ) {
        Text(
            text = "${stringResource(id = R.string.order_screen)} №$orderId",
            fontSize = 24.sp,
            fontWeight = FontWeight.Black
        )

        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSurfaceVariant, RoundedCornerShape(10.dp))
                .padding(10.dp),

            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(id = statusMessage.icon),
                contentDescription = "Order status"
            )

            Text(
                text = orderStatusTitle.uppercase(),
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = stringResource(id = statusMessage.message),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSurfaceVariant, RoundedCornerShape(10.dp))
                .padding(10.dp)
        )

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

        Text(
            text = "$orderTotal ₽",
            fontSize = 34.sp,
            fontWeight = FontWeight.Black
        )
        
        if (orderStatusId == 1) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.pay_button),
                    style = buttonTextStyle
                )
            }
        }

        Text(
            text = stringResource(id = R.string.order_error_warning),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSurfaceVariant, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    navigator.navigate(Screen.About.destination)
                }
                .padding(10.dp)
        )
    }
}