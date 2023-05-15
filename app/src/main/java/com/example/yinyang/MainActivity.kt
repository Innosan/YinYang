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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yinyang.models.navItems
import com.example.yinyang.ui.screens.NavGraphs
import com.example.yinyang.ui.screens.appCurrentDestinationAsState
import com.example.yinyang.ui.screens.destinations.Destination
import com.example.yinyang.ui.screens.startAppDestination
import com.example.yinyang.ui.theme.YinYangTheme
import com.example.yinyang.network.client
import com.example.yinyang.utils.Screen
import com.example.yinyang.viewmodels.ProfileViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.Route
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YinYangTheme(
                darkTheme = true
            ) {
                navController = rememberNavController()

                val userState = client.gotrue.sessionStatus.collectAsState()
                val isUserAuth = userState.value is SessionStatus.Authenticated

                val currentDestination: Destination = navController.appCurrentDestinationAsState().value
                    ?: NavGraphs.root.startAppDestination
                
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                val selectedItem = remember { mutableStateOf(navItems[0]) }

                /**
                 * To explicitly change start route if user is authenticated
                 */
                val startRoute: Route = if (isUserAuth) {
                    Screen.Home.destination
                } else {
                    Screen.SignIn.destination
                }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    gesturesEnabled = isUserAuth,
                    drawerContent = {
                        ModalDrawerSheet(
                            Modifier.fillMaxWidth(.8f),
                        ) {
                            navItems.forEach { item ->
                                NavigationDrawerItem(
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = item.icon),

                                            contentDescription = stringResource(id = item.title)
                                        )
                                    },

                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth(.7f),

                                    label = { Text(text = stringResource(id = item.title))},
                                    selected = currentDestination == item.destination,

                                    onClick = {
                                        scope.launch { drawerState.close() }
                                        navController.navigate(item.destination) {
                                            launchSingleTop = true
                                            popUpTo(item.destination.route)
                                        }
                                        selectedItem.value = item
                                    },

                                    colors = NavigationDrawerItemDefaults.colors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            navController = navController,
                            startRoute = startRoute
                        )
                    }
                }
            }
        }
    }
}