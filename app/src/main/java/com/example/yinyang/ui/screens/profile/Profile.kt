package com.example.yinyang.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.ActivityNavigator
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.ui.shared.models.Product
import com.example.yinyang.ui.shared.models.User
import com.example.yinyang.ui.shared.models.getProducts
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

    getUser()

    ScreenContainer {
        Column() {
            Text(text = Screen.Profile.screenTitle)

            Row() {
                Text(text = "Image")
                Column() {
                    if (user != null) {
                        Text(text = user.firstName + user.lastName)
                        Text(text = user.rating.toString())
                    }
                }
            }
            
            Row() {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Orders")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Favorite")
                }
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