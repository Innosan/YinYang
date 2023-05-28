package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.yinyang.R
import com.example.yinyang.models.DeliveryAddress
import com.example.yinyang.ui.theme.OverpassFamily
import com.example.yinyang.utils.*
import com.example.yinyang.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressList(
    addresses: MutableState<List<DeliveryAddress>>,
    userViewModel: ProfileViewModel
) {
    val updateAddressDialogControl = remember { mutableStateOf(false)  }
    val newAddressDialogControl = remember { mutableStateOf(false)  }

    var showMessage by remember { mutableStateOf(false) }

    val currentId = remember { mutableStateOf(0) }

    var newAddress by remember { mutableStateOf("") }
    var updatedAddress by remember { mutableStateOf("") }

    DisplayMessage(
        message = R.string.max_addresses_note,
        showMessage = showMessage,
        onDismiss = { showMessage = false }
    )

    LazyColumn(
        modifier = Modifier.height(addresses.value.size.dp * 100),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(
            addresses.value,

            key = {address -> address.id}
        ) { address ->
            val currentItem by rememberUpdatedState(newValue = address)

            val dismissState = rememberDismissState(
                confirmValueChange = {
                    when (it) {
                        DismissValue.DismissedToStart -> {
                            currentItem.id.let { it1 ->
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
                            .background(
                                MaterialTheme.colorScheme.onSurfaceVariant,
                                RoundedCornerShape(10.dp)
                            ),

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
                            currentId.value = address.id
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
                                AlertDialogButton(buttonType = ButtonType.UPDATE) {
                                    userViewModel.addressManager.updateAddress(currentId.value, updatedAddress)

                                    // TODO: stop using viewmodel instance

                                    updateAddressDialogControl.value = false
                                }
                            },
                            dismissButton = {
                                AlertDialogButton(buttonType = ButtonType.CLOSE) {
                                    updateAddressDialogControl.value = false
                                }
                            }
                        )
                    }
                }
            )
        }
    }

    CenteredContainer {
        Button(
            onClick = { newAddressDialogControl.value = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
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
                AlertDialogButton(buttonType = ButtonType.ADD) {
                    if (addresses.value.size <= 2) {
                        userViewModel.profile.value.userInfo?.value?.id?.let {
                            userViewModel.addressManager.addAddress(it, newAddress)
                        }
                    } else {
                        showMessage = true
                    }

                    newAddressDialogControl.value = false
                }
            },
            dismissButton = {
                AlertDialogButton(buttonType = ButtonType.CLOSE) {
                    newAddressDialogControl.value = false
                }
            }
        )
    }
}