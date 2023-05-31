package com.example.yinyang.ui.screens.orders

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.yinyang.R
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.ui.shared.components.SectionHeader
import com.example.yinyang.viewmodels.ProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun Orders(
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
                modifier = Modifier.clickable {

                }
            ) {
                Text(text = order.id.toString())
                Text(text = order.createdAt.toString())
            }
        }
    }
}