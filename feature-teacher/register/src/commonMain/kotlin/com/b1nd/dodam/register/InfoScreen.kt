package com.b1nd.dodam.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamContentTopAppBar
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.register.state.TextFieldState
import com.b1nd.dodam.ui.util.PhoneVisualTransformation
import com.b1nd.dodam.ui.util.addFocusCleaner

@ExperimentalMaterial3Api
@Composable
fun InfoScreen(
    onBackClick: () -> Unit,
    onNextClick: (
        name: String,
        teacherRole: String,
        email: String,
        phoneNumber: String,
        extensionNumber: String,
    ) -> Unit,
) {
    var nameState by remember { mutableStateOf(TextFieldState()) }
    var teacherRoleState by remember { mutableStateOf(TextFieldState()) }
    var emailState by remember { mutableStateOf(TextFieldState()) }
    var phoneNumberState by remember { mutableStateOf(TextFieldState()) }
    var extensionNumberState by remember { mutableStateOf(TextFieldState()) }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(phoneNumberState.isValid) {
        if (phoneNumberState.isValid) {
            focusManager.moveFocus(FocusDirection.Up)
        }
    }

    LaunchedEffect(teacherRoleState.isValid) {
        if (teacherRoleState.isValid) {
            focusManager.moveFocus(FocusDirection.Up)
        }
    }
    LaunchedEffect(emailState.isValid) {
        if (emailState.isValid) {
            focusManager.moveFocus(FocusDirection.Up)
        }
    }
    LaunchedEffect(nameState.isValid) {
        if (nameState.isValid) {
            focusManager.moveFocus(FocusDirection.Up)
        }
    }

    Scaffold(
        modifier = Modifier
            .background(DodamTheme.colors.backgroundNeutral)
            .statusBarsPadding(),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .addFocusCleaner(
                        focusManager = focusManager,
                        doOnClear = {
                            if (nameState.value.isNotEmpty()) {
                                nameState = checkNameStateValid(nameState)
                            }
                            if (teacherRoleState.value.isNotEmpty()) {
                                focusManager.moveFocus(FocusDirection.Down)
                                teacherRoleState = checkTeacherRoleStateValid(teacherRoleState)
                            }
                            if (emailState.value.isNotEmpty()) {
                                emailState = checkEmailStateValid(emailState)
                            }
                            if (phoneNumberState.value.isNotEmpty()) {
                                phoneNumberState = checkPhoneNumberStateValid(phoneNumberState)
                            }

                            if (extensionNumberState.value.isNotEmpty()) {
                                extensionNumberState = checkExtensionNumberStateValid(extensionNumberState)
                            }
                        },
                    ),
            ) {
                DodamContentTopAppBar(
                    content = {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberBounceIndication(),
                                    onClick = onBackClick,
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(24.dp),
                                imageVector = DodamIcons.ArrowLeft.value,
                                contentDescription = "뒤로가기",
                                colorFilter = ColorFilter.tint(DodamTheme.colors.labelNormal),
                            )
                        }
                    },
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = when {
                        setOf(
                            nameState,
                            emailState,
                            teacherRoleState,
                            phoneNumberState,
                        ).all { it.isValid } -> "내선 번호를\n입력해주세요"

                        setOf(
                            nameState,
                            emailState,
                            teacherRoleState,
                        ).all { it.isValid } -> "전화번호를\n입력해주세요"

                        setOf(nameState, teacherRoleState).all { it.isValid } -> "이메일을\n입력해주세요"
                        nameState.isValid -> "교사 역할을\n입력해주세요"
                        else -> "이름을\n입력해주세요"
                    },
                    style = DodamTheme.typography.title3Bold(),
                    color = DodamTheme.colors.labelNormal,
                )
            }
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
                        if (nameState.value.isNotEmpty()) {
                            nameState = checkNameStateValid(nameState)
                        }
                        if (teacherRoleState.value.isNotEmpty()) {
                            focusManager.moveFocus(FocusDirection.Down)
                            teacherRoleState = checkTeacherRoleStateValid(teacherRoleState)
                        }
                        if (emailState.value.isNotEmpty()) {
                            emailState = checkEmailStateValid(emailState)
                        }
                        if (phoneNumberState.value.isNotEmpty()) {
                            phoneNumberState = checkPhoneNumberStateValid(phoneNumberState)
                        }
                        if (extensionNumberState.value.isNotEmpty()) {
                            extensionNumberState = checkExtensionNumberStateValid(extensionNumberState)
                        }
                    },
                ),
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Spacer(Modifier.height(0.dp))
                AnimatedVisibility(
                    visible = setOf(
                        nameState,
                        emailState,
                        teacherRoleState,
                        phoneNumberState,
                    ).all { it.isValid },
                ) {
                    DodamTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                extensionNumberState = extensionNumberState.copy(focused = it.isFocused)
                            },
                        value = extensionNumberState.value,
                        onValueChange = {
                            if (it.length <= 10) {
                                extensionNumberState =
                                    extensionNumberState.copy(
                                        value = it,
                                        isValid = extensionNumberState.isValid,
                                        isError = false,
                                        errorMessage = "",
                                    )
                            }
                            if (it.length == 10) {
                                extensionNumberState = checkExtensionNumberStateValid(extensionNumberState)
                                focusManager.clearFocus()
                            }
                        },
                        label = "내선 번호",
                        visualTransformation = PhoneVisualTransformation(
                            "000-000-0000",
                            '0',
                        ),
                        isError = extensionNumberState.isError,
                        supportText = if (extensionNumberState.isError) extensionNumberState.errorMessage else "",
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number,
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            extensionNumberState = checkExtensionNumberStateValid(extensionNumberState)
                            focusManager.clearFocus()
                        }),
                        singleLine = true,
                    )
                }
                AnimatedVisibility(
                    visible = setOf(
                        nameState,
                        emailState,
                        teacherRoleState,
                    ).all { it.isValid },
                ) {
                    DodamTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                phoneNumberState = phoneNumberState.copy(focused = it.isFocused)
                            },
                        value = phoneNumberState.value,
                        onValueChange = {
                            if (it.length <= 11) {
                                phoneNumberState =
                                    phoneNumberState.copy(
                                        value = it,
                                        isValid = phoneNumberState.isValid,
                                        isError = false,
                                        errorMessage = "",
                                    )
                            }
                            if (it.length == 11) {
                                phoneNumberState = checkPhoneNumberStateValid(phoneNumberState)
                                focusManager.clearFocus()
                            }
                        },
                        label = "전화번호",
                        visualTransformation = PhoneVisualTransformation(
                            "000-0000-0000",
                            '0',
                        ),
                        isError = phoneNumberState.isError,
                        supportText = if (phoneNumberState.isError) phoneNumberState.errorMessage else "",
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number,
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            phoneNumberState = checkPhoneNumberStateValid(phoneNumberState)
                            focusManager.clearFocus()
                        }),
                        singleLine = true,
                    )
                }
                AnimatedVisibility(visible = nameState.isValid && teacherRoleState.isValid) {
                    DodamTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                emailState = emailState.copy(focused = it.isFocused)
                            },
                        value = emailState.value,
                        onValueChange = {
                            emailState = emailState.copy(
                                value = it,
                                isValid = emailState.isValid,
                                isError = false,
                                errorMessage = "",
                            )
                        },
                        isError = emailState.isError,
                        supportText = if (emailState.isError) emailState.errorMessage else "",
                        label = "이메일",
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Email,
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            emailState = checkEmailStateValid(emailState)
                            if (emailState.isValid) {
                                focusManager.moveFocus(FocusDirection.Up)
                            } else {
                                focusManager.clearFocus()
                            }
                        }),
                        singleLine = true,
                    )
                }
                AnimatedVisibility(visible = nameState.isValid) {
                    DodamTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                teacherRoleState = teacherRoleState.copy(focused = it.isFocused)
                            },
                        value = teacherRoleState.value,
                        onValueChange = { text ->
                            teacherRoleState = teacherRoleState.copy(
                                value = text,
                                isValid = teacherRoleState.isValid,
                                isError = false,
                                errorMessage = "",
                            )
                        },
                        label = "교사 역할",
                        isError = teacherRoleState.isError,
                        supportText = if (teacherRoleState.isError) teacherRoleState.errorMessage else "",
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = {
                            teacherRoleState = checkTeacherRoleStateValid(teacherRoleState)
                            if (teacherRoleState.isValid) {
                                focusManager.moveFocus(FocusDirection.Up)
                            } else {
                                focusManager.clearFocus()
                            }
                        }),
                        singleLine = true,
                    )
                }

                DodamTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            nameState = nameState.copy(focused = it.isFocused)
                        },
                    value = nameState.value,
                    onValueChange = { text ->
                        nameState = nameState.copy(
                            value = text,
                            isValid = nameState.isValid,
                            isError = false,
                            errorMessage = "",
                        )
                    },
                    label = "이름",
                    isError = nameState.isError,
                    supportText = if (nameState.isError) nameState.errorMessage else "",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        nameState = checkNameStateValid(nameState)
                        if (nameState.isValid) {
                            focusManager.moveFocus(FocusDirection.Up)
                        } else {
                            focusManager.clearFocus()
                        }
                    }),
                    singleLine = true,
                )
            }

            DodamButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                    )
                    .align(Alignment.BottomCenter),
                onClick = {
                    onNextClick(
                        nameState.value,
                        teacherRoleState.value,
                        emailState.value,
                        phoneNumberState.value,
                        extensionNumberState.value,
                    )
                },
                enabled = nameState.value.length in 2..4 &&
                    teacherRoleState.value.isNotEmpty() &&
                    emailState.value.isNotBlank() &&
                    phoneNumberState.value.length == 11 &&
                    extensionNumberState.value.length == 10,
                text = "다음",
                buttonSize = ButtonSize.Large,
                buttonRole = ButtonRole.Primary,
            )
        }
    }
}

