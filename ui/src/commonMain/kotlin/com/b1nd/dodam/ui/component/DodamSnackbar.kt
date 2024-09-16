package com.b1nd.dodam.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.ui.component.modifier.dropShadow
import com.b1nd.dodam.ui.icons.Close
import com.b1nd.dodam.ui.icons.ColoredCheckmarkCircle
import com.b1nd.dodam.ui.icons.ColoredXMarkCircle

@Composable
fun DodamSnackbar(
    state: SnackbarState,
    text: String,
    showDismissAction: Boolean,
    onDismissRequest: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 12.dp,
                end = 12.dp,
                bottom = 20.dp
            )
            .dropShadow(
                offsetY = 4.dp,
                blur = 4.dp,
                color = DodamTheme.colors.labelNormal.copy(alpha = 0.2f)
            )
            .background(
                color = DodamTheme.colors.backgroundNormal,
                shape = DodamTheme.shapes.extraSmall
            ),
    ) {
        Row(
            modifier = Modifier
                .padding(
                    vertical = 20.dp,
                    horizontal = 12.dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                imageVector = when (state) {
                    SnackbarState.SUCCESS -> ColoredCheckmarkCircle
                    SnackbarState.WARRING ->  ColoredCheckmarkCircle
                    else -> ColoredXMarkCircle
                },
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = text,
                style = DodamTheme.typography.heading2Medium(),
                color = DodamTheme.colors.labelNormal
            )
            if (showDismissAction) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberBounceIndication(),
                            onClick = onDismissRequest
                        ),
                    imageVector = Close,
                    contentDescription = null
                )
            }
        }
    }
}


enum class SnackbarState {
    SUCCESS,
    ERROR,
    WARRING
}