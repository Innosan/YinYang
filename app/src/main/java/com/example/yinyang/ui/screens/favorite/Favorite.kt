package com.example.yinyang.ui.screens.favorite

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yinyang.ui.shared.components.ProductCard
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.viewmodels.ProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun Favorite(
    profileViewModel: ProfileViewModel
) {
    val favorite = profileViewModel.profile.value.userFavorite?.value

    ScreenContainer {
        Text(text = "Favorite")

        if (favorite != null) {
            LazyColumn(
                modifier = Modifier.height(800.dp),
            ) {
                items(
                    favorite,

                    key = {favorite -> favorite.product_id.id}
                ) { product ->
                    ProductCard(
                        product = product.product_id,
                        profileViewModel
                    )
                }
            }
        }
    }
}