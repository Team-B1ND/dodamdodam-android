package com.b1nd.dodam.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.common.addFocusCleaner
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
    var isNameValid by remember {
        mutableStateOf(false)
    }
    var titleText by remember {
        if (isNameValid) {
            mutableStateOf("학반번호를 입력해주세요.")
        } else {
            mutableStateOf("이름을 입력해주세요.")
        }
    }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .addFocusCleaner(focusManager)
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
                text = titleText,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            AnimatedVisibility(visible = isNameValid) {
                DodamTextField(
                    value = classInfo,
                    onValueChange = { classInfo = it },
                    onClickCancel = { classInfo = "" },
                    hint = "학반번호",

                    )
            }
            DodamTextField(
                value = name,
                onValueChange = { name = it },
                onClickCancel = { name = "" },
                hint = "이름",
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus()
                }),
            )
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
