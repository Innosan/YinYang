package com.example.yinyang.ui.screens.orders

import androidx.compose.runtime.Composable
import com.example.yinyang.R
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.ui.shared.components.SectionHeader
import com.example.yinyang.viewmodels.ProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun Orders(
    profileViewModel: ProfileViewModel
) {
    ScreenContainer {
        SectionHeader(iconId = R.drawable.ic_orders, title = R.string.order_screen)
    }
}