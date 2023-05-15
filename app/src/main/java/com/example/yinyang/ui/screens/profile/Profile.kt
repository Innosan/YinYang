package com.example.yinyang.ui.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yinyang.R
import com.example.yinyang.ui.screens.destinations.CartDestination
import com.example.yinyang.ui.screens.destinations.FavoriteDestination
import com.example.yinyang.ui.shared.components.*
import com.example.yinyang.utils.*
import com.example.yinyang.viewmodels.ProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Destination
@Composable
fun Profile(
    navigator: DestinationsNavigator,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val userActionsHandler = UserActionsHandler(context)

    val profile = viewModel.profile.value
    val userInfo = profile.userInfo?.value
    val userSession = profile.userSession
    val userAddresses = profile.userAddresses

    val profileLoaded = userInfo != null

    var updatedName by remember { mutableStateOf("")}
    var updatedLastname by remember { mutableStateOf("") }

    var userInfoPopupControl by remember { mutableStateOf(false) }
    val dialogControl = remember { mutableStateOf(false)  }

    ScreenContainer {
        CenteredContainer {
            if (!profileLoaded) {
                CircularProgressIndicator()
            }
        }

        AnimatedVisibility(
            visible = profileLoaded,
            content = {
                Column(
                    modifier = Modifier.padding(vertical = 20.dp),
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),

                        ) {
                        if (userInfo != null) {
                            Text(
                                text = "${userInfo.firstName}\n${userInfo.lastName}",
                                fontWeight = FontWeight.Black,
                                fontSize = 24.sp,
                            )
                            //Text(text = userInfo.id.toString())
                            Row(
                                modifier = Modifier
                                    .background(
                                        Color.White.copy(0.14f),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_rating),
                                    contentDescription = "Your rating"
                                )

                                Text(
                                    text = stringResource(id = getRatingTitle(userInfo.rating).title).uppercase(),
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 28.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        ProfileNavigationButton(
                            title = R.string.orders_button,
                            icon = R.drawable.ic_orders,
                            fraction = .45f,
                            onNavigation = {
                                if (userInfo != null) {
                                    navigator.navigate(FavoriteDestination(userId = userInfo.id))
                                }
                            }
                        )

                        ProfileNavigationButton(
                            title = R.string.favorite_button,
                            icon = R.drawable.ic_favorite,
                            fraction = .85f,
                            onNavigation = {
                                if (userInfo != null) {
                                    navigator.navigate(CartDestination(userId = userInfo.id))
                                }
                            }
                        )
                    }

                    if (userSession != null) {
                        SectionHeader(iconId = R.drawable.ic_profile, title = R.string.personal_section)

                        userSession.email?.let {
                            UserInfoFiled(
                                icon = R.drawable.ic_email,
                                fieldLabel = it,
                                spacedBy = 12
                            )
                        }

                        Spacer(modifier = Modifier.size(12.dp))

                        userSession.phone?.let {
                            UserInfoFiled(
                                icon = R.drawable.ic_phone,
                                fieldLabel = it.ifEmpty { stringResource(id = R.string.no_phone_note) },
                                spacedBy = 12
                            )
                        }
                    }

                    if (userAddresses != null) {
                        SectionHeader(iconId = R.drawable.ic_location, title = R.string.addresses_section)

                        AddressList(items = userAddresses.value, userViewModel = viewModel)
                    }

                    Button(
                        onClick = {
                            userInfoPopupControl = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.edit_profile_button),
                            fontWeight = FontWeight.Black,
                            fontSize = 24.sp,
                        )
                    }
                    if (userInfoPopupControl) {
                        Popup(
                            onDismissRequest = { userInfoPopupControl = false },
                            properties = CustomPopupProperties,
                            popupPositionProvider = CenterPositionProvider(),
                        ) {
                            PopupContainer {
                                Button(onClick = { userInfoPopupControl = false }) {
                                    Text(text = stringResource(id = R.string.close_button))
                                }

                                OutlinedTextField(
                                    value = updatedName,
                                    onValueChange = { updatedName = it },
                                    label = {
                                        Text(text = stringResource(id = R.string.update_name_field_label))
                                    },
                                    placeholder = {
                                        Text(text = stringResource(id = R.string.update_name_field_placeholder))
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_email),

                                            contentDescription = "Name field"
                                        )
                                    }
                                )

                                OutlinedTextField(
                                    value = updatedLastname,
                                    onValueChange = { updatedLastname = it },
                                    label = {
                                        Text(text = stringResource(id = R.string.update_last_name_field_label))
                                    },
                                    placeholder = {
                                        Text(text = stringResource(id = R.string.update_last_name_field_placeholder))
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_email),

                                            contentDescription = "Last name field"
                                        )
                                    }
                                )

                                Button(onClick = {
                                    if (userInfo != null) {
                                        userInfo.id?.let {
                                            viewModel.updateUserInfo(it, updatedName, updatedLastname)
                                        }

                                        userInfoPopupControl = false
                                    }
                                }) {
                                    Text(text = stringResource(id = R.string.update_button))
                                }
                            }
                        }
                    }

                    Button(onClick = { dialogControl.value = true }) {
                        Text(text = stringResource(id = R.string.logout_button))
                    }

                    if (dialogControl.value) {
                        AlertDialog(
                            onDismissRequest = {
                                dialogControl.value = false
                            },
                            title = {
                                Text(text = stringResource(id = R.string.logout_button))
                            },
                            text = {
                                Text(text = stringResource(id = R.string.exit_note))
                            },
                            confirmButton = {
                                Button(onClick = {
                                    coroutineScope.launch {
                                        userActionsHandler.performUserAction(UserAction.LOGOUT)
                                    }
                                }) {
                                    Text(text = stringResource(id = R.string.yes_button))
                                }
                            },
                            dismissButton = {
                                Button(
                                    onClick = {
                                        dialogControl.value = false
                                    }) {
                                    Text(text = stringResource(id = R.string.no_button))
                                }
                            }
                        )
                    }
                }
            }
        )
    }
}