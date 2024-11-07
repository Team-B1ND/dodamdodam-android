package com.b1nd.dodam.setting

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.AvatarSize
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.DividerType
import com.b1nd.dodam.designsystem.component.DodamAvatar
import com.b1nd.dodam.designsystem.component.DodamButtonDialog
import com.b1nd.dodam.designsystem.component.DodamDialog
import com.b1nd.dodam.designsystem.component.DodamDivider
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.component.modifier.`if`
import com.b1nd.dodam.ui.effect.shimmerEffect
import com.b1nd.dodam.ui.icons.DefaultProfile
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@ExperimentalMaterial3Api
@Composable
internal fun SettingScreen(viewModel: SettingViewModel = koinViewModel(), versionInfo: String = "3.2.0", popBackStack: () -> Unit, logout: () -> Unit, navigationToEditMemberInfo: (profileImage: String?, name: String, email: String, phone: String) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showEasterEggDialog by remember { mutableStateOf(false) }
    var showDeactivationDialog by remember { mutableStateOf(false) }

    val uriHandler = LocalUriHandler.current

    var count by remember { mutableIntStateOf(0) }

    LaunchedEffect(count) {
        if (count == 10) {
            showEasterEggDialog = true
        }
    }

    if (showEasterEggDialog) {
        Dialog(
            onDismissRequest = { showEasterEggDialog = false },
        ) {
            DodamButtonDialog(
                confirmButton = { uriHandler.openUri("https://bit.ly/b1ndquiz") },
                confirmButtonText = "확인",
                confirmButtonRole = ButtonRole.Primary,
                dismissButton = { showEasterEggDialog = false },
                dismissButtonText = "취소",
                dismissButtonRole = ButtonRole.Assistive,
                title = "이스터에그를 찾았어요!",
                body = "아래 링크로 이동하시겠어요?",
            )
        }
    }

    if (showLogoutDialog) {
        Dialog(
            onDismissRequest = { showLogoutDialog = false },
        ) {
            DodamButtonDialog(
                confirmButton = {
                    viewModel.logout()
                    logout()
                },
                confirmButtonText = "로그아웃",
                confirmButtonRole = ButtonRole.Negative,
                dismissButton = { showLogoutDialog = false },
                dismissButtonText = "취소",
                dismissButtonRole = ButtonRole.Assistive,
                title = "정말 로그아웃하시겠어요?",
            )
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
        ) {
            DodamDialog(
                confirmButton = { showDialog = false },
                text = "확인",
                title = "아직 준비 중인 기능이에요!",
                body = "정보를 수정하시려면 도담도담 웹사이트를 이용해 주세요.",
            )
        }
    }

    if (showDeactivationDialog) {
        Dialog(
            onDismissRequest = { showDeactivationDialog = false },
        ) {
            DodamButtonDialog(
                confirmButton = {
                    viewModel.deactivate()
                    logout()
                },
                confirmButtonText = "회원탈퇴",
                confirmButtonRole = ButtonRole.Negative,
                dismissButton = { showDeactivationDialog = false },
                dismissButtonText = "취소",
                dismissButtonRole = ButtonRole.Assistive,
                title = "정말 탈퇴하시겠어요?",
            )
        }
    }

    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "설정",
                onBackClick = popBackStack,
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 12.dp, end = 16.dp, start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberBounceIndication(),
                        onClick = { navigationToEditMemberInfo(
                            uiState.profile,
                            uiState.name,
                            uiState.email,
                            uiState.phone
                        ) } ,
                    ),
            ) {
                if (uiState.isLoading) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(
                                    brush = shimmerEffect(),
                                    shape = CircleShape,
                                ),
                        )

                        Column {
                            Box(
                                modifier = Modifier
                                    .size(50.dp, 20.dp)
                                    .background(
                                        shimmerEffect(),
                                        RoundedCornerShape(4.dp),
                                    ),
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Box(
                                modifier = Modifier
                                    .size(100.dp, 14.dp)
                                    .background(
                                        shimmerEffect(),
                                        RoundedCornerShape(4.dp),
                                    ),
                            )
                        }
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        val borderColor = DodamTheme.colors.lineAlternative
                        DodamAvatar(
                            avatarSize = AvatarSize.ExtraLarge,
                            contentDescription = "프로필 이미지",
                            model = uiState.profile ,
                            modifier = Modifier
                                .`if`(uiState.profile.isNullOrEmpty()) {
                                    border(
                                        width = 1.dp,
                                        color = borderColor,
                                        shape = CircleShape
                                    )
                                },
                            contentScale = ContentScale.Crop
                        )

                        Column {
                            Text(
                                text = uiState.name,
                                style = DodamTheme.typography.headlineBold(),
                                color = DodamTheme.colors.labelNormal,
                            )
                            Text(
                                text = "정보 수정",
                                style = DodamTheme.typography.labelMedium(),
                                color = DodamTheme.colors.labelAlternative,
                                textDecoration = TextDecoration.Underline,
                            )
                        }
                    }
                }
            }

            DodamDivider(
                type = DividerType.Normal,
            )

            SettingCard(
                onClick = {
                    uriHandler.openUri("https://dodam.b1nd.com/detailed-information/service-policy")
                },
                text = "서비스 운영 정책",
            )

            SettingCard(
                onClick = {
                    uriHandler.openUri("https://dodam.b1nd.com/detailed-information/personal-information")
                },
                text = "개인정보 처리 방침",
            )

            SettingCard(
                text = "버전 정보",
                description = versionInfo,
                onClick = {
                    count++
                },
            )

            DodamDivider(
                type = DividerType.Normal,
            )

            SettingCard(
                onClick = { showLogoutDialog = true },
                text = "로그아웃",
            )

            SettingCard(
                onClick = { showDeactivationDialog = true },
                text = "회원탈퇴",
                textColor = DodamTheme.colors.statusNegative,
            )
        }
    }
}

@Composable
private fun SettingCard(modifier: Modifier = Modifier, text: String, textColor: Color = DodamTheme.colors.labelNormal, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBounceIndication(),
                onClick = onClick,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 3.5.dp),
            text = text,
            color = textColor,
            style = DodamTheme.typography.headlineMedium(),
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

@Composable
private fun SettingCard(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = DodamTheme.colors.labelNormal,
    description: String,
    descriptionColor: Color = DodamTheme.colors.labelAssistive,
    onClick: (() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val indication = rememberBounceIndication()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .`if`(onClick != null) {
                clickable(
                    interactionSource = interactionSource,
                    indication = indication,
                    onClick = onClick!!,
                )
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 3.5.dp),
            text = text,
            color = textColor,
            style = DodamTheme.typography.headlineMedium(),
        )

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = description,
            color = descriptionColor,
            style = DodamTheme.typography.headlineRegular(),
        )
    }
}
