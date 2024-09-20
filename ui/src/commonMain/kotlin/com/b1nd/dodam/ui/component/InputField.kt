package com.b1nd.dodam.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: @Composable () -> Unit,
    content: @Composable () -> Unit,
    paddingValues: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 12.dp),
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(DodamTheme.shapes.extraLarge)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBounceIndication(),
                onClick = onClick,
            )
            .padding(paddingValues),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        text()
        Spacer(modifier = Modifier.weight(1f))
        content()
    }
}
