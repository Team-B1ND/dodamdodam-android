package com.b1nd.dodam.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.animation.NoInteractionSource

@Composable
fun DodamTopAppBar(modifier: Modifier = Modifier, containerColor: Color = MaterialTheme.colorScheme.background, title: String, icon: ImageVector? = null, onIconClick: (() -> Unit)? = null) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(containerColor)
            .height(48.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            color = contentColorFor(containerColor),
            style = MaterialTheme.typography.headlineSmall,
        )

        Spacer(modifier = Modifier.weight(1f))

        icon?.let {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onIconClick!!),
                imageVector = icon,
                contentDescription = null,
                tint = contentColorFor(containerColor),
            )
        }
    }
}

@Composable
fun DodamTopAppBar(modifier: Modifier = Modifier, containerColor: Color = MaterialTheme.colorScheme.background, titleIcon: @Composable () -> Unit, icon: ImageVector? = null, onIconClick: (() -> Unit)? = null) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(containerColor)
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        titleIcon()

        Spacer(modifier = Modifier.weight(1f))

        icon?.let {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        interactionSource = NoInteractionSource(),
                        indication = null,
                        onClick = onIconClick!!,
                    ),
                imageVector = icon,
                contentDescription = null,
                tint = contentColorFor(containerColor),
            )
        }
    }
}
