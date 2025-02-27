package com.b1nd.dodam.busapply

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.b1nd.dodam.bus.model.Bus
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDialog
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.component.InputField
import com.b1nd.dodam.ui.component.modifier.`if`
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusApplyScreen(viewModel: BusApplyViewModel = koinViewModel(), popBackStack: () -> Unit, showToast: (String, String) -> Unit) {
    val lineNormal = DodamTheme.colors.lineNormal
    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "버스 좌석을\n선택해 주세요",
                onBackClick = popBackStack,
                type = TopAppBarType.Medium,
            )
        },
        containerColor = DodamTheme.colors.backgroundNormal,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .verticalScroll(rememberScrollState()),
            ) {
                Column(
                    modifier = Modifier
                        .drawBehind {
                            drawRoundRect(
                                color = lineNormal,
                                cornerRadius = CornerRadius(9.dp.toPx()),
                                size = Size(
                                    width = 12.dp.toPx(),
                                    height = 108.dp.toPx()
                                ),
                                topLeft = Offset(
                                    x = -7.dp.toPx(),
                                    y = 48.dp.toPx()
                                )
                            )
                            drawRoundRect(
                                color = lineNormal,
                                cornerRadius = CornerRadius(9.dp.toPx()),
                                size = Size(
                                    width = 12.dp.toPx(),
                                    height = 108.dp.toPx()
                                ),
                                topLeft = Offset(
                                    x = size.width - 5.dp.toPx(),
                                    y = 48.dp.toPx()
                                )
                            )
                            drawRoundRect(
                                color = lineNormal,
                                cornerRadius = CornerRadius(9.dp.toPx()),
                                size = Size(
                                    width = 12.dp.toPx(),
                                    height = 108.dp.toPx()
                                ),
                                topLeft = Offset(
                                    x = -7.dp.toPx(),
                                    y = size.height - 156.dp.toPx()
                                )
                            )
                            drawRoundRect(
                                color = lineNormal,
                                cornerRadius = CornerRadius(9.dp.toPx()),
                                size = Size(
                                    width = 12.dp.toPx(),
                                    height = 108.dp.toPx()
                                ),
                                topLeft = Offset(
                                    x = size.width - 5.dp.toPx(),
                                    y = size.height - 156.dp.toPx()
                                )
                            )
                        }
                        .background(
                            color = DodamTheme.colors.backgroundNeutral,
                            shape = DodamTheme.shapes.large
                        )
                        .padding(horizontal = 18.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // 첫 번째 아이템
                    Box(
                        modifier = Modifier
                            .height(58.dp)
                            .align(Alignment.End)
                            .padding(
                                top = 4.dp,
                            ),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            modifier = Modifier.size(22.dp),
                            imageVector = DodamIcons.ArrowLeft.value,
                            contentDescription = null,
                            tint = DodamTheme.colors.labelAssistive
                        )
                    }

                    for (rowIndex in 0 until 11) {
                        if (rowIndex < 10) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                BusSeat(
                                    text = "${rowIndex * 4 + 1}",
                                    selected = false
                                )
                                BusSeat(
                                    text = "${rowIndex * 4 + 2}",
                                    selected = false
                                )
                                Spacer(modifier = Modifier.size(44.dp))
                                BusSeat(
                                    text = "${rowIndex * 4 + 3}",
                                    selected = false
                                )
                                BusSeat(
                                    text = "${rowIndex * 4 + 4}",
                                    selected = false
                                )
                            }
                        } else {
                            Row(
                                modifier = Modifier.padding(bottom = 18.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                for (i in 0 until 5) {
                                    BusSeat(
                                        text = "${40 + i + 1}",
                                        selected = false
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
            DodamButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
                    .fillMaxWidth(),
                onClick = {},
                text = "신청",
                buttonRole = ButtonRole.Primary,
                buttonSize = ButtonSize.Large,
            )
        }
    }
}

@Composable
private fun BusSeat(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
) {
    Box(
        modifier = modifier
            .size(44.dp)
            .background(
                color = if (selected) DodamTheme.colors.primaryNormal else DodamTheme.colors.backgroundNormal,
                shape = RoundedCornerShape(4.dp),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = DodamTheme.typography.body1Medium(),
            color = if (selected) DodamTheme.colors.staticWhite else DodamTheme.colors.labelAssistive
        )
    }
}