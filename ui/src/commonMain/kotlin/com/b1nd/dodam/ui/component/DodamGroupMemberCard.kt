package com.b1nd.dodam.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.AvatarSize
import com.b1nd.dodam.designsystem.component.DodamAvatar

@Composable
fun DodamGroupMemberCard(
    modifier: Modifier = Modifier,
    image: String?,
    name: String,
    action: @Composable () -> Unit = {},
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .padding(start = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DodamAvatar(
            avatarSize = AvatarSize.Small,
            model = image,
        )
        Text(
            text = name,
            style = DodamTheme.typography.body1Medium(),
            color = DodamTheme.colors.labelNormal
        )
        Spacer(modifier = Modifier.weight(1f))
        action()
    }
}