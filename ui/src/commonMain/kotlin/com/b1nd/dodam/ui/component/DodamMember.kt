package com.b1nd.dodam.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.AvatarSize
import com.b1nd.dodam.designsystem.component.DodamAvatar
import com.b1nd.dodam.ui.effect.shimmerEffect

@Composable
fun DodamMember(modifier: Modifier = Modifier, icon: Any?, name: String, content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DodamAvatar(
            avatarSize = AvatarSize.Large,
            model = icon,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = name,
            style = DodamTheme.typography.headlineMedium(),
            color = DodamTheme.colors.labelNormal,
        )
        Spacer(modifier = Modifier.weight(1f))
        content()
    }
}

@Composable
fun DodamMemberLoadingCard(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DodamAvatar(
            modifier = Modifier.padding(vertical = 4.dp),
            avatarSize = AvatarSize.Large,
            model = null,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(27.dp)
                .background(
                    brush = shimmerEffect(),
                    shape = DodamTheme.shapes.extraLarge,
                ),
        )
    }
}
