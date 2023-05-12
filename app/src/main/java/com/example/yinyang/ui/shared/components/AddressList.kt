package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.yinyang.R
import com.example.yinyang.models.DeliveryAddress
import com.example.yinyang.utils.CenterPositionProvider
import com.example.yinyang.utils.CustomPopupProperties
import com.example.yinyang.utils.PopupContainer
import com.example.yinyang.utils.SwipeBackground
import com.example.yinyang.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressList(items: List<DeliveryAddress>, userViewModel: ProfileViewModel) {
    var newAddressPopUpControl by remember { mutableStateOf(false) }
    var updateAddressPopUpControl by remember { mutableStateOf(false) }

    var newAddress by remember { mutableStateOf("")}
    var updatedAddress by remember { mutableStateOf("") }
    
    Column {
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
                    SwipeBackground(
                        dismissState = dismissState,
                        dismissColor = Color.Red.copy(.1f),
                        cornerShapeSize = 10.dp)
                },

                dismissContent = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White.copy(0.14f), RoundedCornerShape(10.dp)),

                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(20.dp),
                            text = address.address,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                        )

                        IconButton(onClick = {
                            updateAddressPopUpControl = true
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_edit_location),

                                contentDescription = "Edit address"
                            )
                        }
                    }

                    if (updateAddressPopUpControl) {
                        Popup(
                            onDismissRequest = { updateAddressPopUpControl = false },
                            properties = CustomPopupProperties,
                            popupPositionProvider = CenterPositionProvider(),
                        ) {
                            PopupContainer {
                                Button(
                                    onClick = { updateAddressPopUpControl = false },
                                ) {
                                    Text(text = "Close")
                                }

                                OutlinedTextField(
                                    value = updatedAddress,
                                    onValueChange = { updatedAddress = it },
                                    label = {
                                        Text(text = "Address line")
                                    },
                                    placeholder = {
                                        Text(text = "Update address...")
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_location),

                                            contentDescription = "Address field"
                                        )
                                    }
                                )

                                Button(onClick = {
                                    currentItem.id?.let { it1 ->
                                        userViewModel.updateAddress(it1, updatedAddress)
                                    }

                                    updateAddressPopUpControl = false
                                }) {
                                    Text(text = "Update")
                                }
                            }
                        }
                    }
                }
            )
        }
        
        Button(onClick = { newAddressPopUpControl = true }) {
            Text(text = "Add")
        }

        if (newAddressPopUpControl) {
            Popup(
                onDismissRequest = { newAddressPopUpControl = false },
                properties = CustomPopupProperties,
                popupPositionProvider = CenterPositionProvider(),
            ) {
                Column {
                    Button(onClick = { newAddressPopUpControl = false }) {
                        Text(text = "Close")
                    }
                    
                    OutlinedTextField(
                        value = newAddress,
                        onValueChange = { newAddress = it },
                        label = {
                            Text(text = "Address line")
                        },
                        placeholder = {
                            Text(text = "Type in your new address...")
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_email),

                                contentDescription = "Address field"
                            )
                        }
                    )
                    
                    Button(onClick = {
                        userViewModel.profile.value.userInfo?.value?.id?.let {
                            userViewModel.addAddress(it, newAddress)
                        }

                        newAddressPopUpControl = false
                    }) {
                        Text(text = "Add new")
                    }
                }
            }
        }
    }
}