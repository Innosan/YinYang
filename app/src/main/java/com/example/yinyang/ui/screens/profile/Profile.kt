package com.example.yinyang.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.yinyang.R
import com.example.yinyang.repository.UserRepository
import com.example.yinyang.ui.shared.components.ProfileNavigationButton
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.network.client
import com.example.yinyang.repository.AddressRepository
import com.example.yinyang.ui.shared.components.AddressList
import com.example.yinyang.utils.*
import com.example.yinyang.viewmodels.ProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.launch

val userRepository = UserRepository(client)
val addressRepository = AddressRepository(client)

@Destination
@Composable
fun Profile(
    navigator: DestinationsNavigator,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val userActionsHandler = UserActionsHandler(context)

    val profileViewModel = remember (userRepository) {
        ProfileViewModel(userRepository, addressRepository)
    }

    val profile = profileViewModel.profile.value
    val userInfo = profile.userInfo?.value
    val userSession = profile.userSession
    val userAddresses = profile.userAddresses

    var updatedName by remember { mutableStateOf("")}
    var updatedLastname by remember { mutableStateOf("") }

    var popupControl by remember { mutableStateOf(false) }
    var userInfoPopupControl by remember { mutableStateOf(false) }

    ScreenContainer {
        Column {
            Text(text = Screen.Profile.screenTitle)

            Row {
                Column {
                    if (userInfo != null) {
                        Text(text = "${userInfo.firstName}\n${userInfo.lastName}")
                        //Text(text = userInfo.id.toString())
                        Text(text = getRatingTitle(userInfo.rating).title.uppercase())
                    }
                }
            }
            
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                ProfileNavigationButton(
                    title = "Orders",
                    icon = R.drawable.ic_orders,
                    fraction = .45f,
                    navigator = navigator,
                    destination = Screen.About.destination
                )
                ProfileNavigationButton(
                    title = "Favorite",
                    icon = R.drawable.ic_favorite,
                    fraction = .85f,
                    navigator = navigator,
                    destination = Screen.Settings.destination
                )
            }

            Text(text = "Personal")

            if (userSession != null) {
                userSession.email?.let { Text(text = it) }
                userSession.phone?.let { Text(text = it.ifEmpty { "No phone provided" }) }
            }

            Row(
                modifier = Modifier.clickable { popupControl = true }
            ) {
                Text(text = "Addresses")
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
                        if (userAddresses != null) {
                            AddressList(items = userAddresses.value, userViewModel = profileViewModel)
                        }
                    }
                }
            }
            
            Button(onClick = { 
                userInfoPopupControl = true
            }) {
                Text(text = "Edit Profile")
            }

            if (userInfoPopupControl) {
                Popup(
                    onDismissRequest = { userInfoPopupControl = false },
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
                        Button(onClick = { userInfoPopupControl = false }) {
                            Text(text = "Close")
                        }

                        OutlinedTextField(
                            value = updatedName,
                            onValueChange = { updatedName = it },
                            label = {
                                Text(text = "Address line")
                            },
                            placeholder = {
                                Text(text = "Update address...")
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_email),

                                    contentDescription = "Address field"
                                )
                            }
                        )
                        OutlinedTextField(
                            value = updatedLastname,
                            onValueChange = { updatedLastname = it },
                            label = {
                                Text(text = "Address line")
                            },
                            placeholder = {
                                Text(text = "Update address...")
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_email),

                                    contentDescription = "Address field"
                                )
                            }
                        )

                        Button(onClick = {
                            if (userInfo != null) {
                                userInfo.id?.let { profileViewModel.updateUserInfo(it, updatedName, updatedLastname) }

                                userInfoPopupControl = false
                            }
                        }) {
                            Text(text = "Update userinfo")
                        }
                    }
                }
            }

            Button(onClick = {
                println(client.gotrue.sessionStatus.value)
            }) {
                Text(text = "Check Session status")
            }

            Button(onClick = {
                coroutineScope.launch {
                    userActionsHandler.performUserAction(UserAction.LOGOUT)
                }
            }) {
                Text(text = "Log out")
            }
        }
    }
}