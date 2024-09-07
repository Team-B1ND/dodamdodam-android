package com.b1nd.dodam.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.foundation.DodamIcons

@Composable
fun DodamContainer(
    modifier: Modifier = Modifier,
    icon: DodamIcons,
    title: String,
    showNextButton: Boolean = false,
    onNextClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .background(
                color = DodamTheme.colors.backgroundNormal,
                shape = DodamTheme.shapes.large,
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (showNextButton) {
                        Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberBounceIndication(),
                            onClick = onNextClick ?: {},
                        )
                    } else {
                        Modifier
                    },
                )
                .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .background(
                        color = DodamTheme.colors.primaryNormal.copy(alpha = 0.65f),
                        shape = RoundedCornerShape(100),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(16.dp),
                    imageVector = icon.value,
                    contentDescription = "icon",
                    tint = DodamTheme.colors.staticWhite,
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = title,
                color = DodamTheme.colors.labelStrong,
                style = DodamTheme.typography.headlineBold(),
            )

            Spacer(modifier = Modifier.weight(1f))

            if (showNextButton) {
                Icon(
                    modifier = Modifier
                        .size(16.dp),
                    imageVector = DodamIcons.ChevronRight.value,
                    contentDescription = "next",
                    tint = DodamTheme.colors.lineNormal,
                )
            }
        }
        Box(
            modifier = Modifier.padding(horizontal = 6.dp),
        ) {
            content()
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}
