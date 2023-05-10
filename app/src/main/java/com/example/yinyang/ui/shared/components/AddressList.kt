package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.yinyang.R
import com.example.yinyang.models.DeliveryAddress
import com.example.yinyang.utils.CenterPositionProvider
import com.example.yinyang.utils.SwipeBackground
import com.example.yinyang.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressList(items: List<DeliveryAddress>, userViewModel: ProfileViewModel) {
    var popupControl by remember { mutableStateOf(false) }

    var newAddress by remember { mutableStateOf("")}
    
    Column {
        Text(text = "Ваши адреса")

        items.forEach {address ->
            val currentItem by rememberUpdatedState(newValue = address)
            
            val dismissState = rememberDismissState(
                confirmValueChange = {
                    when (it) {
                        DismissValue.DismissedToStart -> {
                            currentItem.id?.let { it1 ->
                                println(it1)
                                userViewModel.deleteAddress(it1)
                            }
                            true
                        }
                        else -> { false }
                    }
                }
            )

            SwipeToDismiss(
                state = dismissState,
                background = {
                    SwipeBackground(dismissState = dismissState)
                },
                dismissContent = {
                    Row {
                        Text(text = address.address)
                        IconButton(onClick = {

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_settings),

                                contentDescription = "Edit address"
                            )
                        }
                    }
                }
            )
        }
        
        Button(onClick = { popupControl = true }) {
            Text(text = "Add")
        }

        if (popupControl) {
            Popup(
                onDismissRequest = { popupControl = false },
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = false,
                    excludeFromSystemGesture = true,
                    clippingEnabled = true,
                ),

                popupPositionProvider = CenterPositionProvider(),
            ) {
                Column {
                    Button(onClick = { popupControl = false }) {
                        Text(text = "Close")
                    }
                    
                    OutlinedTextField(
                        value = newAddress,
                        onValueChange = { newAddress = it },
                        label = {
                            Text(text = "E-Mail")
                        },
                        placeholder = {
                            Text(text = "Type in your e-mail...")
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_email),

                                contentDescription = "E-Mail field"
                            )
                        }
                    )
                    
                    Button(onClick = {
                        userViewModel.profile.value.userInfo?.id?.let {
                            userViewModel.addAddress(it, newAddress)
                        }

                        popupControl = false
                    }) {
                        Text(text = "Add new")
                    }
                }
            }
        }
    }
}