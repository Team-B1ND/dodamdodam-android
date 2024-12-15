package com.b1nd.dodam.groupwaiting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamModalBottomSheet
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.ui.component.DodamGroupMemberCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupWaitingScreen(
    popBackStack: () -> Unit
) {

    var showBottomSheet by remember { mutableStateOf(false) }
    if (showBottomSheet) {
        DodamModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            title = {},
            content = {
                Text(
                    text = "박병춘(박병준)님 정보",
                    style = DodamTheme.typography.heading1Bold(),
                    color = DodamTheme.colors.labelNormal
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "직군 | 학부모",
                    style = DodamTheme.typography.headlineMedium(),
                    color = DodamTheme.colors.labelAssistive
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DodamButton(
                        modifier = Modifier.weight(2f),
                        onClick = {},
                        text = "거절하기",
                        buttonSize = ButtonSize.Large,
                        buttonRole = ButtonRole.Assistive,
                    )
                    DodamButton(
                        modifier = Modifier.weight(3f),
                        onClick = {},
                        text = "승인하기",
                        buttonSize = ButtonSize.Large,
                        buttonRole = ButtonRole.Primary,
                    )
                }
            },
            space = 0.dp,
            shape = RoundedCornerShape(
                topStart = DodamTheme.shapes.extraLarge.topStart,
                topEnd = DodamTheme.shapes.extraLarge.topEnd,
                bottomEnd = CornerSize(0f),
                bottomStart = CornerSize(0f),
            )
        )
    }

    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "‘B1ND’ 그룹\n" +
                        "가입 신청 대기 중인 멤버",
                onBackClick = popBackStack,
                type = TopAppBarType.Large
            )
        },
        containerColor = DodamTheme.colors.backgroundNormal
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    modifier = Modifier.padding(
                        start = 16.dp
                    ),
                    text = "학생",
                    style = DodamTheme.typography.body2Medium(),
                    color = DodamTheme.colors.labelAlternative
                )
            }

            items(2) {
                DodamGroupMemberCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberBounceIndication(),
                            onClick = {
                                showBottomSheet = true
                            }
                        ),
                    image = null,
                    name = "test $it"
                )
            }

            item {
                Text(
                    modifier = Modifier.padding(
                        start = 16.dp
                    ),
                    text = "학부모",
                    style = DodamTheme.typography.body2Medium(),
                    color = DodamTheme.colors.labelAlternative
                )
            }

            items(2) {
                DodamGroupMemberCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberBounceIndication(),
                            onClick = {
                                showBottomSheet = true
                            }
                        ),
                    image = null,
                    name = "test $it"
                )
            }
        }
    }
}