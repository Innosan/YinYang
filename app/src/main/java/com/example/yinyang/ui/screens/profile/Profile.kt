package com.example.yinyang.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.ActivityNavigator
import com.example.yinyang.ui.shared.components.ScreenContainer
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

    ScreenContainer {
        Column() {
            Text(text = "Profile")

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