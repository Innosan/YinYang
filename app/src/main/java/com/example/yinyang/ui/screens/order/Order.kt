package com.example.yinyang.ui.screens.order

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.yinyang.R
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.viewmodels.ProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun Order(
    profileViewModel: ProfileViewModel
) {
    ScreenContainer {
        profileViewModel.profile.value.userInfo?.value?.firstName?.let { Text(text = it) }

        Button(onClick = {
            profileViewModel.getUserId()?.let {
                profileViewModel.profile.value.userCart?.value?.let { it1 ->
                    profileViewModel.orderManager.createNewOrder(
                        it,
                        it1,
                        1200
                    )
                }
            }
        }) {
            Text(text = stringResource(id = R.string.order_screen))
        }
    }
}