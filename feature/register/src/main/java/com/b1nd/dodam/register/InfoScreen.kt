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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.common.PhoneVisualTransformation
import com.b1nd.dodam.common.addFocusCleaner
import com.b1nd.dodam.designsystem.component.DodamFullWidthButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.theme.BackIcon
import com.b1nd.dodam.designsystem.theme.DodamTheme
import com.b1nd.dodam.register.state.TextFieldState

@Composable
fun InfoScreen(onBackClick: () -> Unit, onNextClick: () -> Unit) {
    var nameState by remember {
        mutableStateOf(TextFieldState())
    }
    var classInfoState by remember {
        mutableStateOf(TextFieldState())
    }
    var classInfoText by remember {
        mutableStateOf(TextFieldValue())
    }
    var emailState by remember {
        mutableStateOf(TextFieldState())
    }
    var phoneNumber by remember {
        mutableStateOf(TextFieldState())
    }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .addFocusCleaner(
                focusManager = focusManager,
                doOnClear = {
                    if (nameState.value.isNotEmpty()) {
                        nameState = isNameValid(nameState)
                    }
                    if (classInfoState.value.isNotEmpty()) {
                        classInfoState = isClassInfoValid(classInfoState)
                    }
                    if (emailState.value.isNotEmpty()) {
                        emailState = isEmailValid(emailState)
                    }
                    if (phoneNumber.value.isNotEmpty()) {
                        phoneNumber = isPhoneNumberValid(phoneNumber)
                    }
                }
            )
    )
    {
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
                text = if (emailState.isValid && classInfoState.isValid && nameState.isValid) {
                    "전화번호를 입력해주세요"
                } else if (classInfoState.isValid && nameState.isValid) {
                    "이메일을 입력해주세요"
                } else if (nameState.isValid) {
                    "학반번호를 입력해주세요"
                } else {
                    "이름을 입력해주세요"
                },
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
            AnimatedVisibility(visible = emailState.isValid && classInfoState.isValid && nameState.isValid) {
                DodamTextField(
                    value = phoneNumber.value,
                    onValueChange = {
                        if (it.length <= 11) {
                            phoneNumber = TextFieldState(it, phoneNumber.isValid, false, "")
                        }
                        if (it.length == 11) {
                            phoneNumber = isPhoneNumberValid(phoneNumber)
                            Log.d("InfoScreen", "isNameValid: ${nameState.isValid} isClassInfoValid: ${classInfoState.isValid} isEmailValid: ${emailState.isValid} isPhoneNumberValid: ${phoneNumber.isValid}")
                            focusManager.clearFocus()
                        }
                    },
                    onClickCancel = { phoneNumber = TextFieldState() },
                    hint = "전화번호",
                    visualTransformation = PhoneVisualTransformation("000-0000-0000", '0'),
                    modifier = Modifier.padding(top = 24.dp),
                    isError = phoneNumber.isError,
                    supportingText = if (phoneNumber.isError) phoneNumber.errorMessage else "",
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Number,
                    ),
                    keyboardActions = KeyboardActions(onSearch = {
                        phoneNumber = isPhoneNumberValid(phoneNumber)
                        focusManager.clearFocus()
                    }),
                )
            }
            AnimatedVisibility(visible = classInfoState.isValid && nameState.isValid) {
                DodamTextField(
                    value = emailState.value,
                    onValueChange = {
                        emailState = TextFieldState(it, emailState.isValid, false, "")
                    },
                    onClickCancel = { emailState = TextFieldState() },
                    hint = "이메일",
                    modifier = Modifier.padding(top = 24.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        focusManager.clearFocus()
                        emailState = isEmailValid(emailState)
                    }),
                )
            }
            AnimatedVisibility(visible = nameState.isValid) {
                DodamTextField(
                    value = classInfoText,
                    onValueChange = {
                        classInfoState = TextFieldState(
                            it.text.replace("[^0-9]".toRegex(), ""),
                            classInfoState.isValid,
                            classInfoState.isError
                        )
                        when (it.text.length) {
                            1 -> { // 학년을 입력한 경우: "" (0글자)
                                classInfoText = TextFieldValue(
                                    text = classInfoState.value + "학년",
                                    selection = TextRange(3)
                                )
                                classInfoState =
                                    TextFieldState(classInfoState.value, false, false, "")
                            }

                            2 -> { // 학년이 입력되었을 때 삭제를 누른 경우: 2학년 (3글자)
                                classInfoText = TextFieldValue(
                                    text = "",
                                    selection = TextRange.Zero
                                )
                                classInfoState = TextFieldState()
                            }

                            4 -> { // 학년이 입력되었을 때 반을 입력한 경우: 2학년 (3글자)
                                classInfoText = TextFieldValue(
                                    text = classInfoState.value[0] + "학년 " + classInfoState.value[1] + "반",
                                    selection = TextRange(6)
                                )
                                classInfoState = TextFieldState(
                                    classInfoState.value.substring(0, 2),
                                    isValid = false,
                                    isError = false,
                                    errorMessage = ""
                                )
                            }

                            5 -> { // 학년, 반이 입력되었을 때 삭제를 누른 경우: 2학년 4반 (6글자)
                                classInfoText = TextFieldValue(
                                    text = classInfoState.value[0] + "학년",
                                    selection = TextRange(3)
                                )
                                classInfoState =
                                    TextFieldState(
                                        classInfoState.value.substring(0, 1),
                                        isValid = false,
                                        isError = false,
                                        errorMessage = ""
                                    )
                            }

                            7 -> { // 학년, 반이 입력되었을 때 학번을 입력할 경우: 2학년 4반 (6글자)
                                classInfoText = TextFieldValue(
                                    text = classInfoState.value[0] + "학년 " + classInfoState.value[1] + "반 " + classInfoState.value[2] + "번",
                                    selection = TextRange(9)
                                )
                                classInfoState = TextFieldState(
                                    classInfoState.value.substring(0, 3),
                                    isValid = false,
                                    isError = false,
                                    errorMessage = ""
                                )
                            }

                            8 -> { // 학번이 한자리인 상황에서 삭제를 누를 경우: 2학년 4반 6번 (9글자)
                                classInfoText = TextFieldValue(
                                    text = classInfoState.value[0] + "학년 " + classInfoState.value[1] + "반",
                                    selection = TextRange(6)
                                )
                                classInfoState =
                                    TextFieldState(
                                        classInfoState.value.substring(0, 2),
                                        isValid = false,
                                        isError = false,
                                        errorMessage = ""
                                    )
                            }

                            9 -> { // 학번이 두자리인 상황에서 삭제를 누를 경우: 2학년 4반 06번 (10글자)
                                if (classInfoState.value[2] == '0') { // 학번의 십의자리가 0일 떄는 십의자리가 사리지고 아니면 일의자리가 사라짐
                                    classInfoText = TextFieldValue(
                                        text = classInfoState.value[0] + "학년 " + classInfoState.value[1] + "반 " + classInfoState.value[3] + "번",
                                        selection = TextRange(9)
                                    )
                                    classInfoState =
                                        TextFieldState(
                                            classInfoState.value.substring(
                                                0,
                                                2
                                            ) + classInfoState.value[3],
                                            isValid = classInfoState.isValid,
                                            isError = false,
                                            errorMessage = ""
                                        )
                                } else {
                                    classInfoText = TextFieldValue(
                                        text = classInfoState.value[0] + "학년 " + classInfoState.value[1] + "반 " + classInfoState.value[2] + "번",
                                        selection = TextRange(9)
                                    )
                                    classInfoState =
                                        TextFieldState(
                                            classInfoState.value.substring(0, 3),
                                            isValid = classInfoState.isValid,
                                            isError = false,
                                            errorMessage = ""
                                        )
                                }
                            }
                            // 학번이 한자리인 경우에서 입력할 경우: 2학년 4반 6번 (9글자)
                            10 -> {
                                classInfoText = TextFieldValue(
                                    text =
                                    classInfoState.value[0] + "학년 " + classInfoState.value[1] + "반 " + classInfoState.value[2] + classInfoState.value[3] + "번",
                                    selection = TextRange(10)
                                )
                                classInfoState = isClassInfoValid(classInfoState)
                                focusManager.clearFocus(true)
                            }
                        }
                        Log.d("InfoScreen: ", "classInfo: $classInfoState  it: ${it.text}")
                    },
                    onClickCancel = {
                        classInfoText = TextFieldValue()
                        classInfoState = TextFieldState()
                    },
                    hint = "학반번호",
                    isError = classInfoState.isError,
                    supportingText = if (classInfoState.isError) classInfoState.errorMessage else "",
                    modifier = Modifier.padding(top = 24.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(onSearch = {
                        focusManager.clearFocus()
                        classInfoState = isClassInfoValid(classInfoState)
                    }),
                )
            }
            DodamTextField(
                value = nameState.value,
                onValueChange = { text ->
                    nameState = TextFieldState(text, nameState.isValid, false, "")
                },
                onClickCancel = { nameState = TextFieldState() },
                hint = "이름",
                isError = nameState.isError,
                supportingText = if (nameState.isError) nameState.errorMessage else "",
                modifier = Modifier.padding(top = 24.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus()
                    nameState = isNameValid(nameState)
                }),
            )
            AnimatedVisibility(visible = phoneNumber.isValid && emailState.isValid && classInfoState.isValid && nameState.isValid) {
                DodamFullWidthButton(
                    onClick = { onNextClick() },
                    text = "다음",
                    modifier = Modifier.padding(top = 24.dp),
                    enabled = nameState.value.length in 2..4 &&
                            classInfoState.value.length == 4 &&
                            emailState.value.isNotBlank() &&
                            phoneNumber.value.length == 11
                )
            }
        }
    }
}

