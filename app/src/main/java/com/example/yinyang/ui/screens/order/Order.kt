package com.example.yinyang.ui.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.yinyang.R
import com.example.yinyang.ui.screens.order.components.OrderCard
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.ui.shared.components.SectionHeader
import com.example.yinyang.utils.Total
import com.example.yinyang.viewmodels.ProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun Order(
    profileViewModel: ProfileViewModel
) {
    val profile = profileViewModel.profile.value
    val cart = profile.userCart?.value

    val switchOptions = listOf("Delivery", "Pick-up")
    var selectedOptionIndex by remember { mutableStateOf(0) }

    val total = cart?.fold(Total(0, 0, 0)) { acc, cartItem ->
        Total(
            price = acc.price + (cartItem.product_id.price * cartItem.quantity),
            weight = acc.weight + (cartItem.product_id.weight * cartItem.quantity),
            quantity = acc.quantity + (cartItem.quantity)
        )
    }

    ScreenContainer {
        SectionHeader(iconId = R.drawable.ic_favorite, title = R.string.order_screen)

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            cart?.forEachIndexed { index, cartItem ->
                OrderCard(
                    orderItem = cartItem,
                    quantity = cartItem.quantity
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    RoundedCornerShape(12.dp)
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            switchOptions.forEachIndexed { index, option ->
                Box(
                    modifier = Modifier
                        .width(160.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { selectedOptionIndex = index }
                        .background(
                            if (selectedOptionIndex == index)
                                MaterialTheme.colorScheme.primary
                            else
                                Color.Transparent,

                            RoundedCornerShape(12.dp)
                        ),

                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option,
                        color = if (selectedOptionIndex == index) Color.White else Color.Black,
                        textAlign = TextAlign.Center,
                        fontWeight = if (selectedOptionIndex == index) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        profile.userAddresses?.value?.forEachIndexed { index, deliveryAddress ->
            AssistChip(
                onClick = { /* Do something! */ },
                label = { Text(deliveryAddress.address) },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = "Localized description",
                        Modifier.size(AssistChipDefaults.IconSize)
                    )
                }
            )
        }

        Button(onClick = {
            profileViewModel.getUserId()?.let {
                profileViewModel.profile.value.userCart?.value?.let { it1 ->
                    total?.price?.let { it2 ->
                        profileViewModel.orderManager.createNewOrder(
                            it,
                            it1,
                            it2
                        )
                    }
                }
            }
        }) {
            Text(text = stringResource(id = R.string.order_screen))
        }
    }
}