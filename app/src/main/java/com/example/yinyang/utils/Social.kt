package com.example.yinyang.utils

import com.example.yinyang.R

data class Social(
    val icon: Int,
    val title: String,
    val link: String
)

// TODO: change links to Yin Yang's
val socials = listOf(
    Social(
        icon = R.drawable.ic_github,
        title = "GitHub",
        link = "https://github.com/Innosan/YinYang"
    ),
    Social(
        icon = R.drawable.ic_vk,
        title = "VK",
        link = "https://vk.com/inno_san"
    ),
    Social(
        icon = R.drawable.ic_telegram,
        title = "Telegram",
        link = "https://t.me/inno_san"
    )
)
