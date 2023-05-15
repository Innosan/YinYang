package com.example.yinyang.utils

import com.example.yinyang.R

enum class RatingTitle(val value: Int, val title: Int) {
    NEWBIE(1, R.string.newbie_rating),
    OFTEN_USES(2, R.string.often_uses_rating),
    MASTER(3, R.string.master_rating)
}

fun getRatingTitle(ratingValue: Int) : RatingTitle {
    return when (ratingValue) {
        1 -> RatingTitle.NEWBIE
        2 -> RatingTitle.OFTEN_USES
        3 -> RatingTitle.MASTER
        else -> RatingTitle.NEWBIE
    }
}