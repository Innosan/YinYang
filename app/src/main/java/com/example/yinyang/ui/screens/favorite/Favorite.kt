package com.example.yinyang.ui.screens.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yinyang.R
import com.example.yinyang.ui.screens.favorite.components.FavoriteCard
import com.example.yinyang.ui.shared.components.containers.ScreenContainer
import com.example.yinyang.ui.shared.components.containers.SectionHeader
import com.example.yinyang.ui.shared.styles.buttonTextStyle
import com.example.yinyang.utils.Screen
import com.example.yinyang.viewmodels.ProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun Favorite(
    navigator: DestinationsNavigator,
    profileViewModel: ProfileViewModel
) {
    val favorite = profileViewModel.profile.value.userFavorite?.value

    ScreenContainer {
        SectionHeader(iconId = R.drawable.ic_favorite, title = R.string.favorite_screen)

        if (favorite != null) {
            LazyColumn(
                modifier = Modifier.height(580.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(
                    favorite,

                    key = {favorite -> favorite.product_id.id}
                ) { product ->
                    FavoriteCard(
                        product = product.product_id,
                        profileViewModel
                    )
                }
            }
        }

        Button(
            onClick = {
                navigator.navigate(Screen.Home.destination)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp)
            ,
        ) {
            Text(
                text = stringResource(id = R.string.go_to_menu_button),
                style = buttonTextStyle
            )
        }
    }
}