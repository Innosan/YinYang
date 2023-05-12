package com.example.yinyang.utils

import androidx.compose.ui.window.PopupProperties

val CustomPopupProperties = PopupProperties(
    focusable = true,
    dismissOnBackPress = true,
    dismissOnClickOutside = false,
    excludeFromSystemGesture = true,
    clippingEnabled = true,
)