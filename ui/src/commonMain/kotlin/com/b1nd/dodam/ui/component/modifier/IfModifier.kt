package com.b1nd.dodam.ui.component.modifier

import androidx.compose.ui.Modifier

fun Modifier.`if`(enabled: Boolean, block: Modifier.() -> Modifier) = if (enabled) block() else this
