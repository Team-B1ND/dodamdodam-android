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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
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
import com.b1nd.dodam.register.shared.getProductName
import com.b1nd.dodam.register.state.TextFieldState
import com.b1nd.dodam.register.viewmodel.InfoEvent
import com.b1nd.dodam.register.viewmodel.InfoViewModel
import com.b1nd.dodam.ui.util.PhoneVisualTransformation
import com.b1nd.dodam.ui.util.addFocusCleaner
import com.b1nd.dodam.ui.util.moveFocus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterial3Api
@Composable
fun InfoScreen(
    onBackClick: () -> Unit,
    viewModel: InfoViewModel = koinViewModel(),
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
    var phoneCodeState by remember { mutableStateOf(TextFieldState()) }

    var buttonText by remember { mutableStateOf("전화번호 인증코드 전송") }
    var buttonEnabled by remember { mutableStateOf(false) }
    var showExtensionNumber by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(phoneCodeState.isValid) {
        if (phoneCodeState.isValid && !phoneCodeState.focused) {
            focusManager.moveFocus(FocusDirection.Up, 6)
        }
    }
    LaunchedEffect(phoneNumberState.isValid) {
        if (phoneNumberState.isValid && !phoneNumberState.focused) {
            buttonEnabled = true
            focusManager.moveFocus(FocusDirection.Up, 5)
        }
    }
    LaunchedEffect(emailState.isValid) {
        if (emailState.isValid && !emailState.focused) {
            focusManager.moveFocus(FocusDirection.Up, 4)
        }
    }
    LaunchedEffect(teacherRoleState.isValid) {
        if (teacherRoleState.isValid && !teacherRoleState.focused) {
            focusManager.moveFocus(FocusDirection.Up, 3)
        }
    }
    LaunchedEffect(nameState.isValid) {
        if (nameState.isValid && !nameState.focused) {
            focusManager.moveFocus(FocusDirection.Up, 2)
        }
    }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is InfoEvent.SuccessGetAuthPhoneCode -> {
                    buttonText = "인증"
                }
                is InfoEvent.FiledVerifyAuthCode -> {
                    phoneCodeState = TextFieldState(
                        value = phoneCodeState.value,
                        isValid = false,
                        isError = true,
                        errorMessage = "인증번호가 틀렸습니다.",
                    )
                }
                is InfoEvent.SuccessVerifyAuthPhoneCode -> {
                    showExtensionNumber = true
                    buttonText = "다음"
                }
            }
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
                            if (phoneCodeState.value.isNotEmpty()) {
                                phoneCodeState = checkPhoneCodeStateValid(phoneCodeState)
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
                        showExtensionNumber -> "내선 번호를\n입력해주세요"

                        setOf(
                            nameState,
                            emailState,
                            teacherRoleState,
                            phoneNumberState,
                        ).all { it.isValid } -> "인증번호를\n입력해주세요"

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
        containerColor = DodamTheme.colors.backgroundNeutral,
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
                        if (phoneCodeState.value.isNotEmpty()) {
                            phoneCodeState = checkPhoneCodeStateValid(phoneCodeState)
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
                    visible = showExtensionNumber,
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
                        onClickRemoveRequest = {
                            extensionNumberState = extensionNumberState.copy(
                                value = "",
                                isValid = false,
                                isError = false,
                                errorMessage = "",
                            )
                        },
                    )
                }
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
                                phoneCodeState = phoneCodeState.copy(focused = it.isFocused)
                            },
                        value = phoneCodeState.value,
                        onValueChange = {
                            if (it.length <= 6) {
                                phoneCodeState =
                                    phoneCodeState.copy(
                                        value = it,
                                        isValid = phoneCodeState.isValid,
                                        isError = false,
                                        errorMessage = "",
                                    )
                            }
                            if (it.length == 6) {
                                phoneCodeState = checkPhoneCodeStateValid(phoneCodeState)
                                focusManager.clearFocus()
                            }
                        },
                        label = "인증번호",
                        isError = phoneCodeState.isError,
                        supportText = if (phoneCodeState.isError) phoneCodeState.errorMessage else "",
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number,
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            phoneCodeState = checkPhoneCodeStateValid(phoneCodeState)
                            if (phoneCodeState.isValid) {
                                coroutineScope.launch {
                                    delay(100)
                                    focusManager.moveFocus(FocusDirection.Up)
                                }
                            } else {
                                focusManager.clearFocus()
                            }
                        }),
                        singleLine = true,
                        onClickRemoveRequest = {
                            phoneCodeState = phoneCodeState.copy(
                                value = "",
                                isValid = false,
                                isError = false,
                                errorMessage = "",
                            )
                        },
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
                            if (phoneNumberState.isValid) {
                                coroutineScope.launch {
                                    delay(100)
                                    focusManager.moveFocus(FocusDirection.Up)
                                }
                            } else {
                                focusManager.clearFocus()
                            }
                        }),
                        singleLine = true,
                        onClickRemoveRequest = {
                            phoneNumberState = phoneNumberState.copy(
                                value = "",
                                isValid = false,
                                isError = false,
                                errorMessage = "",
                            )
                        },
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
                                coroutineScope.launch {
                                    delay(100)
                                    focusManager.moveFocus(FocusDirection.Up)
                                }
                            } else {
                                focusManager.clearFocus()
                            }
                        }),
                        singleLine = true,
                        onClickRemoveRequest = {
                            emailState = emailState.copy(
                                value = "",
                                isValid = false,
                                isError = false,
                                errorMessage = "",
                            )
                        },
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
                                coroutineScope.launch {
                                    delay(100)
                                    focusManager.moveFocus(FocusDirection.Up)
                                }
                            } else {
                                focusManager.clearFocus()
                            }
                        }),
                        singleLine = true,
                        onClickRemoveRequest = {
                            teacherRoleState = teacherRoleState.copy(
                                value = "",
                                isValid = false,
                                isError = false,
                                errorMessage = "",
                            )
                        },
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
                            coroutineScope.launch {
                                delay(100)
                                focusManager.moveFocus(FocusDirection.Up)
                            }
                        } else {
                            focusManager.clearFocus()
                        }
                    }),
                    onClickRemoveRequest = {
                        nameState = nameState.copy(
                            value = "",
                            isValid = false,
                            isError = false,
                            errorMessage = "",
                        )
                    },
                    singleLine = true,
                )
                DodamButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                        )
                        .padding(top = 24.dp),
                    onClick = {
                        if (buttonText == "다음") {
                            onNextClick(
                                nameState.value,
                                teacherRoleState.value,
                                emailState.value,
                                phoneNumberState.value,
                                extensionNumberState.value,
                            )
                        } else if (buttonText == "인증") {
                            viewModel.verifyAuthCode(
                                type = "PHONE",
                                identifier = phoneNumberState.value,
                                authCode = phoneCodeState.value,
                                userAgent = getProductName(),
                            )
                        } else {
                            viewModel.getAuthCode(
                                type = "PHONE",
                                identifier = phoneNumberState.value,
                            )
                        }
                    },
                    enabled = buttonEnabled,
                    text = buttonText,
                    buttonSize = ButtonSize.Large,
                    buttonRole = ButtonRole.Primary,
                )
            }
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

private fun checkPhoneCodeStateValid(phoneCode: TextFieldState): TextFieldState {
    return if (phoneCode.value.length == 6) {
        TextFieldState(
            value = phoneCode.value,
            isValid = true,
            isError = false,
            errorMessage = "",
        )
    } else {
        TextFieldState(
            value = phoneCode.value,
            isValid = false,
            isError = true,
            errorMessage = "인증번호를 입력해주세요",
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
