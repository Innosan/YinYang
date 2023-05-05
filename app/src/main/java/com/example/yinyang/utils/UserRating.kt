package com.example.yinyang.utils

enum class RatingTitle(val value: Int, val title: String) {
    NEWBIE(1, "Новичок"),
    OFTEN_USES(2, "Частый гость"),
    MASTER(3, "Уважаемый покупатель")
}

fun getRatingTitle(ratingValue: Int) : RatingTitle {
    return when (ratingValue) {
        1 -> RatingTitle.NEWBIE
        2 -> RatingTitle.OFTEN_USES
        3 -> RatingTitle.MASTER
        else -> RatingTitle.NEWBIE
    }
}