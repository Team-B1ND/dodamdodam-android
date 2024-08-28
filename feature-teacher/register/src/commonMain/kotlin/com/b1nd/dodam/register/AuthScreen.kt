package com.b1nd.dodam.register

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamContentTopAppBar
import com.b1nd.dodam.designsystem.component.DodamDialog
import com.b1nd.dodam.designsystem.component.DodamTextButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.register.state.TextFieldState
import com.b1nd.dodam.register.viewmodel.Event
import com.b1nd.dodam.register.viewmodel.RegisterViewModel
import com.b1nd.dodam.ui.util.addFocusCleaner
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@ExperimentalMaterial3Api
@Composable
fun AuthScreen(
    name: String,
    teacherRole: String,
    email: String,
    phoneNumber: String,
    extensionNumber: String,
    viewModel: RegisterViewModel = koinViewModel(),
    navigateToMain: () -> Unit,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    var idState by remember { mutableStateOf(TextFieldState()) }
    var passwordState by remember { mutableStateOf(TextFieldState()) }
    var confirmPasswordState by remember { mutableStateOf(TextFieldState()) }
    val focusManager = LocalFocusManager.current

    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(idState.isValid) {
        if (idState.isValid) {
            focusManager.moveFocus(FocusDirection.Down)
        }
    }
    LaunchedEffect(passwordState.isValid) {
        if (passwordState.isValid) {
            focusManager.moveFocus(FocusDirection.Up)
        }
    }
    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is Event.NavigateToMain -> navigateToMain()
                is Event.ShowDialog -> showDialog = true
            }
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                showDialog = false
            }
        ) {
            DodamDialog(
                confirmButton = {
                    showDialog = false
                },
                title = uiState.error,
            )
        }
    }

    Scaffold(
        modifier = Modifier
            .background(DodamTheme.colors.backgroundNeutral)
            .systemBarsPadding(),
        topBar = {
            Column {
                DodamContentTopAppBar(
                    content = {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberBounceIndication(),
                                    onClick = onBackClick
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(24.dp),
                                imageVector = DodamIcons.ArrowLeft.value,
                                contentDescription = "뒤로가기",
                                colorFilter = ColorFilter.tint(DodamTheme.colors.labelNormal)
                            )
                        }
                    }
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = when {
                        passwordState.isValid -> "비밀번호를\n확인해주세요"
                        idState.isValid -> "비밀번호를\n입력해주세요"
                        else -> "아이디를\n입력해주세요"
                    },
                    style = DodamTheme.typography.title3Bold(),
                    color = DodamTheme.colors.labelNormal,
                )
            }
        },
        containerColor = DodamTheme.colors.backgroundNeutral
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .addFocusCleaner(
                    focusManager = focusManager,
                    doOnClear = {
                        if (idState.value.isNotBlank()) {
                            idState = isIdValid(idState)
                        }
                        if (passwordState.value.isNotBlank()) {
                            passwordState = isPasswordValid(passwordState)
                        }
                        if (confirmPasswordState.value.isNotBlank()) {
                            confirmPasswordState =
                                isConfirmPasswordValid(passwordState, confirmPasswordState)
                        }
                    },
                ),
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Spacer(Modifier.height(0.dp))
                AnimatedVisibility(visible = passwordState.isValid && idState.isValid) {
                    DodamTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                confirmPasswordState =
                                    confirmPasswordState.copy(focused = it.isFocused)
                            },
                        value = confirmPasswordState.value,
                        onValueChange = {
                            confirmPasswordState =
                                confirmPasswordState.copy(
                                    value = it,
                                    isValid = confirmPasswordState.isValid,
                                    isError = false,
                                    errorMessage = "",
                                )
                        },
                        label = "비밀번호 확인",
                        isError = confirmPasswordState.isError,
                        supportText = if (confirmPasswordState.isError) confirmPasswordState.errorMessage else "",
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            confirmPasswordState =
                                isConfirmPasswordValid(passwordState, confirmPasswordState)
                            focusManager.clearFocus()
                        }),
                        visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                    )
                }
                AnimatedVisibility(visible = idState.isValid) {
                    DodamTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                passwordState =
                                    passwordState.copy(focused = it.isFocused)
                            },
                        value = passwordState.value,
                        onValueChange = {
                            passwordState =
                                passwordState.copy(
                                    value = it,
                                    isValid = passwordState.isValid,
                                    isError = false,
                                    errorMessage = "",
                                )
                        },
                        label = "비밀번호",
                        isError = passwordState.isError,
                        supportText = if (passwordState.isError) passwordState.errorMessage else "",
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = {
                            passwordState = isPasswordValid(passwordState)
                            if (passwordState.isValid) {
                                focusManager.moveFocus(FocusDirection.Up)
                            }
                        }),
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                    )
                }
                DodamTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            idState = idState.copy(focused = it.isFocused)
                        },
                    value = idState.value,
                    onValueChange = {
                        if (it.length <= 20) {
                            idState = idState.copy(
                                value = it,
                                isValid = idState.isValid,
                                isError = false,
                                errorMessage = "",
                            )
                        }
                        if (it.length == 20) {
                            idState = isIdValid(idState)
                            focusManager.clearFocus()
                        }
                    },
                    label = "아이디",
                    isError = idState.isError,
                    supportText = if (idState.isError) idState.errorMessage else "아이디는 영문과 숫자로 5~20글자 이내여야 해요.",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        idState = isIdValid(idState)
                        focusManager.clearFocus()
                    }),
                    singleLine = true,
                )
            }
            DodamButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    )
                    .align(Alignment.BottomCenter),
                enabled = idState.value.isNotBlank() &&
                        passwordState.value.isNotBlank() &&
                        confirmPasswordState.value.isNotBlank(),
                onClick = {
                    viewModel.register(
                        email = email,
                        id = idState.value,
                        name = name,
                        phone = phoneNumber,
                        pw = passwordState.value,
                        tel = extensionNumber,
                        position = teacherRole
                    )
                },
                loading = uiState.isLoading,
                buttonSize = ButtonSize.Large,
                buttonRole = ButtonRole.Primary,
                text = "가입하기"
            )
        }
    }
}

