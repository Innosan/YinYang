package com.example.yinyang.ui.shared.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.yinyang.R
import com.example.yinyang.models.DeliveryAddress
import com.example.yinyang.network.client
import com.example.yinyang.repository.UserRepository
import com.example.yinyang.utils.SwipeBackground
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressList(items: MutableList<DeliveryAddress>, userRepository: UserRepository) {
    Column {
        Text(text = "Ваши адреса")

        items.forEach {address ->
            val currentItem by rememberUpdatedState(newValue = address)
            val dismissState = rememberDismissState(
                confirmValueChange = {
                    items.remove(currentItem)
                    true
                }
            )
            SwipeToDismiss(
                state = dismissState,
                background = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> Color.Transparent
                            DismissValue.DismissedToEnd -> Color.Blue
                            DismissValue.DismissedToStart -> Color.Red
                        }
                    )
                    val alignment = Alignment.CenterEnd
                    val icon = Icons.Default.Delete

                    val scale by animateFloatAsState(
                        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                    )

                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = Dp(20f)),
                        contentAlignment = alignment
                    ) {
                        Icon(
                            icon,
                            contentDescription = "Delete Icon",
                            modifier = Modifier.scale(scale)
                        )
                    }
                },
                dismissContent = {
                    Row {
                        Text(text = address.address)
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_settings),

                                contentDescription = "Edit address"
                            )
                        }
                    }
                }
            )
        }
        
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Add")
        }
    }
}