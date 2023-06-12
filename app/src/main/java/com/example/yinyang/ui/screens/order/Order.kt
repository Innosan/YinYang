package com.example.yinyang.ui.screens.order

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yinyang.R
import com.example.yinyang.ui.screens.destinations.OrdersDestination
import com.example.yinyang.ui.screens.destinations.ProfileDestination
import com.example.yinyang.ui.screens.order.components.OrderCard
import com.example.yinyang.ui.shared.components.ModalDatePicker
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.ui.shared.components.SectionHeader
import com.example.yinyang.ui.shared.components.TotalBlock
import com.example.yinyang.ui.shared.styles.buttonTextStyle
import com.example.yinyang.utils.formatDate
import com.example.yinyang.utils.getTotal
import com.example.yinyang.viewmodels.ProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalMaterial3Api::class
)
@Destination
@Composable
fun Order(
    navigator: DestinationsNavigator,
    profileViewModel: ProfileViewModel
) {
    val colorScheme = MaterialTheme.colorScheme

    val profile = profileViewModel.profile.value
    val cart = profile.userCart?.value

    val switchOptions = listOf(R.string.delivery, R.string.pick_up)

    var selectedOptionIndex by remember { mutableStateOf(0) }
    var selectedChipIndex by remember { mutableStateOf(0) }

    val selectedDate = remember {
        mutableStateOf<LocalDate>(
            Instant
                .now()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        )
    }

    var selectedAddress by remember { mutableStateOf("")}
    selectedAddress = profile.userAddresses?.value?.get(selectedChipIndex)?.address ?: ""
    
    var deliveryNote by remember { mutableStateOf("")}

    val dateDialogController = remember { mutableStateOf(false) }

    val total = getTotal(cart)

    ScreenContainer(
        contentSpacing = 24
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            SectionHeader(iconId = R.drawable.ic_orders, title = R.string.order_screen)

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
                .height(40.dp)
                .background(
                    colorScheme.onSurfaceVariant,
                    RoundedCornerShape(12.dp)
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            switchOptions.forEachIndexed { index, option ->
                Box(
                    modifier = Modifier
                        .width(160.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { selectedOptionIndex = index }
                        .background(
                            if (selectedOptionIndex == index)
                                colorScheme.primary
                            else
                                Color.Transparent,

                            RoundedCornerShape(12.dp)
                        ),

                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = option),
                        color = colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        fontWeight = if (selectedOptionIndex == index) FontWeight.ExtraBold else FontWeight.Normal,
                    )
                }
            }
        }

        Column {
            AnimatedVisibility(
                visible = selectedOptionIndex != 1,
            ) {
                Column() {
                    SectionHeader(
                        iconId = R.drawable.ic_location,
                        title = R.string.order_location_section
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        profile.userAddresses?.value?.forEachIndexed { index, deliveryAddress ->
                            ElevatedFilterChip(
                                onClick = {
                                    selectedChipIndex = index
                                    selectedAddress = deliveryAddress.address
                                },

                                label = { Text(deliveryAddress.address) },

                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_order_location),
                                        contentDescription = "Localized description",
                                        Modifier.size(FilterChipDefaults.IconSize)
                                    )
                                },
                                selected = index == selectedChipIndex,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = colorScheme.primary,
                                    selectedLeadingIconColor = colorScheme.onPrimary,
                                    selectedLabelColor = colorScheme.onPrimary,

                                    containerColor = colorScheme.onSurfaceVariant,
                                    iconColor = colorScheme.onSurface
                                )
                            )
                        }
                    }

                    OutlinedTextField(
                        value = deliveryNote,
                        onValueChange = { deliveryNote = it },
                        label = {
                            Text(text = stringResource(id = R.string.delivery_note_field_label))
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.delivery_note_field_placeholder))
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delivery_note),

                                contentDescription = "Delivery note field"
                            )
                        }
                    )
                }
            }
        }

        Column() {
            AnimatedVisibility(visible = selectedOptionIndex != 0) {
                Column() {
                    SectionHeader(iconId = R.drawable.ic_pick_date, title = R.string.order_date_section)

                    Button(
                        onClick = {
                            dateDialogController.value = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.onSurfaceVariant
                        ),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text(
                            text = formatDate(selectedDate.value),
                            style = buttonTextStyle,
                            fontSize = 20.sp,
                        )
                    }

                    ModalDatePicker(
                        pickedDate = selectedDate,
                        dialogController = dateDialogController
                    )
                }
            }
        }

        TotalBlock(total = total)

        Button(
            onClick = {
                profileViewModel.getUserId()?.let {
                    profileViewModel.profile.value.userCart?.value?.let { it1 ->
                        total?.price?.let { it2 ->
                            profileViewModel.orderManager.createNewOrder(
                                it,
                                it1,
                                it2,
                                selectedAddress,
                                deliveryNote
                            )
                        }
                    }
                }

                navigator.navigate(OrdersDestination) {
                    popUpTo(ProfileDestination.route)
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(id = R.string.make_order_button).uppercase(),
                fontWeight = FontWeight.Black,
                style = buttonTextStyle
            )
        }
    }
}