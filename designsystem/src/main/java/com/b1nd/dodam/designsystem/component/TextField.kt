package com.b1nd.dodam.designsystem.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.b1nd.dodam.designsystem.theme.CancelIcon
import com.b1nd.dodam.designsystem.theme.DodamTheme
import com.b1nd.dodam.designsystem.theme.ErrorIcon
import com.b1nd.dodam.designsystem.theme.EyeIcon
import com.b1nd.dodam.designsystem.theme.EyeSlashIcon

/**
 * Dodam TextField
 *
 * @param value:  the input text to be shown in the text field
 * @param onValueChange: the callback that is triggered when the input service updates the text. An updated text comes as a parameter of the callback
 * @param onClickCancel: TextField cancel button click callback
 * @param modifier: the Modifier to be applied to this text field
 * @param hint: when the input text is empty, the hint is displayed in the text field
 * @param isPassword: is this text field for password input, if this is true, the eye icon is displayed to show/hide the password
 * @param enabled: ontrols the enabled state of this text field. When false, this component will not respond to user input, and it will appear visually disabled and disabled to accessibility services.
 * @param readOnly: controls the editable state of the text field. When true, the text field cannot be modified. However, a user can focus it and copy text from it. Read-only text fields are usually used to display pre-filled forms that a user cannot edit.
 * @param textStyle: the style to be applied to the input text. Defaults to LocalTextStyle.
 * @param prefix: the optional prefix to be displayed before the input text in the text field
 * @param suffix: the optional suffix to be displayed after the input text in the text field
 * @param supportingText: the optional supporting text to be displayed below the text field
 * @param isPasswordVisible: the optional initial visibility of the password. If this is true, the password is visible, if isPassword is false this parameter is ignored
 * @param isError: indicates if the text field's current value is in error. If set to true, the label, bottom indicator and trailing icon by default will be displayed in error color
 * @param keyboardOptions: software keyboard options that contains configuration such as
 * @param keyboardActions: keyboardActions - when the input service emits an IME action, the corresponding callback is called. Note that this IME action may be different from what you specified in KeyboardOptions.imeAction.
 * @param singleLine: the optional single line state of the text field. When true, the text field will be displayed in a single line
 * @param maxLines: the optional maximum lines of the text field. Defaults to 1
 * @param minLines: the optional minimum lines of the text field. Defaults to 1
 * @param visualTransformation: transforms the visual representation of the input value For example, you can use PasswordVisualTransformation to create a password text field. By default, no visual transformation is applied.
 * @param interactionSource: the MutableInteractionSource representing the stream of Interactions for this text field. You can create and pass in your own remembered instance to observe Interactions and customize the appearance / behavior of this text field in different states.
 * @param shape: defines the shape of this text field's container. Defaults to TextFieldDefaults.shape
 * @param colors: the optional colors to be applied to the text field. Defaults to TextFieldDefaults.colors
 * */

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
    visualTransformation: VisualTransformation = VisualTransformation.None,
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
            visualTransformation
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

@Composable
fun DodamTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
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
    visualTransformation: VisualTransformation = VisualTransformation.None,
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
            visualTransformation
        },
        label = {
            Text(
                hint,
                style = if (isFocused || value.text.isNotEmpty()) {
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
private fun DodamTextFieldPreview() {
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
                value = value.value,
                onValueChange = { value.value = it },
                onClickCancel = { value.value = "" },
                hint = "password",
                isPassword = true,
                isPasswordVisible = false,
            )
            DodamTextField(
                value = value.value,
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
