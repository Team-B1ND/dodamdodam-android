package com.b1nd.dodam.register

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.data.core.model.Children
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.register.state.InfoSideEffect
import com.b1nd.dodam.register.state.TextFieldState
import com.b1nd.dodam.register.viewmodel.InfoViewModel
import com.b1nd.dodam.ui.util.PhoneVisualTransformation
import com.b1nd.dodam.ui.util.addFocusCleaner
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@Composable
internal fun InfoScreen(
    viewModel: InfoViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onNextClick: (
        name: String,
        grade: String,
        room: String,
        number: String,
        email: String,
        phoneNumber: String,
        childrenList: List<Children>,
    ) -> Unit,
    childrenList: List<Children>,
    showToast: (String, String) -> Unit
) {
    var nameState by remember { mutableStateOf(TextFieldState()) }
    var phoneNumberState by remember { mutableStateOf(TextFieldState()) }
    var phoneCodeState by remember { mutableStateOf(TextFieldState()) }
    var emailState by remember { mutableStateOf(TextFieldState()) }
    var emailCodeState by remember { mutableStateOf(TextFieldState()) }
    var classInfoState by remember { mutableStateOf(TextFieldState()) }
    var classInfoText by remember { mutableStateOf(TextFieldValue()) }
    val focusManager = LocalFocusManager.current

    var role by remember { mutableStateOf("STUDENT") }
    var buttonText by remember { mutableStateOf("전화번호 인증코드 전송") }
    var authType by remember { mutableStateOf("PHONE") }

    var showPhoneCodeTextField by remember { mutableStateOf(false) }
    var showEmailCodeTextField by remember { mutableStateOf(false) }
    var showEmailTextField by remember { mutableStateOf(false) }
    var buttonEnabled by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.sideEffect.collect {
            when (it) {
                is InfoSideEffect.NavigateToAuth -> {
                    if (role == "PARENT") {
                        onNextClick(
                            nameState.value,
                            "",
                            "",
                            "",
                            "",
                            phoneNumberState.value,
                            childrenList,
                        )
                    } else {
                        onNextClick(
                            nameState.value,
                            classInfoState.value[0].toString(),
                            classInfoState.value[1].toString(),
                            classInfoState.value[2].toString() + classInfoState.value[3].toString(),
                            emailState.value,
                            phoneNumberState.value,
                            childrenList,
                        )
                    }
                }

                is InfoSideEffect.SuccessGetAuthPhoneCode -> {
                    buttonText = "인증"
                    showPhoneCodeTextField = true
                    buttonEnabled = false
                }

                is InfoSideEffect.SuccessGetAuthEmailCode -> {
                    buttonText = "인증"
                    showEmailCodeTextField = true
                }

                is InfoSideEffect.SuccessVerifyAuthPhoneCode -> {
                    buttonText = "이메일 인증코드 전송"
                    buttonEnabled = false
                    showEmailTextField = true
                    phoneCodeState = TextFieldState(
                        value = phoneCodeState.value,
                        isValid = true,
                    )
                    authType = "EMAIL"
                }

                is InfoSideEffect.FiledVerifyAuthCode -> {
                    if (it.type == "PHONE") {
                        phoneCodeState = TextFieldState(
                            value = phoneCodeState.value,
                            isValid = false,
                            isError = true,
                            errorMessage = "인증번호가 틀렸습니다.",
                        )
                    } else if (it.type == "EMAIL") {
                        emailCodeState = TextFieldState(
                            value = emailCodeState.value,
                            isValid = false,
                            isError = true,
                            errorMessage = "인증번호가 틀렸습니다.",
                        )
                    }
                }
                is InfoSideEffect.FiledGetAuthCode ->{
                    showToast("ERROR", "인증코드 발급에 실패했습니다.")
                }
            }
        }
    }
    LaunchedEffect(classInfoState.isValid) {
        if (classInfoState.isValid) {
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
    LaunchedEffect(phoneCodeState.value) {
        if (phoneCodeState.value.length == 6) {
            buttonEnabled = true
        }
    }
    LaunchedEffect(emailCodeState.value) {
        if (emailCodeState.value.length == 6) {
            buttonEnabled = true
        }
    }
    LaunchedEffect(phoneNumberState.value) {
        if (phoneNumberState.value.length == 11) {
            buttonEnabled = true
        }
    }

    LaunchedEffect(emailState.value) {
        if (emailState.value.isNotEmpty()) {
            buttonEnabled = true
        }
    }

    LaunchedEffect(true) {
        role = if (childrenList.isNotEmpty()) "PARENT" else "STUDENT"
    }

    Scaffold(
        modifier = Modifier
            .background(DodamTheme.colors.backgroundNeutral)
            .statusBarsPadding()
            .imePadding(),
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.addFocusCleaner(
                    focusManager = focusManager,
                    doOnClear = {
                        if (nameState.value.isNotEmpty()) {
                            nameState = checkNameStateValid(nameState)
                        }
                        if (classInfoState.value.isNotEmpty()) {
                            focusManager.moveFocus(FocusDirection.Down)
                            classInfoState = checkClassInfoStateValid(classInfoState)
                        }
                        if (emailState.value.isNotEmpty()) {
                            emailState = checkEmailStateValid(emailState)
                        }
                        if (phoneNumberState.value.isNotEmpty()) {
                            phoneNumberState = checkPhoneNumberStateValid(phoneNumberState)
                        }
                    },
                ),
                title = when {
                    role == "PARENT" -> when {
                        setOf(
                            nameState,
                            phoneNumberState,
                        ).all { it.isValid } -> "인증번호를\n입력해주세요"
                        nameState.isValid -> "전화번호를\n입력해주세요"
                        else -> "이름을\n입력해주세요"
                    }

                    else -> when {
                        setOf(
                            nameState,
                            phoneNumberState,
                            classInfoState,
                            phoneCodeState,
                            emailState,
                        ).all { it.isValid } -> "인증번호를\n입력해주세요"
                        setOf(
                            nameState,
                            phoneNumberState,
                            classInfoState,
                            phoneCodeState,
                        ).all { it.isValid } -> "이메일을\n입력해주세요"
                        setOf(
                            nameState,
                            classInfoState,
                            phoneNumberState,
                        ).all { it.isValid } -> "인증번호를\n입력해주세요"
                        setOf(nameState, classInfoState).all { it.isValid } -> "전화번호를\n입력해주세요"
                        nameState.isValid -> "학반번호를\n입력해주세요"
                        else -> "이름을\n입력해주세요"
                    }
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
                        if (nameState.value.isNotEmpty()) {
                            nameState = checkNameStateValid(nameState)
                        }
                        if (classInfoState.value.isNotEmpty()) {
                            focusManager.moveFocus(FocusDirection.Down)
                            classInfoState = checkClassInfoStateValid(classInfoState)
                        }
                        if (emailState.value.isNotEmpty()) {
                            emailState = checkEmailStateValid(emailState)
                        }
                        if (phoneNumberState.value.isNotEmpty()) {
                            phoneNumberState = checkPhoneNumberStateValid(phoneNumberState)
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
                AnimatedVisibility(
                    visible = showEmailCodeTextField,
                ) {
                    DodamTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                emailCodeState = emailCodeState.copy(focused = it.isFocused)
                            },
                        value = emailCodeState.value,
                        onValueChange = { text ->
                            emailCodeState = emailCodeState.copy(
                                value = text,
                                isValid = emailCodeState.isValid,
                                isError = false,
                                errorMessage = "",
                            )
                        },
                        label = "이메일 인증코드",
                        isError = emailCodeState.isError,
                        onClickRemoveRequest = {
                            emailCodeState = emailCodeState.copy(value = "")
                        },
                        supportText = if (emailCodeState.isError) emailCodeState.errorMessage else "",
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number,
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.clearFocus()
                        }),
                        singleLine = true,
                    )
                }

                AnimatedVisibility(visible = showEmailTextField) {
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
                        onClickRemoveRequest = {
                            emailState = emailState.copy(value = "")
                        },
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

                AnimatedVisibility(
                    visible = showPhoneCodeTextField,
                ) {
                    DodamTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                phoneCodeState = phoneCodeState.copy(focused = it.isFocused)
                            },
                        value = phoneCodeState.value,
                        onValueChange = { text ->
                            phoneCodeState = phoneCodeState.copy(
                                value = text,
                                isValid = phoneCodeState.isValid,
                                isError = false,
                                errorMessage = "",
                            )
                        },
                        label = "전화번호 인증코드",
                        isError = phoneCodeState.isError,
                        onClickRemoveRequest = {
                            phoneCodeState = phoneCodeState.copy(value = "")
                        },
                        supportText = if (phoneCodeState.isError) phoneCodeState.errorMessage else "",
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number,
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.clearFocus()
                        }),
                        singleLine = true,
                    )
                }

                AnimatedVisibility(
                    visible =
                    role == "STUDENT" && setOf(
                        nameState,
                        classInfoState,
                    ).all { it.isValid } || role == "PARENT" && nameState.isValid,
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
                        onClickRemoveRequest = {
                            phoneNumberState = phoneNumberState.copy(value = "")
                        },
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

                AnimatedVisibility(visible = nameState.isValid && role == "STUDENT") {
                    DodamTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                classInfoState = classInfoState.copy(focused = it.isFocused)
                            },
                        value = classInfoText,
                        onValueChange = {
                            if (it.text.length <= 10) {
                                classInfoState = classInfoState.copy(
                                    value = it.text.replace("[^0-9]".toRegex(), ""),
                                    isValid = classInfoState.isValid,
                                    isError = classInfoState.isError,
                                )
                            }
                            when (it.text.length) {
                                1 -> { // 학년을 입력한 경우: "" (0글자)
                                    classInfoText = TextFieldValue(
                                        text = classInfoState.getValueAsString(1),
                                        selection = TextRange(3),
                                    )
                                    classInfoState =
                                        classInfoState.copy(
                                            value = classInfoText.text[0].toString(),
                                            isValid = false,
                                            isError = false,
                                            errorMessage = "",
                                        )
                                }

                                2 -> { // 학년이 입력되었을 때 삭제를 누른 경우: 2학년 (3글자)
                                    classInfoText = classInfoText.copy(
                                        text = "",
                                        selection = TextRange.Zero,
                                    )
                                    classInfoState = classInfoState.copy(
                                        value = "",
                                        isValid = false,
                                        isError = false,
                                        errorMessage = "",
                                    )
                                }

                                4 -> { // 학년이 입력되었을 때 반을 입력한 경우: 2학년 (3글자)
                                    classInfoText = classInfoText.copy(
                                        text = classInfoState.getValueAsString(2),
                                        selection = TextRange(6),
                                    )
                                    classInfoState = classInfoState.copy(
                                        value = classInfoState.value.substring(0, 2),
                                        isValid = false,
                                        isError = false,
                                        errorMessage = "",
                                    )
                                }

                                5 -> { // 학년, 반이 입력되었을 때 삭제를 누른 경우: 2학년 4반 (6글자)
                                    classInfoText = classInfoText.copy(
                                        text = classInfoState.getValueAsString(1),
                                        selection = TextRange(3),
                                    )
                                    classInfoState =
                                        classInfoState.copy(
                                            value = classInfoState.value.substring(0, 1),
                                            isValid = false,
                                            isError = false,
                                            errorMessage = "",
                                        )
                                }

                                7 -> { // 학년, 반이 입력되었을 때 학번을 입력할 경우: 2학년 4반 (6글자)
                                    classInfoText = classInfoText.copy(
                                        text = classInfoState.getValueAsString(),
                                        selection = TextRange(9),
                                    )
                                    classInfoState = classInfoState.copy(
                                        value = classInfoState.value.substring(0, 3),
                                        isValid = false,
                                        isError = false,
                                        errorMessage = "",
                                    )
                                }

                                8 -> { // 학번이 한자리인 상황에서 삭제를 누를 경우: 2학년 4반 6번 (9글자)
                                    classInfoText = classInfoText.copy(
                                        text = classInfoState.getValueAsString(2),
                                        selection = TextRange(6),
                                    )
                                    classInfoState =
                                        classInfoState.copy(
                                            value = classInfoState.value.substring(0, 2),
                                            isValid = false,
                                            isError = false,
                                            errorMessage = "",
                                        )
                                }

                                9 -> { // 학번이 두자리인 상황에서 삭제를 누를 경우: 2학년 4반 06번 (10글자)
                                    if (classInfoState.value[2] == '0') { // 학번의 십의자리가 0일 떄는 십의자리가 사리지고 아니면 일의자리가 사라짐
                                        classInfoText = classInfoText.copy(
                                            text = classInfoState.getValueAsString(lastIndex = 3),
                                            selection = TextRange(9),
                                        )
                                        classInfoState =
                                            classInfoState.copy(
                                                value = classInfoState.value.substring(
                                                    0,
                                                    2,
                                                ) + classInfoState.value[3],
                                                isValid = classInfoState.isValid,
                                                isError = false,
                                                errorMessage = "",
                                            )
                                    } else {
                                        classInfoText = classInfoText.copy(
                                            text = classInfoState.getValueAsString(),
                                            selection = TextRange(9),
                                        )
                                        classInfoState =
                                            classInfoState.copy(
                                                value = classInfoState.value.substring(0, 3),
                                                isValid = classInfoState.isValid,
                                                isError = false,
                                                errorMessage = "",
                                            )
                                    }
                                }
                                // 학번이 한자리인 경우에서 입력할 경우: 2학년 4반 6번 (9글자)
                                10 -> {
                                    classInfoText = classInfoText.copy(
                                        text = classInfoState.getValueAsString(
                                            lastIndex = 3,
                                            lastPrefix = 2,
                                        ),
                                        selection = TextRange(10),
                                    )
                                    classInfoState = checkClassInfoStateValid(classInfoState)
                                }

                                11 -> {
                                    if (classInfoState.isValid) {
                                        focusManager.moveFocus(FocusDirection.Up)
                                    }
                                }
                            }
                        },
                        label = "학반번호",
                        isError = classInfoState.isError,
                        onClickRemoveRequest = {
                            classInfoState = classInfoState.copy(value = "")
                        },
                        supportText = if (classInfoState.isError) classInfoState.errorMessage else "",
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number,
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            classInfoState = checkClassInfoStateValid(classInfoState)
                            if (classInfoState.isValid) {
                                focusManager.moveFocus(FocusDirection.Up)
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
                    onClickRemoveRequest = {
                        nameState = nameState.copy(value = "")
                    },
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

                DodamButton(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(),
                    onClick = {
                        if (emailState.value.isNotEmpty()) {
                            val updatedEmailState = checkEmailStateValid(emailState)
                            emailState = updatedEmailState
                            if (updatedEmailState.isError) {
                                return@DodamButton
                            }
                        }
                        if (buttonText == "인증") {
                            viewModel.verifyAuthCode(
                                type = authType,
                                identifier = if (authType == "PHONE") phoneNumberState.value else emailState.value,
                                authCode = if (authType == "PHONE") phoneCodeState.value else emailCodeState.value,
                                userAgent = Build.PRODUCT,
                                role = role,
                            )
                        } else {
                            viewModel.getAuthCode(
                                type = authType,
                                identifier = if (authType == "PHONE") phoneNumberState.value else emailState.value,
                            )
                        }
                    },
                    enabled = buttonEnabled,
                    text = buttonText,
                    buttonRole = ButtonRole.Primary,
                    buttonSize = ButtonSize.Large,
                    loading = uiState.isLoading
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

private fun checkClassInfoStateValid(classInfoState: TextFieldState): TextFieldState {
    return when (classInfoState.value.length) {
        in 3..4 -> { // 학 반 번호가 모두 입력되었다면
            if (classInfoState.value[0].toString().toInt() !in 1..3) { // 학년이 1~3학년이 아니라면
                TextFieldState(
                    value = classInfoState.value,
                    isValid = false,
                    isError = true,
                    errorMessage = "학년을 다시 확인해주세요.",
                )
            }
            if (classInfoState.value[1] !in '1'..'4') { // 학반이 1~4반이 아니라면
                TextFieldState(
                    value = classInfoState.value,
                    isValid = false,
                    isError = true,
                    errorMessage = "반을 다시 확인해주세요.",
                )
            }
            if (classInfoState.value.length == 3) { // 학번을 한글자로 입력했다면
                if (classInfoState.value[2] in '1'..'9') { // 학번이 1~9번인가?
                    TextFieldState(
                        value = classInfoState.value.substring(
                            0,
                            2,
                        ) + "0" + classInfoState.value[2],
                        isValid = true,
                        isError = false,
                        errorMessage = "",
                    )
                } else { // 학번이 1~9사이가 아님
                    TextFieldState(
                        value = classInfoState.value,
                        isValid = false,
                        isError = true,
                        errorMessage = "번호를 다시 확인해주세요.",
                    )
                }
            } else { // 학번을 4글자로 입력했다면
                if ((
                        classInfoState.value[2].toString() +
                            classInfoState.value[3].toString()
                        )
                        .toInt() in 1..25
                ) { // 학번이 1~25 사이인가
                    TextFieldState(
                        value = classInfoState.value,
                        isValid = true,
                        isError = false,
                        errorMessage = "",
                    )
                } else { // 학번이 1~25 사이가 아니라면
                    TextFieldState(
                        value = classInfoState.value,
                        isValid = false,
                        isError = true,
                        errorMessage = "번호를 다시 확인해주세요.",
                    )
                }
            }
        }

        1 -> { // 반 번호가 입력되지 않았다면
            TextFieldState(
                value = classInfoState.value,
                isValid = false,
                isError = true,
                errorMessage = "반 번호를 입력해주세요",
            )
        }

        2 -> { // 번호가 입력되지 않았다면
            TextFieldState(
                value = classInfoState.value,
                isValid = false,
                isError = true,
                errorMessage = "번호를 입력해주세요",
            )
        }

        else -> { // 아무것도 입력되지 않았다면
            TextFieldState(
                value = classInfoState.value,
                isValid = false,
                isError = true,
                errorMessage = "학년 반 번호를 입력해주세요",
            )
        }
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