private fun isNameValid(nameState: TextFieldState): TextFieldState {
    return if (nameState.value.length in 2..4) {
        TextFieldState(
            nameState.value,
            isValid = true,
            isError = false,
            errorMessage = ""
        )
    } else {
        TextFieldState(
            nameState.value,
            isValid = false,
            isError = true,
            errorMessage = "이름은 2~4글자로 입력해주세요"
        )
    }
}

private fun isClassInfoValid(classInfoState: TextFieldState): TextFieldState {
    Log.d("isClassInfoValid", "value : ${classInfoState.value}")
    return when (classInfoState.value.length) {
        in 3..4 -> { // 학 반 번호가 모두 입력되었다면
            if (classInfoState.value[0] !in '1'..'3') { // 학년이 1~3학년이 아니라면
                TextFieldState(
                    classInfoState.value,
                    isValid = false,
                    isError = true,
                    errorMessage = "학년을 다시 확인해주세요."
                )
            }
            if (classInfoState.value[1] !in '1'..'4') { // 학반이 1~4반이 아니라면
                TextFieldState(
                    classInfoState.value,
                    isValid = false,
                    isError = true,
                    errorMessage = "반을 다시 확인해주세요."
                )
            }
            if (classInfoState.value.length == 3) { // 학번을 한글자로 입력했다면
                if (classInfoState.value[2] in '1'..'9') { // 학번이 1~9번인가?
                    TextFieldState(
                        classInfoState.value.substring(
                            0,
                            2
                        ) + "0" + classInfoState.value[2],
                        isValid = true,
                        isError = false,
                        errorMessage = ""
                    )
                } else { // 학번이 1~9사이가 아님
                    TextFieldState(
                        classInfoState.value,
                        isValid = false,
                        isError = true,
                        errorMessage = "번호를 다시 확인해주세요."
                    )
                }
            } else { // 학번을 4글자로 입력했다면
                if ((classInfoState.value[2].toString() +
                            classInfoState.value[3].toString())
                        .toInt() in 1..25
                ) { // 학번이 1~25 사이인가
                    TextFieldState(
                        classInfoState.value,
                        isValid = true,
                        isError = false,
                        errorMessage = ""
                    )
                } else { // 학번이 1~25 사이가 아니라면
                    TextFieldState(
                        classInfoState.value,
                        isValid = false,
                        isError = true,
                        errorMessage = "번호를 다시 확인해주세요."
                    )
                }
            }
        }
        2 -> { // 번호가 입력되지 않았다면
            TextFieldState(
                classInfoState.value,
                isValid = false,
                isError = true,
                errorMessage = "번호를 입력해주세요"
            )
        }

        1 -> { // 반 번호가 입력되지 않았다면
            TextFieldState(
                classInfoState.value,
                isValid = false,
                isError = true,
                errorMessage = "반 번호를 입력해주세요"
            )
        }

        else -> { // 아무것도 입력되지 않았다면
            TextFieldState(
                classInfoState.value,
                isValid = false,
                isError = true,
                errorMessage = "학년 반 번호를 입력해주세요"
            )
        }
    }
}

private fun isEmailValid(emailState: TextFieldState): TextFieldState {
    return if (emailState.value.isNotBlank()) {
        TextFieldState(
            emailState.value,
            isValid = true,
            isError = false,
            errorMessage = ""
        )
    } else {
        TextFieldState(
            emailState.value,
            isValid = false,
            isError = true,
            errorMessage = "이메일을 입력해주세요"
        )
    }
}

private fun isPhoneNumberValid(phoneNumber: TextFieldState): TextFieldState {
    Log.d("InfoScreen", "phoneNumber: ${phoneNumber.value}")
    return if (phoneNumber.value.length == 11) {
        TextFieldState(
            phoneNumber.value,
            isValid = true,
            isError = false,
            errorMessage = ""
        )
    } else {
        TextFieldState(
            phoneNumber.value,
            isValid = false,
            isError = true,
            errorMessage = "전화번호를 입력해주세요"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InfoScreenPreview() {
    DodamTheme {
        InfoScreen(onBackClick = {}, onNextClick = {})
    }
}
