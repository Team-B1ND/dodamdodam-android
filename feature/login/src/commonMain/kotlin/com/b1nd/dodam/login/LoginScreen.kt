package com.b1nd.dodam.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDialog
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import kotlinx.coroutines.launch
import com.b1nd.dodam.login.viewmodel.Event
import com.b1nd.dodam.login.viewmodel.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel

@ExperimentalMaterial3Api
@Composable
internal fun LoginScreen(viewModel: LoginViewModel = koinViewModel(), onBackClick: () -> Unit, navigateToMain: () -> Unit, role: String) {
    val uiState by viewModel.uiState.collectAsState()

    var idError by remember { mutableStateOf("") }
    var pwError by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is Event.NavigateToMain -> navigateToMain()
                is Event.ShowDialog -> coroutineScope.launch {
                    showDialog = true
                }

                is Event.CheckId -> idError = event.message
                is Event.CheckPw -> pwError = event.message
            }
        }
    }
    LoginScreen(
        onBackClick = onBackClick,
        onLoginClick = {
            viewModel.login(id, password, role)
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
        isLoading = uiState.isLoading,
        showDialog = showDialog,
        dismissDialog = { showDialog = false },
        errorMessage = uiState.error,
    )
}

@ExperimentalMaterial3Api
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
    isLoading: Boolean,
    showDialog: Boolean,
    dismissDialog: () -> Unit,
    errorMessage: String,
) {
    var idFocused by remember { mutableStateOf(false) }
    var pwFocused by remember { mutableStateOf(false) }

    var showPassword by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(
            onDismissRequest = dismissDialog,
        ) {
            DodamDialog(
                title = errorMessage,
                body = "아직 계정이 승인되지 않았어요.\n" +
                    "승인을 기다려주세요.",
                confirmButton = dismissDialog,
            )
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = dismissDialog,
        ) {
            DodamDialog(
                title = errorMessage,
                confirmButton = dismissDialog,
            )
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
        topBar = {
            DodamTopAppBar(
                title = "아이디와 비밀번호를\n" +
                    "입력해주세요",
                onBackClick = onBackClick,
                type = TopAppBarType.Medium,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            DodamTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        idFocused = it.isFocused
                    },
                value = id,
                onValueChange = onIdChange,
                label = "아이디",
                isError = idError.isNotBlank(),
                onClickRemoveRequest = onIdCancel,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true,
            )

            DodamTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        pwFocused = it.isFocused
                    },
                value = pw,
                onValueChange = onPwChange,
                label = "비밀번호",
                isError = pwError.isNotBlank(),
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                onClickRemoveRequest = onPwCancel,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                singleLine = true,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "비밀번호를 잊으셨나요? ",
                    color = DodamTheme.colors.labelAlternative,
                )
                Text(
                    text = "비밀번호 재설정",
                    color = DodamTheme.colors.labelNormal,
                    style = DodamTheme.typography.labelMedium().copy(
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = TextDecoration.Underline,
                    ),
                )
            }
            DodamButton(
                onClick = onLoginClick,
                text = "로그인",
                enabled = id.isNotBlank() && pw.isNotBlank(),
                loading = isLoading,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
