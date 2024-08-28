package login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDialog
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import login.viewmodel.Event
import login.viewmodel.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel

@ExperimentalMaterial3Api
@Composable
internal fun LoginScreen(viewModel: LoginViewModel = koinViewModel(), onBackClick: () -> Unit, navigateToMain: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    var idError by remember { mutableStateOf("") }
    var pwError by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is Event.NavigateToMain -> navigateToMain()
                is Event.ShowDialog -> showDialog = true
                is Event.CheckId -> idError = event.message
                is Event.CheckPw -> pwError = event.message
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
        DodamDialog(
            title = "d",
            body = "dd",
            confirmButton = {

            }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
        topBar = {
            DodamTopAppBar(
                title = "",
                onBackClick ={}
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
//                supportingText = if (idError.isNotBlank()) {
//                    { Text(text = idError) }
//                } else {
//                    null
//                },
//                trailingIcon = {
//                    if (idFocused) {
//                        XMarkCircleIcon(
//                            modifier = Modifier.clickable {
//                                onIdCancel()
//                            },
//                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
//                        )
//                    }
//                },
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
//                supportingText = if (pwError.isNotBlank()) {
//                    { Text(text = pwError) }
//                } else {
//                    null
//                },
//                trailingIcon = {
//                    if (pwFocused) {
//                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//                            if (showPassword) {
//                                EyeIcon(
//                                    modifier = Modifier.clickable {
//                                        showPassword = false
//                                    },
//                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
//                                )
//                            } else {
//                                EyeSlashIcon(
//                                    modifier = Modifier.clickable {
//                                        showPassword = true
//                                    },
//                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
//                                )
//                            }
//
//                            XMarkCircleIcon(
//                                modifier = Modifier.clickable {
//                                    onPwCancel()
//                                },
//                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
//                            )
//                        }
//                    }
//                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                singleLine = true,
            )
            DodamButton(
                onClick = onLoginClick,
                text = "",
                enabled = id.isNotBlank() && pw.isNotBlank(),
                loading = isLoading
            )
        }
    }
}
