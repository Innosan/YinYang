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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.yinyang.models.Payment
import com.example.yinyang.network.client
import com.example.yinyang.ui.screens.orders.components.OrderItemCard
import com.example.yinyang.ui.shared.components.containers.BackgroundedText
import com.example.yinyang.ui.shared.components.containers.ScreenContainer
import com.example.yinyang.ui.shared.components.service.PaymentWebView
import com.example.yinyang.ui.shared.styles.buttonTextStyle
import com.example.yinyang.utils.OrderStatus
import com.example.yinyang.utils.Screen
import com.example.yinyang.utils.getPaymentBody
import com.example.yinyang.viewmodels.OrderViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.jan.supabase.functions.functions
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.*

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

    val coroutineScope = rememberCoroutineScope()

    val paymentToken = mutableStateOf("")

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

        BackgroundedText(textId = statusMessage.message)

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
                onClick = {
                    coroutineScope.launch {
                        val paymentInfo = client.functions.invoke(
                            function = "get-payment-token",
                            body = getPaymentBody(orderId, orderTotal),
                            headers = Headers.build {
                                append(HttpHeaders.ContentType, "application/json")
                            }
                        )

                        paymentToken.value = paymentInfo.body<Payment>().confirmation.confirmationToken
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.pay_button),
                    style = buttonTextStyle
                )
            }
        }

        PaymentWebView(paymentToken = paymentToken.value)

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