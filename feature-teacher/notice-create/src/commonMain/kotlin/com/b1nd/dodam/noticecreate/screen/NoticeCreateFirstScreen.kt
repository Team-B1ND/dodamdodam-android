package com.b1nd.dodam.noticecreate.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.DividerType
import com.b1nd.dodam.designsystem.component.DodamDivider
import com.b1nd.dodam.designsystem.component.DodamTextButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.foundation.DodamIcons

@Composable
fun NoticeCreateFirstScreen(
    title: String,
    onTitleValueChange: (String) -> Unit,
    body: String,
    onBodyValueChange: (String) -> Unit,
    popBackStack: () -> Unit,
    onNextClick: () -> Unit,
) {

    val bodyFocusRequester = remember { FocusRequester() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "공지 작성",
                onBackClick = popBackStack
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .navigationBarsPadding()
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                DodamDivider(
                    type = DividerType.Normal
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NoticeIconButton(
                        imageVector = DodamIcons.File.value,
                        onClick = {}
                    )
                    NoticeIconButton(
                        imageVector = DodamIcons.Photo.value,
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    DodamTextButton(
                        onClick = onNextClick,
                        text = "다음",
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        },
        containerColor = DodamTheme.colors.backgroundNormal
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp
                )
                .padding(it)
        ) {
            Spacer(Modifier.height(8.dp))
            DodamTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                label = "제목을 입력하세요",
                onValueChange = {
                    onTitleValueChange(it)
                },
                onClickRemoveRequest = {
                    onTitleValueChange("")
                }
            )
            Spacer(Modifier.height(24.dp))
            DodamTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                bodyFocusRequester.requestFocus()
                            }
                        )
                    },
                value = body,
                label = "대소고에 새로운 소식을 전해보세요",
                onValueChange = {
                    onBodyValueChange(it)
                },
                onClickRemoveRequest = {
                    onBodyValueChange("")
                },
                isShowDivider = false,
                focusRequester = bodyFocusRequester
            )
        }
    }
}

@Composable
private fun NoticeIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBounceIndication(),
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .size(28.dp),
            imageVector = imageVector,
            contentDescription = null,
            tint = DodamTheme.colors.labelNormal.copy(
                alpha = 0.5f
            )
        )
    }
}