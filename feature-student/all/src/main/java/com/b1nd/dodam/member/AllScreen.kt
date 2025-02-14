package com.b1nd.dodam.member

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ActionIcon
import com.b1nd.dodam.designsystem.component.AvatarSize
import com.b1nd.dodam.designsystem.component.DividerType
import com.b1nd.dodam.designsystem.component.DodamAvatar
import com.b1nd.dodam.designsystem.component.DodamDefaultTopAppBar
import com.b1nd.dodam.designsystem.component.DodamDivider
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.component.modifier.`if`
import com.b1nd.dodam.ui.effect.shimmerEffect
import com.b1nd.dodam.ui.icons.BarChart
import com.b1nd.dodam.ui.icons.ColoredBus
import com.b1nd.dodam.ui.icons.ColoredGroup
import com.b1nd.dodam.ui.icons.ColoredMegaphone
import com.b1nd.dodam.ui.icons.ColoredMusicalNote
import com.b1nd.dodam.ui.icons.ColoredSmailMan
import com.b1nd.dodam.ui.icons.ColoredTent
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllScreen(
    viewModel: AllViewModel = koinViewModel(),
    navigateToSetting: () -> Unit,
    navigateToMyPoint: () -> Unit,
    navigateToAddBus: () -> Unit,
    navigateToOuting: () -> Unit,
    navigateToWakeUpSong: () -> Unit,
    navigateToAddWakeUpSong: () -> Unit,
    role: String
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getMyInfo()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            DodamDefaultTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "전체",
                actionIcons = persistentListOf(
                    ActionIcon(
                        icon = DodamIcons.Gear,
                        onClick = navigateToSetting,
                    ),
                ),
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            if (uiState.isSimmer) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(
                                shimmerEffect(),
                                CircleShape,
                            ),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.padding(top = 9.5.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Box(
                            modifier = Modifier
                                .height(21.dp)
                                .width(150.dp)
                                .background(shimmerEffect(), RoundedCornerShape(100)),
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .height(20.dp)
                                .width(80.dp)
                                .background(shimmerEffect(), RoundedCornerShape(100)),
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    uiState.memberInfo?.let { myInfo ->
                        Box {
                            val borderColor = DodamTheme.colors.lineAlternative
                            DodamAvatar(
                                avatarSize = AvatarSize.ExtraLarge,
                                contentDescription = "프로필 이미지",
                                model = myInfo.profileImage,
                                modifier = Modifier
                                    .`if`(myInfo.profileImage.isNullOrEmpty()) {
                                        border(
                                            width = 1.dp,
                                            color = borderColor,
                                            shape = CircleShape,
                                        )
                                    },
                                contentScale = ContentScale.Crop,
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        val classInfo = myInfo.student
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(
                                text = "환영합니다, " + myInfo.name + "님",
                                style = DodamTheme.typography.headlineBold(),
                                color = DodamTheme.colors.labelNormal,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${classInfo?.grade ?: 0}학년 ${classInfo?.room ?: 0}반 ${classInfo?.number ?: 0}번",
                                style = DodamTheme.typography.labelMedium(),
                                color = DodamTheme.colors.labelAlternative,
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                if (role == "STUDENT") {
                    AllCardView(
                        imageVector = BarChart,
                        text = "내 상벌점 보기",
                        onClick = navigateToMyPoint,
                    )

                    DodamDivider(type = DividerType.Normal)

                    AllCardView(
                        imageVector = ColoredBus,
                        text = "귀가 버스 신청하기",
                        onClick = navigateToAddBus,
                    )

                    AllCardView(
                        imageVector = ColoredTent,
                        text = "외출/외박 확인하기",
                        onClick = navigateToOuting,
                    )

                    AllCardView(
                        imageVector = ColoredMegaphone,
                        text = "기상송 확인하기",
                        onClick = navigateToWakeUpSong,
                    )

                    AllCardView(
                        imageVector = ColoredMusicalNote,
                        text = "기상송 신청하기",
                        onClick = navigateToAddWakeUpSong,
                    )
                }else{
                    AllCardView(
                        imageVector = ColoredSmailMan,
                        text = "내 자녀 관리",
                        onClick = { },
                    )
                }
                AllCardView(
                    imageVector = ColoredGroup,
                    text = "그룹",
                    onClick = { },
                )
            }
        }
    }
}

@Composable
fun AllCardView(imageVector: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(
                indication = rememberBounceIndication(),
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            )
            .padding(
                top = 4.dp,
                bottom = 4.dp,
                start = 8.dp,
                end = 4.dp,
            ),
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = DodamTheme.colors.fillAlternative,
                    shape = RoundedCornerShape(8.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                imageVector = imageVector,
                contentDescription = "image",
                modifier = Modifier.size(20.dp),
            )
        }
        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            color = DodamTheme.colors.labelNormal,
            style = DodamTheme.typography.headlineMedium(),
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = DodamIcons.ChevronRight.value,
            contentDescription = null,
            tint = DodamTheme.colors.labelAssistive,
            modifier = Modifier.size(14.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AllCardViewPreview() {
    DodamTheme {
        AllCardView(
            imageVector = BarChart,
            onClick = {},
            text = "test",
        )
    }
}
