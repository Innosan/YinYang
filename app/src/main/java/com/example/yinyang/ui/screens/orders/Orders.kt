package com.example.yinyang.ui.screens.orders

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yinyang.R
import com.example.yinyang.ui.screens.destinations.OrderViewDestination
import com.example.yinyang.ui.shared.components.containers.ScreenContainer
import com.example.yinyang.ui.shared.components.containers.SectionHeader
import com.example.yinyang.utils.DateFormatter
import com.example.yinyang.viewmodels.ProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun Orders(
    navigator: DestinationsNavigator,
    profileViewModel: ProfileViewModel
) {
    val orders = profileViewModel.profile.value.userOrders

    val dateFormatter = DateFormatter()

    ScreenContainer(
        contentSpacing = 20
    ) {
        SectionHeader(iconId = R.drawable.ic_orders, title = R.string.orders_screen)

        orders?.value?.forEachIndexed { index, order ->
            val orderDateTime = object {
                val date = order.created_at?.let { dateFormatter.getLocalDateTime(it).date }
                val time = order.created_at?.let { dateFormatter.getLocalDateTime(it).time }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .clickable {
                        navigator.navigate(
                            OrderViewDestination(
                                orderId = order.id,
                                orderStatusTitle = order.status_id.title,
                                orderStatusId = order.status_id.id,
                                orderTotal = order.total_price,
                            )
                        )
                    }
                    .background(
                        MaterialTheme.colorScheme.onSurfaceVariant,
                        RoundedCornerShape(14.dp)
                    )
                    .padding(10.dp),

                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.order_screen) + " №" + order.id,
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp
                    )

                    Text(
                        text = "${order.total_price} ₽",
                        fontWeight = FontWeight.Black,
                        fontSize = 32.sp
                    )
                }

                Column {
                    Text(
                        text = order.status_id.title.uppercase(),
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(10.dp)
                    )
                    Text(text = orderDateTime.date.toString())
                    Text(
                        text = orderDateTime.time?.let { dateFormatter.formatTime(it) }.toString()
                    )
                }
            }
        }
    }
}