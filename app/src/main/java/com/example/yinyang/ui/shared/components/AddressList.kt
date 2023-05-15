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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.yinyang.R
import com.example.yinyang.models.DeliveryAddress
import com.example.yinyang.utils.*
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
                modifier = Modifier.padding(vertical = 10.dp),

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
                                    Text(text = stringResource(id = R.string.close_button))
                                }

                                OutlinedTextField(
                                    value = updatedAddress,
                                    onValueChange = { updatedAddress = it },
                                    label = {
                                        Text(text = stringResource(id = R.string.update_address_label))
                                    },
                                    placeholder = {
                                        Text(text = stringResource(id = R.string.update_address_placeholder))
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
                                    Text(text = stringResource(id = R.string.update_button))
                                }
                            }
                        }
                    }
                }
            )
        }

        CenteredContainer {
            Button(
                onClick = { newAddressPopUpControl = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(0.14f),
                ),
                shape = RoundedCornerShape(7.dp),
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.add_button),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (newAddressPopUpControl) {
            Popup(
                onDismissRequest = { newAddressPopUpControl = false },
                properties = CustomPopupProperties,
                popupPositionProvider = CenterPositionProvider(),
            ) {
                PopupContainer {
                    Button(onClick = { newAddressPopUpControl = false }) {
                        Text(text = stringResource(id = R.string.close_button))
                    }

                    OutlinedTextField(
                        value = newAddress,
                        onValueChange = { newAddress = it },
                        label = {
                            Text(text = stringResource(id = R.string.update_address_label))
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.new_address_placeholder))
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
                        Text(text = stringResource(id = R.string.add_button))
                    }
                }
            }
        }
    }
}