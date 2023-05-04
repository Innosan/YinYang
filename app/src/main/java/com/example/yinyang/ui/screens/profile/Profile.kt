package com.example.yinyang.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.yinyang.R
import com.example.yinyang.ui.shared.components.ProfileNavigationButton
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.ui.shared.models.User
import com.example.yinyang.ui.shared.models.getUser
import com.example.yinyang.ui.utils.Screen
import com.example.yinyang.ui.utils.client
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.launch

@Destination
@Composable
fun Profile(
    navigator: DestinationsNavigator,
) {
    val coroutineScope = rememberCoroutineScope()

    suspend fun logOut() {
        client.gotrue.invalidateSession()
    }

    val (user, setUser) = remember { mutableStateOf<User?>(null) }

    val getUser: () -> Unit = {
        coroutineScope.launch {
            setUser(getUser())
        }
    }

    LaunchedEffect(Unit) {
        getUser()
    }

    ScreenContainer {
        Column() {
            Text(text = Screen.Profile.screenTitle)

            Row() {
                Text(text = "Image")
                Column() {
                    if (user != null) {
                        Text(text = "${user.firstName}\n${user.lastName}")
                        Text(text = user.rating.toString())
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

            Button(onClick = {
                println(client.gotrue.sessionStatus.value)
            }) {
                Text(text = "Check Session status")
            }

            Button(onClick = {
                coroutineScope.launch {
                    logOut()
                }

                navigator.navigate(Screen.SignIn.destination) {

                    popUpTo(Screen.SignIn.destination.route) {
                        inclusive = true
                    }
                }
            }) {
                Text(text = "Log out")
            }
        }
    }
}