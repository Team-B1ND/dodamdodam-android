package com.b1nd.dodam.parent.children_manage

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.AvatarSize
import com.b1nd.dodam.designsystem.component.DodamAvatar
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.ui.component.modifier.`if`


@Composable
internal fun ChildrenManageScreen(
    popBackStack: () -> Unit,
    changeBottomNavVisible: (visible: Boolean) -> Unit
) {

    LaunchedEffect(true) {
        changeBottomNavVisible(false)
    }

    DisposableEffect(true) {
        onDispose {
            changeBottomNavVisible(true)
        }
    }
    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "학생코드로\n" +
                        "자녀를 등록해주세요",
                onBackClick = popBackStack,
                type = TopAppBarType.Large,
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {

        }
    }
}


@Composable
fun ChildrenCard(
    modifier: Modifier = Modifier,
    profile: String? = null,
    name: String,
    relation: String
) {
    Box(
        modifier = modifier
            .background(
                color = DodamTheme.colors.backgroundNormal,
                shape = RoundedCornerShape(12.dp)
            )
            .fillMaxWidth()
            .height(144.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            DodamAvatar(
                avatarSize = AvatarSize.ExtraLarge,
                contentDescription = "프로필 이미지",
                model = profile,
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = name,
                    color = DodamTheme.colors.labelStrong,
                    style = DodamTheme.typography.headlineBold()
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "학생과의 관계: ",
                        color = DodamTheme.colors.labelAssistive,
                        style = DodamTheme.typography.labelRegular()
                    )
                    Text(
                        text = relation,
                        color = DodamTheme.colors.labelAssistive,
                        style = DodamTheme.typography.labelRegular(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

    }
}

@Preview
@Composable
private fun Preview() {
    ChildrenCard(
        name = "한준혁",
        relation = "본좌",
        profile = "https://dodamdodam-storage.s3.ap-northeast-2.amazonaws.com/dodamdodam-storage/431b87b3-ff1b-4079-b070-d4a6b44665d8IMG_0007"
    )
}