package com.b1nd.dodam.groupcreate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.ui.util.addFocusCleaner

@Composable
internal fun GroupCreateScreen(
    popBackStack: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    var groupNameText by remember { mutableStateOf("") }
    var groupDescriptionText by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager),
        containerColor = DodamTheme.colors.backgroundNormal,
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "새 그룹 만들기",
                onBackClick = popBackStack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    top = 8.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DodamTextField(
                modifier = Modifier.fillMaxWidth(),
                value = groupNameText,
                onValueChange = {
                    groupNameText = it
                },
                label = "그룹 이름",
            )

            DodamTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                value = groupDescriptionText,
                onValueChange = {
                    if (it.length > 300) return@DodamTextField
                    groupDescriptionText = it
                },
                label = "그룹 설명"
            )
            Text(
                modifier = Modifier.align(Alignment.End),
                text = buildAnnotatedString {
                    withStyle(
                        DodamTheme.typography.body1Medium().copy(
                            color = if (groupDescriptionText.length == 300) DodamTheme.colors.statusNegative else DodamTheme.colors.primaryNormal
                        ).toSpanStyle()
                    ) {
                        this.append("${groupDescriptionText.length}")
                    }
                    append("/300")
                },
                style = DodamTheme.typography.body1Medium(),
                color = if (groupDescriptionText.length == 300) DodamTheme.colors.statusNegative else DodamTheme.colors.labelAssistive
            )
            Spacer(modifier = Modifier.weight(1f))
            DodamButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                text = "만들기",
                buttonRole = ButtonRole.Primary,
                buttonSize = ButtonSize.Large
            )
        }
    }
}