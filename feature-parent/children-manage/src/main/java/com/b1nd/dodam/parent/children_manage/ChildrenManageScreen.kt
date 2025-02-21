package com.b1nd.dodam.parent.children_manage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.zIndex
import com.b1nd.dodam.dds.animation.bounceClick
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.AvatarSize
import com.b1nd.dodam.designsystem.component.DodamAvatar
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.designsystem.foundation.DodamIcons


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
                title = "학생코드로\n자녀를 등록해주세요",
                onBackClick = popBackStack,
                type = TopAppBarType.Large,
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral
    ) { it ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val children = listOf(
                "한" to "ㅁㄴ",
                "이" to "ㄴㅇ",
                "박" to "ㅇㄹ",
                "김" to "ㄹㅂ",
                "최" to "ㅂㅁ",
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(children.chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowItems.fastForEach { (name, relation) ->
                            ChildrenCard(
                                name = name,
                                relation = relation,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (rowItems.size == 1 && rowItems.first() == children.last()) {
                            AddChildrenButton(
                                modifier = Modifier.weight(1f),
                                onClick = {}
                            )
                        }
                    }
                }
                if (children.size % 2 != 1) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            AddChildrenButton(modifier = Modifier.weight(1f)) { }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
                item {
                    Spacer(Modifier.height(150.dp))
                }
            }
            DodamButton(
                onClick = {},
                text = "완료",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .padding(horizontal = 16.dp)
            )
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

@Composable
fun AddChildrenButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .background(
                color = DodamTheme.colors.backgroundNormal,
                shape = RoundedCornerShape(12.dp)
            )
            .fillMaxWidth()
            .height(144.dp)
            .bounceClick(
                onClick,
                interactionColor = DodamTheme.colors.lineNormal
            )
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
        ){
            Icon(
                imageVector = DodamIcons.Plus.value,
                contentDescription = null,
                tint = DodamTheme.colors.labelAssistive,
                modifier = Modifier.size(24.dp),
            )
            Spacer(Modifier.height(8.dp))
            Text(text = "자녀 추가 등록", color = DodamTheme.colors.labelAssistive, style = DodamTheme.typography.labelBold())
        }
    }
}

@Preview
@Composable
private fun Preview() {

    Column {
        ChildrenCard(
            name = "한준혁",
            relation = "본좌",
            profile = "https://dodamdodam-storage.s3.ap-northeast-2.amazonaws.com/dodamdodam-storage/431b87b3-ff1b-4079-b070-d4a6b44665d8IMG_0007"
        )
        Spacer(Modifier.height(16.dp))
        AddChildrenButton(
            onClick = {}
        )
    }

}