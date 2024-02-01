package com.b1nd.dodam.designsystem.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.b1nd.dodam.designsystem.theme.CancelIcon
import com.b1nd.dodam.designsystem.theme.DodamTheme
import com.b1nd.dodam.designsystem.theme.ErrorIcon
import com.b1nd.dodam.designsystem.theme.EyeIcon
import com.b1nd.dodam.designsystem.theme.EyeSlashIcon

@Composable
fun DodamTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onClickCancel: () -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    isPassword: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: String = "",
    isPasswordVisible: Boolean = false,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        errorIndicatorColor = MaterialTheme.colorScheme.error,
        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
        focusedTextColor = MaterialTheme.colorScheme.onBackground,
        errorTextColor = MaterialTheme.colorScheme.onBackground,
        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        errorLabelColor = MaterialTheme.colorScheme.error,
        unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
    ),
) {
    var isFocused by remember { mutableStateOf(false) }
    var isSlashEye by remember { mutableStateOf(isPasswordVisible) }
    TextField(
        value = value,
        textStyle = textStyle,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused },
        shape = shape,
        isError = isError,
        enabled = enabled,
        readOnly = readOnly,
        prefix = prefix,
        suffix = suffix,
        singleLine = singleLine,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        minLines = minLines,
        colors = colors,
        supportingText = {
            Text(
                text = supportingText,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp,
            )
        },
        interactionSource = interactionSource,
        visualTransformation = if (isPassword && !isSlashEye) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        label = {
            Text(
                hint,
                style = if (isFocused || value.isNotEmpty()) {
                    MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 12.sp,
                    )
                } else {
                    MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                    )
                },
            )
        },
        trailingIcon = {
            if (!isError) {
                if (isFocused) {
                    Row {
                        if (isPassword) {
                            if (!isSlashEye) {
                                IconButton(
                                    onClick = { isSlashEye = !isSlashEye },
                                    modifier = Modifier
                                        .background(Color.Transparent)
                                        .size(24.dp),
                                ) {
                                    EyeIcon()
                                }
                            } else {
                                IconButton(
                                    onClick = { isSlashEye = !isSlashEye },
                                    modifier = Modifier
                                        .background(Color.Transparent)
                                        .size(24.dp),
                                ) {
                                    EyeSlashIcon()
                                }
                            }
                            Spacer(modifier = Modifier.size(16.dp))
                        }
                        IconButton(
                            onClick = { onClickCancel() },
                            modifier = Modifier
                                .background(Color.Transparent)
                                .size(24.dp),
                        ) {
                            CancelIcon()
                        }
                        if (isPassword) Spacer(modifier = Modifier.size(12.dp))
                    }
                }
            } else {
                ErrorIcon()
            }
        },
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, backgroundColor = 0x000000)
@Preview(showBackground = true)
@Composable
fun DodamTextFieldPreview() {
    var value = remember { mutableStateOf("") }
    DodamTheme {
        Column {
            DodamTextField(
                onValueChange = { value.value = it },
                value = value.value,
                hint = "id",
                onClickCancel = { value.value = "" },
            )
            DodamTextField(
                value = "12345678",
                onValueChange = { value.value = it },
                onClickCancel = { value.value = "" },
                hint = "password",
                isPassword = true,
                isPasswordVisible = false,
            )
            DodamTextField(
                value = "12345678",
                onValueChange = { value.value = it },
                onClickCancel = { value.value = "" },
                hint = "password",
                isPassword = true,
                isPasswordVisible = true,
            )
            DodamTextField(
                value = "12345678",
                onValueChange = { value.value = it },
                onClickCancel = { value.value = "" },
                hint = "password",
                isError = true,
                isPassword = true,
                isPasswordVisible = false,
                supportingText = "비밀번호가 일치하지 않습니다.",
            )
        }
    }
}
