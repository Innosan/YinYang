package com.example.yinyang.utils

import com.example.yinyang.R

enum class ButtonType(val buttonText: Int, val lowPriority: Boolean) {
    ADD(R.string.add_button, false),
    CLOSE(R.string.close_button, true),
    UPDATE(R.string.update_button, false),
    CONFIRM(R.string.yes_button, false),
    REJECT(R.string.no_button, true),
}