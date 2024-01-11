package com.b1nd.dodam.designsystem.animation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.bounceClick(interactionSource: InteractionSource) = composed {
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        if (isPressed) {
            0.97f
        } else {
            1f
        },
    )
    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
}