private fun isIdValid(idState: TextFieldState): TextFieldState {
    return if ((idState.value.length in 5..20) &&
        idState.value.matches(
            Regex(
                "^[A-Za-z0-9]+\$",
            ),
        )
    ) {
        TextFieldState(
            idState.value,
            isValid = true,
            isError = false,
            errorMessage = "",
        )
    } else {
        TextFieldState(
            idState.value,
            isValid = false,
            isError = true,
            errorMessage = "아이디는 5~20글자 이내여야 합니다.",
        )
    }
}

private fun isPasswordValid(passwordState: TextFieldState): TextFieldState {
    return if (passwordState.value.length >= 8 &&
        passwordState.value.matches(
            Regex(
                "^[A-Za-z0-9!@#\$%^&*()]+\$",
            ),
        )
    ) {
        TextFieldState(
            passwordState.value,
            isValid = true,
            isError = false,
            errorMessage = "",
        )
    } else {
        TextFieldState(
            passwordState.value,
            isValid = false,
            isError = true,
            errorMessage = "영문 + 숫자 8자리 이상이여야 합니다.",
        )
    }
}

private fun isConfirmPasswordValid(passwordState: TextFieldState, confirmPasswordState: TextFieldState): TextFieldState {
    return if (passwordState.value == confirmPasswordState.value) {
        TextFieldState(
            confirmPasswordState.value,
            isValid = true,
            isError = false,
            errorMessage = "",
        )
    } else {
        TextFieldState(
            confirmPasswordState.value,
            isValid = false,
            isError = true,
            errorMessage = "비밀번호가 일치하지 않습니다.",
        )
    }
}
