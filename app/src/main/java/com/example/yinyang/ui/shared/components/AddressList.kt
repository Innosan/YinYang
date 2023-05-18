package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yinyang.R
import com.example.yinyang.models.DeliveryAddress
import com.example.yinyang.ui.theme.OverpassFamily
import com.example.yinyang.utils.*
import com.example.yinyang.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressList(items: MutableState<List<DeliveryAddress>>, userViewModel: ProfileViewModel) {
    val updateAddressDialogControl = remember { mutableStateOf(false)  }
    val newAddressDialogControl = remember { mutableStateOf(false)  }

    val currentId = remember { mutableStateOf(0)}

    var newAddress by remember { mutableStateOf("")}
    var updatedAddress by remember { mutableStateOf("") }
    
    Column {
        items.value.forEach {address ->
            val currentItem by rememberUpdatedState(newValue = address)
            
            val dismissState = rememberDismissState(
                confirmValueChange = {
                    when (it) {
                        DismissValue.DismissedToStart -> {
                            currentItem.id?.let { it1 ->
                                userViewModel.addressManager.deleteAddress(it1)
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
                        cornerShapeSize = 10.dp
                    )
                },

                dismissContent = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                4.dp,
                                RoundedCornerShape(10.dp),
                                ambientColor = Color.Black,
                                spotColor = Color.Black
                            )
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
                            updateAddressDialogControl.value = true
                            currentId.value = address.id!!
                            println(currentId.value)
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_edit_location),

                                contentDescription = "Edit address"
                            )
                        }
                    }

                    if (updateAddressDialogControl.value) {
                        AlertDialog(
                            onDismissRequest = {
                                updateAddressDialogControl.value = false
                            },
                            title = {
                                Text(text = stringResource(id = R.string.update_address_note))
                            },
                            text = {
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
                            },
                            confirmButton = {
                                Button(onClick = {
                                    userViewModel.addressManager.updateAddress(currentId.value, updatedAddress)

                                    // TODO: stop using viewmodel instance

                                    updateAddressDialogControl.value = false
                                }) {
                                    Text(text = stringResource(id = R.string.update_button))
                                }
                            },
                            dismissButton = {
                                Button(
                                    onClick = {
                                        updateAddressDialogControl.value = false
                                    }) {
                                    Text(text = stringResource(id = R.string.close_button))
                                }
                            }
                        )
                    }
                }
            )
        }

        CenteredContainer {
            Button(
                onClick = { newAddressDialogControl.value = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(0.14f),
                ),
                shape = RoundedCornerShape(7.dp),
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.add_button).uppercase(),
                    fontFamily = OverpassFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (newAddressDialogControl.value) {
            AlertDialog(
                onDismissRequest = {
                    newAddressDialogControl.value = false
                },
                title = {
                    Text(text = stringResource(id = R.string.add_address_note))
                },
                text = {
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
                },
                confirmButton = {
                    Button(onClick = {
                        userViewModel.profile.value.userInfo?.value?.id?.let {
                            userViewModel.addressManager.addAddress(it, newAddress)
                        }

                        newAddressDialogControl.value = false
                    }) {
                        Text(text = stringResource(id = R.string.add_button))
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            newAddressDialogControl.value = false
                        }) {
                        Text(text = stringResource(id = R.string.close_button))
                    }
                }
            )
        }
    }
}