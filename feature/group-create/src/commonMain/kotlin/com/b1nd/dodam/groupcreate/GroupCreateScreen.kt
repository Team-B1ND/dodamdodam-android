package com.b1nd.dodam.groupcreate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.b1nd.dodam.groupcreate.model.GroupCreateSideEffect
import com.b1nd.dodam.groupcreate.viewmodel.GroupCreateViewModel
import com.b1nd.dodam.ui.component.SnackbarState
import com.b1nd.dodam.ui.util.addFocusCleaner
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun GroupCreateScreen(
    viewModel: GroupCreateViewModel = koinViewModel(),
    showSnackbar: (state: SnackbarState, message: String) -> Unit,
    popBackStack: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val uiState by viewModel.uiState.collectAsState()

    var groupNameText by remember { mutableStateOf("") }
    var groupDescriptionText by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is GroupCreateSideEffect.FailedGroupCreate -> {
                    showSnackbar(SnackbarState.ERROR, sideEffect.error.message ?: "Error")
                }
                GroupCreateSideEffect.SuccessGroupCreate -> {
                    showSnackbar(SnackbarState.SUCCESS, "새로운 그룹을 생성했어요!")
                    popBackStack()
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .addFocusCleaner(focusManager),
            containerColor = DodamTheme.colors.backgroundNormal,
            topBar = {
                DodamTopAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    title = "새 그룹 만들기",
                    onBackClick = popBackStack,
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp),
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
                    label = "그룹 설명",
                )
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = buildAnnotatedString {
                        withStyle(
                            DodamTheme.typography.body1Medium().copy(
                                color = if (groupDescriptionText.length == 300) DodamTheme.colors.statusNegative else DodamTheme.colors.primaryNormal,
                            ).toSpanStyle(),
                        ) {
                            this.append("${groupDescriptionText.length}")
                        }
                        append("/300")
                    },
                    style = DodamTheme.typography.body1Medium(),
                    color = if (groupDescriptionText.length == 300) DodamTheme.colors.statusNegative else DodamTheme.colors.labelAssistive,
                )
                Spacer(modifier = Modifier.weight(1f))
                DodamButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.createGroup(
                            name = groupNameText,
                            description = groupDescriptionText,
                        )
                    },
                    text = "만들기",
                    enabled = groupNameText.isNotEmpty() && groupDescriptionText.isNotEmpty(),
                    buttonRole = ButtonRole.Primary,
                    buttonSize = ButtonSize.Large,
                )
            }
        }
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DodamTheme.colors.staticBlack.copy(alpha = 0.3f)),
            )
        }
    }
}
