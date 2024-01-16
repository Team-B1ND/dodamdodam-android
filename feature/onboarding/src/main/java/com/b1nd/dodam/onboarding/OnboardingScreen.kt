package com.b1nd.dodam.onboarding

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.animation.NoInteractionSource
import com.b1nd.dodam.designsystem.component.DodamFullWidthButton
import com.b1nd.dodam.designsystem.theme.Black
import com.b1nd.dodam.designsystem.theme.White

@Composable
internal fun OnboardingScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.onboarding_background),
            contentDescription = "onboarding background",
            contentScale = ContentScale.Crop
        )

        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                drawRect(
                    color = Black,
                    alpha = 0.5f
                )
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.onboarding_logo),
                contentDescription = "dodam logo"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "[부사] 어린아이가 탈 없이 잘 놀며 자라는 모양.",
                color = White,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            DodamFullWidthButton(
                onClick = onRegisterClick,
                text = "시작하기"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text(
                    text = "이미 계정이 있나요?",
                    color = White,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    modifier = Modifier.clickable(
                        interactionSource = NoInteractionSource(),
                        indication = null,
                        onClick = onLoginClick
                    ),
                    text = "로그인",
                    color = White,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
