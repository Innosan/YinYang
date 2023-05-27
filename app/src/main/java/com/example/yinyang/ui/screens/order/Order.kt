package com.example.yinyang.ui.screens.order

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.viewmodels.ProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun Order(

) {
    ScreenContainer {
        Text(text = "Order")
    }
}