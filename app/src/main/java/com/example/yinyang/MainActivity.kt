package com.example.yinyang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.yinyang.ui.screens.home.HomePage
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yinyang.ui.screens.about.About
import com.example.yinyang.ui.screens.help.Help
import com.example.yinyang.ui.screens.profile.Profile
import com.example.yinyang.ui.screens.settings.Settings
import com.example.yinyang.ui.screens.signin.SignIn
import com.example.yinyang.ui.shared.models.navItems
import com.example.yinyang.ui.theme.YinYangTheme
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YinYangTheme(
                darkTheme = true
            ) {
                navController = rememberNavController()
                
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                val selectedItem = remember { mutableStateOf(navItems[0]) }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        navItems.forEach { item ->
                            NavigationDrawerItem(
                                icon = {
                                    Icon(
                                        painter = painterResource(id = item.icon),

                                        contentDescription = item.title
                                    )
                                },
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(.7f),
                                label = { Text(text = item.title)},
                                selected = item == selectedItem.value,

                                onClick = {
                                    navController.navigate(route = item.route)
                                    scope.launch { drawerState.close() }
                                    selectedItem.value = item
                                }
                            )
                        }
                    },
                    content = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            NavHost(navController = navController, startDestination = "home") {
                                composable("home") {
                                    HomePage()
                                }
                                composable("sign-in") {
                                    SignIn()
                                }
                                composable("about") {
                                    About()
                                }
                                composable("profile") {
                                    Profile()
                                }
                                composable("settings") {
                                    Settings()
                                }
                                composable("help") {
                                    Help()
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}