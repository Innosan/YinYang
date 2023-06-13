package com.example.yinyang.ui.screens.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yinyang.R
import com.example.yinyang.ui.screens.destinations.OrderViewDestination
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.ui.shared.components.SectionHeader
import com.example.yinyang.viewmodels.ProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun Orders(
    navigator: DestinationsNavigator,
    profileViewModel: ProfileViewModel
) {
    val orders = profileViewModel.profile.value.userOrders

    println(orders)

    ScreenContainer(
        contentSpacing = 20
    ) {
        SectionHeader(iconId = R.drawable.ic_orders, title = R.string.orders_screen)

        orders?.value?.forEachIndexed { index, order ->
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .clickable {
                        navigator.navigate(
                            OrderViewDestination(
                                orderId = order.id,
                                orderStatus = order.status_id.title
                            )
                        )
                    }
                    .background(
                        MaterialTheme.colorScheme.onSurfaceVariant,
                        RoundedCornerShape(14.dp)
                    )
                    .padding(10.dp)
                    
            ) {
                Column() {
                    Text(text = stringResource(id = R.string.order_screen) + " №" + order.id)

                    Text(text = "${order.total_price} ₽")
                }

                Column() {
                    Text(text = order.status_id.title)
                    Text(text = order.created_at.toString())
                }
            }
        }
    }
}