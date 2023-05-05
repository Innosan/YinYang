package com.example.yinyang.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.yinyang.R
import com.example.yinyang.ui.shared.components.ProfileNavigationButton
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.ui.shared.models.user.ProfileViewModel
import com.example.yinyang.ui.shared.models.user.UserRepository
import com.example.yinyang.ui.utils.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.launch

val userRepository = UserRepository(client)

@Destination
@Composable
fun Profile(
    navigator: DestinationsNavigator,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val userActionsHandler = UserActionsHandler(context)

    val profileViewModel = remember (userRepository) {
        ProfileViewModel(userRepository)
    }
    val profile = profileViewModel.profile.value
    val userInfo = profile.userInfo
    val userSession = profile.userSession
    val userAddresses = profile.userAddresses

    ScreenContainer {
        Column() {
            Text(text = Screen.Profile.screenTitle)

            Row() {
                Text(text = "Image")
                Column() {
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

            Text(text = "Addresses")
            Column() {
                userAddresses?.forEach { deliveryAddress ->
                    Text(text = deliveryAddress.address)
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