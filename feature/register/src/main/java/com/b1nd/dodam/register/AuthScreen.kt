package com.b1nd.dodam.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.dds.component.DodamDialog
import com.b1nd.dodam.dds.component.DodamLargeTopAppBar
import com.b1nd.dodam.dds.component.DodamTextField
import com.b1nd.dodam.dds.component.button.DodamCTAButton
import com.b1nd.dodam.dds.component.button.DodamTextButton
import com.b1nd.dodam.dds.style.EyeIcon
import com.b1nd.dodam.dds.style.EyeSlashIcon
import com.b1nd.dodam.dds.style.XMarkCircleIcon
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
        DodamDialog(
            onDismissRequest = { showDialog = false },
            confirmText = {
                DodamTextButton(onClick = { showDialog = false }) {
                    Text(text = "확인")
                }
            },
            title = { Text(text = uiState.error) },
        )
    }

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
        topBar = {
            DodamLargeTopAppBar(
                title = {
                    Text(
                        text = when {
                            passwordState.isValid -> "비밀번호를\n확인해주세요"
                            idState.isValid -> "비밀번호를\n입력해주세요"
                            else -> "아이디를\n입력해주세요"
                        },
                    )
                },
                onNavigationIconClick = onBackClick,
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
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
                modifier = Modifier.padding(horizontal = 24.dp),
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
                        label = { Text(text = "비밀번호 확인") },
                        trailingIcon = {
                            if (confirmPasswordState.focused) {
                                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                    if (showConfirmPassword) {
                                        EyeIcon(
                                            modifier = Modifier.clickable {
                                                showConfirmPassword = false
                                            },
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )
                                    } else {
                                        EyeSlashIcon(
                                            modifier = Modifier.clickable {
                                                showConfirmPassword = true
                                            },
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )
                                    }

                                    XMarkCircleIcon(
                                        modifier = Modifier.clickable {
                                            confirmPasswordState = TextFieldState()
                                        },
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }
                        },
                        isError = confirmPasswordState.isError,
                        supportingText = if (confirmPasswordState.isError) {
                            { Text(text = confirmPasswordState.errorMessage) }
                        } else {
                            null
                        },
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
                        label = { Text(text = "비밀번호") },
                        trailingIcon = {
                            if (passwordState.focused) {
                                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                    if (showPassword) {
                                        EyeIcon(
                                            modifier = Modifier.clickable {
                                                showPassword = false
                                            },
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )
                                    } else {
                                        EyeSlashIcon(
                                            modifier = Modifier.clickable {
                                                showPassword = true
                                            },
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )
                                    }

                                    XMarkCircleIcon(
                                        modifier = Modifier.clickable {
                                            passwordState = TextFieldState()
                                        },
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }
                        },
                        isError = passwordState.isError,
                        supportingText = if (passwordState.isError) {
                            { Text(text = passwordState.errorMessage) }
                        } else {
                            null
                        },
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
                    trailingIcon = {
                        if (idState.focused) {
                            XMarkCircleIcon(
                                modifier = Modifier.clickable {
                                    idState = TextFieldState()
                                },
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    },
                    label = { Text(text = "아이디") },
                    isError = idState.isError,
                    supportingText = {
                        Text(text = if (idState.isError) idState.errorMessage else "아이디는 영문과 숫자로 5~20글자 이내여야 해요.")
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        idState = isIdValid(idState)
                        focusManager.clearFocus()
                    }),
                    singleLine = true,
                )
            }
            DodamCTAButton(
                enabled = idState.value.isNotBlank() &&
                    passwordState.value.isNotBlank() &&
                    confirmPasswordState.value.isNotBlank(),
                onClick = {
                    viewModel.register(
                        email = email,
                        grade = grade.toInt(),
                        id = idState.value,
                        name = name,
                        number = number.toInt(),
                        phone = phoneNumber,
                        pw = passwordState.value,
                        room = room.toInt(),
                    )
                },
                isLoading = uiState.isLoading,
            ) {
                Text(text = "가입하기")
            }
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
