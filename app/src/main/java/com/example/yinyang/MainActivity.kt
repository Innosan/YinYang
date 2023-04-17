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
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yinyang.ui.screens.NavGraphs
import com.example.yinyang.ui.screens.appCurrentDestinationAsState
import com.example.yinyang.ui.screens.destinations.Destination
import com.example.yinyang.ui.screens.startAppDestination
import com.example.yinyang.ui.shared.models.navItems
import com.example.yinyang.ui.theme.YinYangTheme
import com.example.yinyang.ui.utils.client
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.gotrue
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

                val userState = client.gotrue.sessionStatus.collectAsState();

                val isGesturesEnabled = userState.value is SessionStatus.Authenticated

                val currentDestination: Destination = navController.appCurrentDestinationAsState().value
                    ?: NavGraphs.root.startAppDestination
                
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                val selectedItem = remember { mutableStateOf(navItems[0]) }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    gesturesEnabled = isGesturesEnabled,
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
                                selected = currentDestination == item.destination,

                                onClick = {
                                    scope.launch { drawerState.close() }
                                    navController.navigate(item.destination) {
                                        launchSingleTop = true
                                    }
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
                            DestinationsNavHost(navGraph = NavGraphs.root, navController = navController)
                        }
                    }
                )
            }
        }
    }
}