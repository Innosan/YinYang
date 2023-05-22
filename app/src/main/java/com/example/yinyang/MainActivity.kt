package com.example.yinyang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yinyang.models.navItems
import com.example.yinyang.network.client
import com.example.yinyang.ui.screens.NavGraphs
import com.example.yinyang.ui.screens.appCurrentDestinationAsState
import com.example.yinyang.ui.screens.cart.Cart
import com.example.yinyang.ui.screens.destinations.*
import com.example.yinyang.ui.screens.favorite.Favorite
import com.example.yinyang.ui.screens.home.HomePage
import com.example.yinyang.ui.screens.profile.Profile
import com.example.yinyang.ui.screens.startAppDestination
import com.example.yinyang.ui.theme.YinYangTheme
import com.example.yinyang.viewmodels.ProductViewModel
import com.example.yinyang.viewmodels.ProfileViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    private val profileViewModel: ProfileViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityKiller: () -> Unit = {
            this.finishAndRemoveTask()
        }

        setContent {
            YinYangTheme(
                dynamicColor = false
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
                                        }

                                        selectedItem.value = item
                                    },

                                    colors = NavigationDrawerItemDefaults.colors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                                        selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,

                                        unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                                        unselectedTextColor = MaterialTheme.colorScheme.onBackground,
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
                        ) {
                            composable(HomePageDestination) {
                                HomePage(
                                    navigator = destinationsNavigator,
                                    productViewModel = productViewModel,
                                    profileViewModel = profileViewModel
                                )
                            }
                            composable(ProfileDestination) {
                                Profile(
                                    navigator = destinationsNavigator,
                                    viewModel = profileViewModel,
                                    onLogout = activityKiller
                                )
                            }
                            composable(FavoriteDestination) {
                                Favorite(
                                    navigator = destinationsNavigator,
                                    profileViewModel = profileViewModel
                                )
                            }
                            composable(CartDestination) {
                                Cart(
                                    profileViewModel = profileViewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}