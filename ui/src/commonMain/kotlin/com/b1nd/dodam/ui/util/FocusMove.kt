package com.b1nd.dodam.ui.util

import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager

fun FocusManager.moveFocus(focusDirection: FocusDirection, times: Int) {
    for (i in 0 until times) {
        this.moveFocus(focusDirection)
    }
}