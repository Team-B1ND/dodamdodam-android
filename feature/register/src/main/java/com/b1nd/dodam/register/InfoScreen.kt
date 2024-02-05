package com.b1nd.dodam.register

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.common.PhoneVisualTransformation
import com.b1nd.dodam.common.addFocusCleaner
import com.b1nd.dodam.designsystem.component.DodamFullWidthButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.theme.BackIcon
import com.b1nd.dodam.designsystem.theme.DodamTheme

@Composable
fun InfoScreen(onBackClick: () -> Unit, onNextClick: () -> Unit) {
    var name by remember {
        mutableStateOf("")
    }
    var classInfo by remember {
        mutableStateOf("")
    }
    var classInfoText by remember {
        mutableStateOf(TextFieldValue())
    }
    var email by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var isNameValid by remember {
        mutableStateOf(false)
    }
    var isClassInfoValid by remember {
        mutableStateOf(false)
    }
    var isEmailValid by remember {
        mutableStateOf(false)
    }
    var isPhoneNumberValid by remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager, doOnClear = {
                if (!isNameValid && name != "") {
                    isNameValid = true
                }
                if (!isClassInfoValid && classInfo != "") {
                    isClassInfoValid = true
                }
                if (!isEmailValid && email != "") {
                    isEmailValid = true
                }
                if (!isPhoneNumberValid && phoneNumber != "") {
                    isPhoneNumberValid = true
                }
            })
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
                text = if (isEmailValid) {
                    "전화번호를 입력해주세요"
                } else if (isClassInfoValid) {
                    "이메일을 입력해주세요"
                } else if (isNameValid) {
                    "학반번호를 입력해주세요"
                } else {
                    "이름을 입력해주세요"
                },
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            AnimatedVisibility(visible = isEmailValid) {
                DodamTextField(
                    value = phoneNumber,
                    onValueChange = {
                        if (it.length == 13) {
                            focusManager.clearFocus()
                        }
                        phoneNumber = it
                    },
                    onClickCancel = { phoneNumber = "" },
                    hint = "전화번호",
                    visualTransformation = PhoneVisualTransformation("000-0000-0000", '0'),
                    modifier = Modifier.padding(top = 24.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Number,
                    ),
                    keyboardActions = KeyboardActions(onSearch = {
                        focusManager.clearFocus()
                        isPhoneNumberValid = true
                    }),
                )
            }
            AnimatedVisibility(visible = isClassInfoValid) {
                DodamTextField(
                    value = email,
                    onValueChange = { email = it },
                    onClickCancel = { email = "" },
                    hint = "이메일",
                    modifier = Modifier.padding(top = 24.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        focusManager.clearFocus()
                        isEmailValid = true
                    }),
                )
            }
            AnimatedVisibility(visible = isNameValid) {
                DodamTextField(
                    value = classInfoText,
                    onValueChange = {
                        classInfo = it.text.replace("[^0-9]".toRegex(), "")
                        Log.d("InfoScreen: ", "classInfo: $classInfo  it: ${it.text}")
                        when (it.text.length) {
                            1 -> {
                                classInfoText = TextFieldValue(
                                    text = classInfo + "학년",
                                    selection = TextRange(3)
                                )
                            }

                            2 -> {
                                classInfoText = TextFieldValue(
                                    text = "",
                                    selection = TextRange.Zero
                                )
                                classInfo = ""
                            }

                            4 -> {
                                classInfoText = TextFieldValue(
                                    text = classInfo[0] + "학년 " + classInfo[1] + "반",
                                    selection = TextRange(6)
                                )
                            }

                            5 -> {
                                classInfoText = TextFieldValue(
                                    text = classInfo[0] + "학년",
                                    selection = TextRange(3)
                                )
                            }

                            7 -> {
                                classInfoText = TextFieldValue(
                                    text = classInfo[0] + "학년 " + classInfo[1] + "반 " + classInfo[2] + "번",
                                    selection = TextRange(9)
                                )
                            }

                            8 -> {
                                classInfoText = TextFieldValue(
                                    text = classInfo[0] + "학년 " + classInfo[1] + "반",
                                    selection = TextRange(6)
                                )
                                isClassInfoValid = true
                            }

                            9 -> {
                                classInfoText = TextFieldValue(
                                    text = classInfo[0] + "학년 " + classInfo[1] + "반 " + classInfo[3] + "번",
                                    selection = TextRange(9)
                                )
                            }

                            10 -> {
                                classInfoText = TextFieldValue(
                                    text =
                                    classInfo[0] + "학년 " + classInfo[1] + "반 " + classInfo[2] + classInfo[3] + "번",
                                    selection = TextRange(10)
                                )
                                focusManager.clearFocus(true)
                                isClassInfoValid = true
                            }
                        }
                    },
                    onClickCancel = {
                        classInfoText = TextFieldValue()
                        classInfo = ""
                    },
                    hint = "학반번호",
                    modifier = Modifier.padding(top = 24.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(onSearch = {
                        focusManager.clearFocus()
                        isClassInfoValid = true
                    }),
                )
            }
            DodamTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                onClickCancel = { name = "" },
                hint = "이름",
                modifier = Modifier.padding(top = 24.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus()
                    isNameValid = true
                }),
            )
            AnimatedVisibility(visible = isPhoneNumberValid) {
                DodamFullWidthButton(
                    onClick = { onNextClick() },
                    text = "다음",
                    modifier = Modifier.padding(top = 24.dp),
                    enabled = name.isNotBlank() && classInfo.isNotBlank() && email.isNotBlank() && phoneNumber.isNotBlank()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InfoScreenPreview() {
    DodamTheme {
        InfoScreen(onBackClick = {}, onNextClick = {})
    }
}
