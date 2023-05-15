package com.example.yinyang.ui.screens.favorite

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.example.yinyang.network.client
import com.example.yinyang.repository.FavoriteRepository
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch

val favRepo: FavoriteRepository = FavoriteRepository(client)

@Destination
@Composable
fun Favorite(
    userId: Int?,
) {
    val coroutineScope = rememberCoroutineScope()
    
    val items = favRepo.fav.value

    println(items)
    ScreenContainer {
        Text(text = "Favorite")
        Text(text = userId.toString())

        Button(onClick = { 
            coroutineScope.launch {
                if (userId != null) {
                    favRepo.getFavorites(userId)
                }
            }
        }) {
            Text(text = "Load fav")
        }
    }
}