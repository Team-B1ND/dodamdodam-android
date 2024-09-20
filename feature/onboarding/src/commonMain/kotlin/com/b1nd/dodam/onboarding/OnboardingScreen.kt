package com.b1nd.dodam.onboarding

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamModalBottomSheet
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import dodamdodam_android.feature.onboarding.generated.resources.Res
import dodamdodam_android.feature.onboarding.generated.resources.onboarding_background
import dodamdodam_android.feature.onboarding.generated.resources.onboarding_logo
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(onRegisterClick: () -> Unit, onLoginClick: () -> Unit) {
    val uriHandler = LocalUriHandler.current

    var showBottomSheet by remember { mutableStateOf(false) }
    var isTermsChecked by remember { mutableStateOf(false) }
    var isPrivacyChecked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            bitmap = imageResource(Res.drawable.onboarding_background),
            contentDescription = "onboarding background",
            contentScale = ContentScale.Crop,
        )

        val staticBlackColor = DodamTheme.colors.staticBlack
        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                drawRect(
                    color = staticBlackColor,
                    alpha = 0.5f,
                )
            },
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Image(
                imageVector = vectorResource(Res.drawable.onboarding_logo),
                contentDescription = "dodam logo",
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "어린아이가 탈 없이 잘 놀며 자라는 모양",
                color = DodamTheme.colors.staticWhite,
                style = DodamTheme.typography.body1Medium(),
            )

            Spacer(modifier = Modifier.weight(1f))

            DodamButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    showBottomSheet = true
                },
                text = "시작하기",
                buttonSize = ButtonSize.Large,
                buttonRole = ButtonRole.Primary,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text(
                    text = "이미 계정이 있나요?",
                    color = DodamTheme.colors.staticWhite,
                    style = DodamTheme.typography.labelMedium(),
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onLoginClick,
                    ),
                    text = "로그인",
                    color = DodamTheme.colors.staticWhite,
                    style = DodamTheme.typography.labelMedium().copy(
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = TextDecoration.Underline,
                    ),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if (showBottomSheet) {
        DodamModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            title = {
                Text(
                    text = "회원가입을 하기 위해 동의가 필요해요",
                    style = DodamTheme.typography.heading2Bold(),
                    color = DodamTheme.colors.labelNormal,
                )
                Spacer(Modifier.height(4.dp))
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            DodamTheme.colors.backgroundNormal,
                            RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                        ),
                ) {
                    Text(
                        text = "도담도담에 가입하기 위해 서비스 이용약관과 개인정보처리방침 동의가 필요해요",
                        style = DodamTheme.typography.body1Medium(),
                        color = DodamTheme.colors.labelNormal,
                    )
                    Spacer(Modifier.height(24.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberBounceIndication(showBackground = false),
                                onClick = {
                                    if (isPrivacyChecked && isTermsChecked) {
                                        isPrivacyChecked = false
                                        isTermsChecked = false
                                    } else {
                                        isPrivacyChecked = true
                                        isTermsChecked = true
                                    }
                                },
                            )
                            .background(
                                color = DodamTheme.colors.fillNormal,
                                shape = DodamTheme.shapes.medium,
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Spacer(Modifier.width(28.dp))
                        Image(
                            modifier = Modifier.size(20.dp),
                            imageVector = DodamIcons.Checkmark.value,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(
                                if (isPrivacyChecked && isTermsChecked) {
                                    DodamTheme.colors.primaryNormal
                                } else {
                                    DodamTheme.colors.labelAlternative.copy(
                                        alpha = 0.5f,
                                    )
                                },
                            ),
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "필수 항목 모두 체크하기",
                            color = DodamTheme.colors.labelNeutral,
                            style = DodamTheme.typography.body1Bold(),
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    TermsButton(
                        text = "[필수] 개인정보 수집 및 이용에 대한 안내",
                        checked = isPrivacyChecked,
                        onClick = {
                            isPrivacyChecked = !isPrivacyChecked
                        },
                        onClickLink = {
                            uriHandler.openUri("https://dodam.b1nd.com/detailed-information/service-policy")
                        },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TermsButton(
                        text = "[필수] 서비스 이용약관",
                        checked = isTermsChecked,
                        onClick = {
                            isTermsChecked = !isTermsChecked
                        },
                        onClickLink = {
                            uriHandler.openUri("https://dodam.b1nd.com/detailed-information/personal-information")
                        },
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    DodamButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onRegisterClick,
                        enabled = isPrivacyChecked,
                        text = "다음",
                        buttonRole = ButtonRole.Primary,
                        buttonSize = ButtonSize.Large,
                    )
                }
            },
        )
    }
}

@Composable
private fun TermsButton(modifier: Modifier = Modifier, text: String, checked: Boolean, onClick: () -> Unit, onClickLink: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberBounceIndication(),
                    onClick = onClick,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(4.dp))
            Image(
                modifier = Modifier.size(16.dp),
                imageVector = DodamIcons.Checkmark.value,
                contentDescription = null,
                colorFilter = ColorFilter.tint(if (checked) DodamTheme.colors.primaryNormal else DodamTheme.colors.labelAssistive.copy(0.5f)),
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                modifier = Modifier.padding(
                    vertical = 8.dp,
                ),
                text = text,
                style = DodamTheme.typography.labelRegular(),
                color = DodamTheme.colors.labelAssistive,
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberBounceIndication(showBackground = false),
                    onClick = onClickLink,
                ),
            text = "보기",
            style = DodamTheme.typography.labelBold().copy(
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline,
            ),
            color = DodamTheme.colors.labelAssistive,
        )
    }
}
