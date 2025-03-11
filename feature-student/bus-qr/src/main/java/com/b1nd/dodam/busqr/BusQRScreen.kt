package com.b1nd.dodam.busqr

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import qrgenerator.qrkitpainter.QrKitLogo
import qrgenerator.qrkitpainter.rememberQrKitPainter

@Composable
internal fun BusQRScreen(
    popBackStack: () -> Unit,
    id: Int,
) {

    val painter = rememberQrKitPainter(
        data = "test"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DodamTheme.colors.backgroundNormal,)
    ) {
        DodamTopAppBar(
            modifier = Modifier.statusBarsPadding(),
            title = "QR리더기에 태깅해주세요",
            type = TopAppBarType.Medium,
            onBackClick = popBackStack
        )
        val primaryNormal = DodamTheme.colors.primaryNormal
        Box(
            modifier = Modifier
                .size(280.dp)
                .align(Alignment.Center)
                .drawBehind {
                    val width = 32.dp.toPx()

                    // 왼쪽 상단
                    drawLine(
                        start = Offset.Zero,
                        end = Offset(x = width, y = 0f),
                        color = primaryNormal,
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )

                    drawLine(
                        start = Offset.Zero,
                        end = Offset(x = 0f, y =  width),
                        color = primaryNormal,
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )

                    // 오른쪽 상단
                    drawLine(
                        start = Offset(x = size.width - width, y = 0f),
                        end = Offset(x = size.width, y = 0f),
                        color = primaryNormal,
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )

                    drawLine(
                        start = Offset(x = size.width, y = 0f),
                        end = Offset(x = size.width, y = width),
                        color = primaryNormal,
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )


                    // 왼쪽 하단
                    drawLine(
                        start = Offset(x = 0f, y = size.height - width),
                        end = Offset(x = 0f, y = size.height),
                        color = primaryNormal,
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )

                    drawLine(
                        start = Offset(x = 0f, y = size.height),
                        end = Offset(x = width, y = size.height),
                        color = primaryNormal,
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )

                    // 오른쪽 하단
                    drawLine(
                        start = Offset(x = size.width - 32.dp.toPx(), y = size.height),
                        end = Offset(x = size.width, y = size.height),
                        color = primaryNormal,
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )

                    drawLine(
                        start = Offset(x = size.width, y = size.height),
                        end = Offset(x = size.width, y =  size.height - width),
                        color = primaryNormal,
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                },
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier.size(232.dp),
                painter = painter,
                contentDescription = "QR코드"
            )
        }

        DodamButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 12.dp
                )
                .navigationBarsPadding()
                .fillMaxWidth(),
            onClick = {},
            text = "완료",
            buttonRole = ButtonRole.Primary,
            buttonSize = ButtonSize.Large
        )
    }
}