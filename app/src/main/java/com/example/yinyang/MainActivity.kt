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
import com.example.yinyang.ui.shared.models.navItems
import com.example.yinyang.ui.theme.YinYangTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YinYangTheme(
                darkTheme = true
            ) {
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
                                    scope.launch { drawerState.close() }
                                    selectedItem.value = item
                                    println("Close drawer")
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
                            HomePage(onIconClick = { scope.launch { drawerState.open() } })
                        }
                    }
                )
            }
        }
    }
}