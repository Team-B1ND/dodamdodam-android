package com.b1nd.dodam.onboarding

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.dds.component.button.DodamCTAButton
import com.b1nd.dodam.dds.foundation.DodamColor
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.CheckmarkCircleIcon
import com.b1nd.dodam.dds.style.CheckmarkIcon
import com.b1nd.dodam.dds.style.ChevronRightIcon
import com.b1nd.dodam.dds.style.LabelLarge
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties

@Composable
internal fun OnboardingScreen(onRegisterClick: () -> Unit, onLoginClick: () -> Unit) {
    val context = LocalContext.current

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
                    color = DodamColor.Black,
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
                text = "어린아이가 탈 없이 잘 놀며 자라는 모양.",
                color = DodamColor.White,
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.weight(1f))

            com.b1nd.dodam.dds.component.button.DodamCTAButton(
                onClick = { showBottomSheet = true },
                content = {
                    Text(text = "시작하기")
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text(
                    text = "이미 계정이 있나요?",
                    color = DodamColor.White,
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onLoginClick,
                    ),
                    text = "로그인",
                    color = DodamColor.White,
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
                enableEdgeToEdge = true,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.background,
                        RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                    )
                    .navigationBarsPadding()
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.5.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = MaterialTheme.shapes.small,
                        )
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                if (isPrivacyChecked && isTermsChecked) {
                                    isPrivacyChecked = false
                                    isTermsChecked = false
                                } else {
                                    isPrivacyChecked = true
                                    isTermsChecked = true
                                }
                            },
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    CheckmarkCircleIcon(
                        tint = if (isPrivacyChecked && isTermsChecked) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.outline
                        },
                    )

                    BodyLarge(text = "모두 동의합니다", color = MaterialTheme.colorScheme.onBackground)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CheckmarkIcon(
                        modifier = Modifier
                            .size(32.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { isTermsChecked = !isTermsChecked },
                            ),
                        tint = if (isTermsChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    context.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("http://dodam.b1nd.com/detailed-information/service-policy"),
                                        ),
                                    )
                                },
                            ),
                    ) {
                        LabelLarge(text = "(필수) 서비스 이용약관", color = MaterialTheme.colorScheme.tertiary)

                        Spacer(modifier = Modifier.weight(1f))

                        ChevronRightIcon(
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CheckmarkIcon(
                        modifier = Modifier
                            .size(32.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { isPrivacyChecked = !isPrivacyChecked },
                            ),
                        tint = if (isPrivacyChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    context.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("http://dodam.b1nd.com/detailed-information/personal-information"),
                                        ),
                                    )
                                },
                            ),
                    ) {
                        LabelLarge(text = "(필수) 개인정보 수집 및 이용에 대한 안내", color = MaterialTheme.colorScheme.tertiary)

                        Spacer(modifier = Modifier.weight(1f))

                        ChevronRightIcon(
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                DodamCTAButton(
                    onClick = onRegisterClick,
                    enabled = isPrivacyChecked && isTermsChecked,
                    content = { Text(text = "다음") },
                )
            }
        }
    }
}
