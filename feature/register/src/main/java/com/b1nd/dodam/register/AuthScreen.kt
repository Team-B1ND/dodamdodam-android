package com.b1nd.dodam.register

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.b1nd.dodam.designsystem.component.DodamFullWidthButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.theme.BackIcon
import com.b1nd.dodam.designsystem.util.addFocusCleaner
import com.b1nd.dodam.register.state.TextFieldState
import com.b1nd.dodam.register.viewmodel.Event
import com.b1nd.dodam.register.viewmodel.RegisterViewModel

@Composable
fun AuthScreen(
    name: String,
    grade: String,
    room: String,
    number: String,
    email: String,
    phoneNumber: String,
    viewModel: RegisterViewModel = hiltViewModel(),
    navigateToMain: () -> Unit,
    onBackClick: () -> Unit
) {
    var idState by remember { mutableStateOf(TextFieldState()) }
    var passwordState by remember { mutableStateOf(TextFieldState()) }
    var confirmPasswordState by remember { mutableStateOf(TextFieldState()) }
    val focusManager = LocalFocusManager.current
    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is Event.NavigateToMain -> navigateToMain()
            }
        }
    }
    LaunchedEffect(idState.isValid) {
        if (idState.isValid) {
            focusManager.moveFocus(FocusDirection.Down)
        }
    }
    LaunchedEffect(passwordState.isValid) {
        if (passwordState.isValid) {
            focusManager.moveFocus(FocusDirection.Down)
        }
    }
    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is Event.NavigateToMain -> navigateToMain()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                })
    ) {
        BackIcon(
            modifier = Modifier
                .padding(16.dp)
                .statusBarsPadding()
                .clickable { onBackClick() }
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = if (passwordState.isValid) {
                    "비밀번호를 다시 입력해주세요"
                } else if (idState.isValid) {
                    "비밀번호를 입력해주세요"
                } else {
                    "아이디를 입력해주세요"
                },
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
            AnimatedVisibility(visible = passwordState.isValid && idState.isValid) {
                DodamTextField(
                    value = confirmPasswordState.value,
                    onValueChange = {
                        confirmPasswordState =
                            confirmPasswordState.copy(
                                value = it,
                                isValid = confirmPasswordState.isValid,
                                isError = false,
                                errorMessage = ""
                            )
                    },
                    modifier = Modifier.padding(top = 24.dp),
                    hint = "비밀번호 확인",
                    onClickCancel = { confirmPasswordState = TextFieldState() },
                    isPassword = true,
                    isError = confirmPasswordState.isError,
                    supportingText = if (confirmPasswordState.isError) confirmPasswordState.errorMessage else "",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        confirmPasswordState =
                            isConfirmPasswordValid(passwordState, confirmPasswordState)
                        focusManager.clearFocus()
                    }),
                )
            }
            AnimatedVisibility(visible = idState.isValid) {
                DodamTextField(
                    value = passwordState.value,
                    onValueChange = {
                        passwordState =
                            passwordState.copy(
                                value = it,
                                isValid = passwordState.isValid,
                                isError = false,
                                errorMessage = ""
                            )
                    },
                    modifier = Modifier.padding(top = 24.dp),
                    hint = "비밀번호",
                    onClickCancel = { passwordState = TextFieldState() },
                    isPassword = true,
                    isError = passwordState.isError,
                    supportingText = if (passwordState.isError) passwordState.errorMessage else "영문 + 숫자 8자리 이상이여야 합니다.",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        passwordState = isPasswordValid(passwordState)
                        focusManager.clearFocus()
                    }),
                )
            }
            DodamTextField(
                value = idState.value,
                onValueChange = {
                    if (it.length <= 20) {
                        idState = idState.copy(
                            value = it,
                            isValid = idState.isValid,
                            isError = false,
                            errorMessage = ""
                        )
                    }
                    if (it.length == 20) {
                        idState = isIdValid(idState)
                        focusManager.clearFocus()
                    }
                },
                onClickCancel = { idState = TextFieldState() },
                hint = "아이디",
                modifier = Modifier.padding(top = 24.dp),
                isError = idState.isError,
                supportingText = if (idState.isError) idState.errorMessage else "아이디는 5~20글자 이내여야 합니다.",
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    idState = isIdValid(idState)
                    focusManager.clearFocus()
                }),
            )
            AnimatedVisibility(visible = confirmPasswordState.isValid) {
                DodamFullWidthButton(
                    onClick = {
                        Log.d( "AuthScreen: ", "email: $email, grade: $grade, id: ${idState.value}, name: $name, number: $number, phone: $phoneNumber, pw: ${passwordState.value}, room: $room")
                        viewModel.register(
                            email = email,
                            grade = grade.toInt(),
                            id = idState.value,
                            name = name,
                            number = number.toInt(),
                            phone = phoneNumber,
                            pw = passwordState.value,
                            room = room.toInt()
                        )
                    },
                    text = "다음",
                    modifier = Modifier.padding(top = 32.dp),
                    enabled = idState.value.isNotBlank() &&
                            passwordState.value.isNotBlank() &&
                            confirmPasswordState.value.isNotBlank()
                )
            }
        }
    }
}

private fun isIdValid(idState: TextFieldState): TextFieldState {
    return if ((idState.value.length in 5..20) &&
        idState.value.matches(
            Regex(
                "^[A-Za-z0-9]+\$"
            )
        )
    ) {
        TextFieldState(
            idState.value,
            isValid = true,
            isError = false,
            errorMessage = ""
        )
    } else {
        TextFieldState(
            idState.value,
            isValid = false,
            isError = true,
            errorMessage = "아이디는 5~20글자 이내여야 합니다."
        )
    }
}

private fun isPasswordValid(passwordState: TextFieldState): TextFieldState {
    return if (passwordState.value.length >= 8 &&
        passwordState.value.matches(
            Regex(
                "^[A-Za-z0-9!@#\$%^&*()]+\$"
            )
        )
    ) {
        TextFieldState(
            passwordState.value,
            isValid = true,
            isError = false,
            errorMessage = ""
        )
    } else {
        TextFieldState(
            passwordState.value,
            isValid = false,
            isError = true,
            errorMessage = "영문 + 숫자 8자리 이상이여야 합니다."
        )
    }
}

private fun isConfirmPasswordValid(
    passwordState: TextFieldState,
    confirmPasswordState: TextFieldState
): TextFieldState {
    return if (passwordState.value == confirmPasswordState.value) {
        TextFieldState(
            confirmPasswordState.value,
            isValid = true,
            isError = false,
            errorMessage = ""
        )
    } else {
        TextFieldState(
            confirmPasswordState.value,
            isValid = false,
            isError = true,
            errorMessage = "비밀번호가 일치하지 않습니다."
        )
    }
}