private fun checkNameStateValid(nameState: TextFieldState): TextFieldState {
    return if (nameState.value.length in 2..4) {
        TextFieldState(
            value = nameState.value,
            isValid = true,
            isError = false,
            errorMessage = "",
        )
    } else {
        TextFieldState(
            value = nameState.value,
            isValid = false,
            isError = true,
            errorMessage = "이름은 2~4글자로 입력해주세요",
        )
    }
}

private fun checkTeacherRoleStateValid(teacherRoleState: TextFieldState): TextFieldState {
    return if (teacherRoleState.value.isEmpty()) {
        TextFieldState(
            value = teacherRoleState.value,
            isValid = false,
            isError = true,
            errorMessage = "교사 역할을 입력해 주세요",
        )
    } else {
        TextFieldState(
            value = teacherRoleState.value,
            isValid = true,
            isError = false,
            errorMessage = "",
        )
    }
}

private fun checkEmailStateValid(emailState: TextFieldState): TextFieldState {
    return if (emailState.value.isEmpty()) {
        TextFieldState(
            value = emailState.value,
            isValid = false,
            isError = true,
            errorMessage = "이메일을 입력해 주세요",
        )
    } else if (!"^(?:[^@]*@[^@]*)\$"
            .toRegex()
            .matches(emailState.value)
    ) {
        TextFieldState(
            value = emailState.value,
            isValid = false,
            isError = true,
            errorMessage = "이메일을 다시 확인해 주세요.",
        )
    } else {
        TextFieldState(
            value = emailState.value,
            isValid = true,
            isError = false,
            errorMessage = "",
        )
    }
}

private fun checkPhoneNumberStateValid(phoneNumber: TextFieldState): TextFieldState {
    return if (phoneNumber.value.length == 11) {
        TextFieldState(
            value = phoneNumber.value,
            isValid = true,
            isError = false,
            errorMessage = "",
        )
    } else {
        TextFieldState(
            value = phoneNumber.value,
            isValid = false,
            isError = true,
            errorMessage = "전화번호를 입력해주세요",
        )
    }
}

private fun checkExtensionNumberStateValid(extensionNumber: TextFieldState): TextFieldState {
    return when {
        extensionNumber.value.length != 10 ->
            TextFieldState(
                value = extensionNumber.value,
                isValid = false,
                isError = true,
                errorMessage = "내선 번호를 입력해주세요",
            )

        extensionNumber.value.substring(0, 3) != "053" -> {
            TextFieldState(
                value = extensionNumber.value,
                isValid = false,
                isError = true,
                errorMessage = "내선 번호를 입력해주세요",
            )
        }
        else ->
            TextFieldState(
                value = extensionNumber.value,
                isValid = true,
                isError = false,
                errorMessage = "",
            )
    }
}
