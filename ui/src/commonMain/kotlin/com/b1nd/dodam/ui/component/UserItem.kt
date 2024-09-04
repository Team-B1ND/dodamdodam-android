package com.b1nd.dodam.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.ui.icons.DefaultUser

@Composable
fun UserItem(modifier: Modifier = Modifier, profileImage: String = "", userName: String, action: @Composable () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp)
            .background(color = DodamTheme.colors.staticWhite),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileImage(
            profileImage = profileImage,
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = userName,
            style = DodamTheme.typography.headlineMedium(),
            color = DodamTheme.colors.labelNormal,
        )
        Spacer(modifier = Modifier.weight(1f))
        action()
    }
}

@Composable
fun ProfileImage(modifier: Modifier = Modifier, profileImage: String) {
    if (profileImage.isEmpty()) {
        Box(
            modifier = modifier
                .size(40.dp)
                .background(
                    color = DodamTheme.colors.fillNormal,
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier.size(15.dp),
                contentDescription = "",
                bitmap = DefaultUser,
                contentScale = ContentScale.Crop,
            )
        }
    } else {
        AsyncImage(
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape),
            model = profileImage,
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
    }
}
