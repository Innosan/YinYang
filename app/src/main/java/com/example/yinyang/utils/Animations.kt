package com.example.yinyang.utils

import androidx.compose.animation.*


fun setHorizontalEnter(): EnterTransition {

    return slideInHorizontally(
        initialOffsetX = { 160 }
    ) + fadeIn(
        initialAlpha = 0f
    ) + scaleIn(
        initialScale = 0f
    )
}

fun setVerticalEnter(): EnterTransition {

    return slideInVertically(
        initialOffsetY = { -160 }
    ) + fadeIn(
        initialAlpha = 0f
    ) + scaleIn(
        initialScale = 0f
    )
}

fun setHorizontalExit(): ExitTransition {

    return slideOutHorizontally(
        targetOffsetX = { 160 }
    ) + fadeOut(
        targetAlpha = 0f
    ) + scaleOut(
        targetScale = 0f
    )
}

fun setVerticalExit(): ExitTransition {

    return slideOutVertically(
        targetOffsetY = { -160 }
    ) + fadeOut(
        targetAlpha = 0f
    ) + scaleOut(
        targetScale = 0f
    )
}