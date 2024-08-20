package com.b1nd.dodam.teacher

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun DodamTeacherApp(viewModel: TestViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    var idText by remember { mutableStateOf("") }
    var pwText by remember { mutableStateOf("") }
    var tokenText by remember { mutableStateOf("") }

    MaterialTheme {
        Column {
            Text(state.toString())

            TextField(
                value = idText,
                onValueChange = {
                    idText = it
                },
            )
            TextField(
                value = pwText,
                onValueChange = {
                    pwText = it
                },
            )
            TextField(
                value = tokenText,
                onValueChange = {
                    tokenText = it
                },
            )
            DodamTestButton(
                text = "삭제",
                onClick = {
                    viewModel.deleteUser()
                },
            )

            DodamTestButton(
                text = "저장",
                onClick = {
                    viewModel.saveUser(
                        id = idText,
                        pw = pwText,
                        token = tokenText,
                    )
                },
            )
        }
    }
}

@Composable
private fun DodamTestButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        content = {
            Text(text)
        },
    )
}
