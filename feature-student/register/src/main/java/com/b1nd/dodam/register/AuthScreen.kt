package com.b1nd.dodam.register

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.b1nd.dodam.data.core.model.Children
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDialog
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.register.state.TextFieldState
import com.b1nd.dodam.register.viewmodel.Event
import com.b1nd.dodam.register.viewmodel.RegisterViewModel
import com.b1nd.dodam.ui.util.addFocusCleaner
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@Composable
fun AuthScreen(
    name: String,
    grade: String,
    room: String,
    number: String,
    email: String,
    phoneNumber: String,
    childrenList: List<Children>,
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
    var role by remember { mutableStateOf("") }

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
    LaunchedEffect(true) {
        role = if (childrenList.isNotEmpty()) "PARENT" else "STUDENT"
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                showDialog = false
            },
        ) {
            DodamDialog(
                confirmButton = {
                    showDialog = false
                },
                text = "확인",
                title = uiState.error,
            )
        }
    }

    Scaffold(
        modifier = Modifier
            .background(DodamTheme.colors.backgroundNeutral)
            .systemBarsPadding()
            .imePadding(),
        topBar = {
            DodamTopAppBar(
                title = when {
                    passwordState.isValid -> "비밀번호를\n확인해주세요"
                    idState.isValid -> "비밀번호를\n입력해주세요"
                    else -> "아이디를\n입력해주세요"
                },
                onBackClick = onBackClick,
                type = TopAppBarType.Medium,
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
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
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
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
                        onClickRemoveRequest = {
                            confirmPasswordState = confirmPasswordState.copy(value = "")
                        },
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
                        onClickRemoveRequest = {
                            passwordState = passwordState.copy(value = "")
                        },
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
                    onClickRemoveRequest = {
                        idState = idState.copy(value = "")
                    },
                    supportText = if (idState.isError) idState.errorMessage else "아이디는 영문과 숫자로 5~20글자 이내여야 해요.",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        idState = isIdValid(idState)
                        focusManager.clearFocus()
                    }),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(150.dp))
            }
            DodamButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp)
                    .align(Alignment.BottomCenter),
                enabled = idState.value.isNotBlank() &&
                    passwordState.value.isNotBlank() &&
                    confirmPasswordState.value.isNotBlank(),
                onClick = {
                    if (role == "STUDENT") {
                        viewModel.register(
                            email = email,
                            grade = grade.toInt(),
                            name = name,
                            id = idState.value,
                            number = number.toInt(),
                            phone = phoneNumber,
                            pw = passwordState.value,
                            room = room.toInt(),
                        )
                    } else {
                        Log.d("TAG", "parent ${idState.value}\n${passwordState.value}\n$name\n$childrenList\n$phoneNumber ")
                        viewModel.parentRegister(
                            id = idState.value,
                            pw = passwordState.value,
                            name = name,
                            childrenList = childrenList,
                            phone = phoneNumber,
                        )
                    }
                },
                loading = uiState.isLoading,
                buttonSize = ButtonSize.Large,
                buttonRole = ButtonRole.Primary,
                text = "가입하기",
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
