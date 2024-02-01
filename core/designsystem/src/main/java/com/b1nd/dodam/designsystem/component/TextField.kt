package com.b1nd.dodam.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.b1nd.dodam.designsystem.theme.Gray400

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
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors()
) {
    var isFocused by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(true) }
    TextField(
        value = value,
        textStyle = textStyle,
        onValueChange = onValueChange,
        modifier = modifier
            .background(
                color = Color.Transparent
            )
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused },
        shape = shape,
        isError = isError,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        minLines = minLines,
        colors = colors,
        interactionSource = interactionSource,
        visualTransformation = if (isPassword && isPasswordVisible) PasswordVisualTransformation()
        else VisualTransformation.None,
        label = {
            Text(
                hint,
                style = if (!isFocused)
                    MaterialTheme.typography.bodyMedium
                else MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp
                ),
                color = if (isError) MaterialTheme.colorScheme.error else if (isFocused) MaterialTheme.colorScheme.primary else Gray400
            )
        },
        trailingIcon = {
            Row {
                if (!isError) {
                    if (isPassword) {
                        if (isPasswordVisible)
                            IconButton(
                                onClick = { isPasswordVisible = !isPasswordVisible },
                                modifier = Modifier
                                    .background(Color.Transparent)
                                    .size(24.dp)
                            ) {
                                EyeIcon()
                            } else {
                            IconButton(
                                onClick = { isPasswordVisible = !isPasswordVisible },
                                modifier = Modifier
                                    .background(Color.Transparent)
                                    .size(24.dp)
                            ) {
                                EyeSlashIcon()
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    IconButton(
                        onClick = { onClickCancel() },
                        modifier = Modifier
                            .background(Color.Transparent)
                            .size(24.dp)
                    ) {
                        CancelIcon()
                    }
                } else {
                    ErrorIcon()
                }
            }
        }
    )
}

@Preview
@Composable
fun DodamTextFieldPreview() {
    var value = remember { mutableStateOf("") }
    DodamTheme {
        DodamTextField(
            onValueChange = { value.value = it },
            value = value.value,
            hint = "hint",
            isError = true,
            onClickCancel = { value.value = "" },
            isPassword = true,)
    }
}
