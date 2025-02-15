package com.b1nd.dodam.all

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
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
import com.b1nd.dodam.ui.icons.ColoredPencil
import com.b1nd.dodam.ui.icons.ColoredTrophy
import com.b1nd.dodam.ui.icons.Tent
import kotlinx.collections.immutable.persistentListOf
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun AllScreen(
    viewModel: AllViewModel = koinViewModel(),
    navigateToSetting: () -> Unit,
    navigateToOut: () -> Unit,
    navigateToNightStudy: () -> Unit,
    navigateToPoint: () -> Unit,
) {
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(true) {
        viewModel.loadProfile()
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
                        enabled = true,
                    ),
                ),
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 12.dp,
                            bottom = 8.dp,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (uiState.isLoading) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(
                                    brush = shimmerEffect(),
                                    shape = CircleShape,
                                ),
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .width(130.dp)
                                .height(20.dp)
                                .background(
                                    brush = shimmerEffect(),
                                    shape = DodamTheme.shapes.medium,
                                ),
                        )
                    } else {
                        val borderColor = DodamTheme.colors.lineAlternative
                        DodamAvatar(
                            avatarSize = AvatarSize.ExtraLarge,
                            contentDescription = "프로필 이미지",
                            model = uiState.memberInfo.profileImage,
                            modifier = Modifier
                                .`if`(uiState.memberInfo.profileImage.isNullOrEmpty()) {
                                    border(
                                        width = 1.dp,
                                        color = borderColor,
                                        shape = CircleShape,
                                    )
                                },
                            contentScale = ContentScale.Crop,
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "환영합니다, " + uiState.memberInfo.name + "님",
                            style = DodamTheme.typography.headlineBold(),
                            color = DodamTheme.colors.labelNormal,
                        )
                    }
                }
            }

            item {
                DodamDivider(type = DividerType.Normal)
            }

            item {
                AllCard(
                    image = Tent,
                    text = "외출/외박 승인하기",
                    onClick = navigateToOut,
                )
            }

            item {
                AllCard(
                    image = ColoredPencil,
                    text = "심야 자습 승인하기",
                    onClick = navigateToNightStudy,
                )
            }

            item {
                AllCard(
                    image = ColoredTrophy,
                    text = "상벌점 부여하기",
                    onClick = navigateToPoint,
                )
            }
        }
    }
}

@Composable
private fun AllCard(image: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBounceIndication(),
                onClick = onClick,
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = DodamTheme.colors.fillAlternative,
                    shape = DodamTheme.shapes.extraSmall,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier
                    .padding(6.dp)
                    .size(20.dp),
                imageVector = image,
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = DodamTheme.typography.headlineMedium(),
            color = DodamTheme.colors.labelNormal,
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.size(14.dp),
            imageVector = DodamIcons.ChevronRight.value,
            contentDescription = null,
            colorFilter = ColorFilter.tint(DodamTheme.colors.labelAssistive),
        )
    }
}
