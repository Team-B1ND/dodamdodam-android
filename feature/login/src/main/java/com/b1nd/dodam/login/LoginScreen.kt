package com.b1nd.dodam.login

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.b1nd.dodam.designsystem.component.DodamFullWidthButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.theme.BackIcon
import com.b1nd.dodam.designsystem.theme.DodamTheme
import com.b1nd.dodam.login.viewmodel.Event
import com.b1nd.dodam.login.viewmodel.LoginViewModel

@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    navigateToMain: () -> Unit
) {
    var idError by remember { mutableStateOf("") }
    var pwError by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is Event.NavigateToMain -> navigateToMain()
                is Event.Error -> {
                    when (event.message) {
                        "해당 멤버를 찾지 못함" -> {
                            idError = "계정을 찾을 수 없어요"
                        }

                        "비밀번호가 일치하지 않음" -> {
                            pwError = "비밀번호가 일치하지 않아요"
                        }

                        else -> {
                            pwError = event.message
                        }
                    }
                }
            }
        }
    }
    LoginScreen(
        onBackClick = onBackClick,
        onLoginClick = {
            viewModel.login(id, password)
        },
        onIdCancel = {
            id = ""
            idError = ""
        },
        onPwCancel = {
            password = ""
            pwError = ""
        },
        onIdChange = {
            id = it
            idError = ""
        },
        onPwChange = {
            password = it
            pwError = ""
        },
        id = id,
        idError = idError,
        pw = password,
        pwError = pwError,
    )
}

@Composable
private fun LoginScreen(
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    id: String,
    idError: String,
    pw: String,
    pwError: String,
    onIdChange: (String) -> Unit,
    onPwChange: (String) -> Unit,
    onIdCancel: () -> Unit,
    onPwCancel: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
    ) {
        BackIcon(
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    onBackClick()
                },
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Column(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 32.dp),
        ) {
            Text(
                text = "아이디와 비밀번호를\n입력해주세요",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 32.dp),
            )
            DodamTextField(
                value = id,
                onValueChange = onIdChange,
                onClickCancel = onIdCancel,
                hint = "아이디",
                isError = idError.isNotBlank(),
                supportingText = idError,
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 32.dp),
            )
            DodamTextField(
                value = pw,
                onValueChange = onPwChange,
                onClickCancel = onPwCancel,
                isPassword = true,
                isPasswordVisible = false,
                isError = pwError.isNotBlank(),
                supportingText = pwError,
                hint = "비밀번호",
                textStyle = MaterialTheme.typography.bodyMedium,
            )
        }
//        TODO: this code is use when making forget password feature
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "비밀번호를 잊으셨나요?",
//                style = MaterialTheme.typography.labelMedium,
//                color = MaterialTheme.colorScheme.tertiary,)
//            Text(text = "비밀번호 재성정",
//                style = MaterialTheme.typography.labelMedium,
//                color = MaterialTheme.colorScheme.onBackground,)
//        }
        DodamFullWidthButton(
            modifier = Modifier.padding(horizontal = 24.dp),
            onClick = onLoginClick,
            text = "로그인",
            enabled = id.isNotEmpty() && pw.isNotEmpty(),
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreview() {
    DodamTheme {
        LoginScreen(
            onBackClick = { },
            onLoginClick = { },
            id = "아이디",
            idError = "",
            pw = "비밀번호",
            pwError = "",
            onIdChange = { },
            onPwChange = { },
            onIdCancel = { },
            onPwCancel = { },
        )
    }
}
