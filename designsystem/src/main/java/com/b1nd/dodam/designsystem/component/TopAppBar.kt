package com.b1nd.dodam.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.animation.NoInteractionSource

@Composable
fun DodamTopAppBar(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    title: String,
    icon: ImageVector? = null,
    onIconClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(containerColor)
            .statusBarsPadding()
            .height(48.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            color = contentColor,
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
                tint = contentColor,
            )
        }
    }
}

@Composable
fun DodamTopAppBar(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    titleIcon: @Composable () -> Unit,
    icon: ImageVector? = null,
    onIconClick: (() -> Unit)? = null,
    isCollapsed: Boolean = false,
) {
    Box {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(58.dp)
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
                    tint = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
        if (isCollapsed) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                color = MaterialTheme.colorScheme.secondary,
                thickness = 1.dp,
            )
        }
    }
}
