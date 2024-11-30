package com.b1nd.dodam.ui.component.modifier

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.`if`(enabled: Boolean, block: @Composable Modifier.() -> Modifier) = composed { if (enabled) block() else this }
