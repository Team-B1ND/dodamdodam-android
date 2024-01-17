package com.b1nd.dodam.onboarding

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.animation.NoInteractionSource
import com.b1nd.dodam.designsystem.component.DodamFullWidthButton
import com.b1nd.dodam.designsystem.theme.Black
import com.b1nd.dodam.designsystem.theme.Blue500
import com.b1nd.dodam.designsystem.theme.CheckCircleIcon
import com.b1nd.dodam.designsystem.theme.CheckIcon
import com.b1nd.dodam.designsystem.theme.Gray300
import com.b1nd.dodam.designsystem.theme.Gray800
import com.b1nd.dodam.designsystem.theme.Gray900
import com.b1nd.dodam.designsystem.theme.RightArrowIcon
import com.b1nd.dodam.designsystem.theme.White
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties

@Composable
internal fun OnboardingScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var isTermsChecked by remember { mutableStateOf(false) }
    var isPrivacyChecked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.onboarding_background),
            contentDescription = "onboarding background",
            contentScale = ContentScale.Crop,
        )

        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                drawRect(
                    color = Black,
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
                imageVector = ImageVector.vectorResource(id = R.drawable.onboarding_logo),
                contentDescription = "dodam logo",
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "[부사] 어린아이가 탈 없이 잘 놀며 자라는 모양.",
                color = White,
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.weight(1f))

            DodamFullWidthButton(
                onClick = {
                    showBottomSheet = true
                },
                text = "시작하기",
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text(
                    text = "이미 계정이 있나요?",
                    color = White,
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    modifier = Modifier.clickable(
                        interactionSource = NoInteractionSource(),
                        indication = null,
                        onClick = onLoginClick,
                    ),
                    text = "로그인",
                    color = White,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if (showBottomSheet) {
        BottomSheetDialog(
            onDismissRequest = { showBottomSheet = false },
            properties = BottomSheetDialogProperties(
                dismissWithAnimation = true,
                enableEdgeToEdge = true
            )
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .fillMaxWidth()
                    .background(White)
                    .navigationBarsPadding()
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.5.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(12.dp)
                        .clickable(
                            interactionSource = NoInteractionSource(),
                            indication = null,
                        ) {
                            if (isPrivacyChecked && isTermsChecked) {
                                isPrivacyChecked = false
                                isTermsChecked = false
                            }
                            else {
                                isPrivacyChecked = true
                                isTermsChecked = true
                            }
                        },
                ) {
                    CheckCircleIcon(tint = if (isPrivacyChecked && isTermsChecked) Blue500 else Gray300)

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "모두 동의합니다",
                        color = Gray900,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CheckIcon(
                        modifier = Modifier
                            .clickable(
                                interactionSource = NoInteractionSource(),
                                indication = null,
                                onClick = { isTermsChecked = !isTermsChecked }
                            ),
                        tint = if (isTermsChecked) Blue500 else Gray300
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "(필수) 서비스 이용약관",
                        color = Gray800,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    RightArrowIcon(tint = Gray800)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CheckIcon(
                        modifier = Modifier
                            .clickable(
                                interactionSource = NoInteractionSource(),
                                indication = null,
                                onClick = { isPrivacyChecked = !isPrivacyChecked }
                            ),
                        tint = if (isPrivacyChecked) Blue500 else Gray300
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "(필수) 개인정보 수집 및 이용에 대한 안내",
                        color = Gray800,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    RightArrowIcon(tint = Gray800)
                }

                Spacer(modifier = Modifier.height(16.dp))

                DodamFullWidthButton(
                    onClick = onRegisterClick,
                    enabled = isPrivacyChecked && isTermsChecked,
                    text = "다음"
                )
            }
        }
    }
}
