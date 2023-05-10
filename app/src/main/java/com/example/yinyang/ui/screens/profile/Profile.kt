package com.example.yinyang.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.yinyang.R
import com.example.yinyang.repository.UserRepository
import com.example.yinyang.network.client
import com.example.yinyang.repository.AddressRepository
import com.example.yinyang.ui.shared.components.*
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

    var userInfoPopupControl by remember { mutableStateOf(false) }

    ScreenContainer {
        Column {
            Text(
                text = Screen.Profile.screenTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black
            )

            Row {
                Column {
                    if (userInfo != null) {
                        Text(
                            text = "${userInfo.firstName}\n${userInfo.lastName}",
                            fontWeight = FontWeight.Bold
                        )
                        //Text(text = userInfo.id.toString())
                        Text(
                            text = getRatingTitle(userInfo.rating).title.uppercase(),
                            modifier = Modifier
                                .background(darkColorScheme().secondary, RoundedCornerShape(10.dp))
                        )
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

            if (userSession != null) {
                SectionHeader(iconId = R.drawable.ic_profile, title = "Личное")

                userSession.email?.let {
                    UserInfoFiled(
                        icon = R.drawable.ic_email,
                        fieldLabel = it)
                }
                userSession.phone?.let {
                    UserInfoFiled(
                        icon = R.drawable.ic_phone,
                        fieldLabel = it.ifEmpty { "No phone provided" })
                }
            }

            if (userAddresses != null) {
                SectionHeader(iconId = R.drawable.ic_location, title = "Ваши адреса")

                AddressList(items = userAddresses.value, userViewModel = profileViewModel)
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
                                Text(text = "Name")
                            },
                            placeholder = {
                                Text(text = "Update your name...")
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
                                Text(text = "Last name")
                            },
                            placeholder = {
                                Text(text = "Update your last name...")
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
                                userInfo.id?.let { profileViewModel.updateUserInfo(it, updatedName, updatedLastname) }

                                userInfoPopupControl = false
                            }
                        }) {
                            Text(text = "Update")
                        }
                    }
                }
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