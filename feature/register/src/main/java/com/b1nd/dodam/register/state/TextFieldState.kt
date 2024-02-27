package com.b1nd.dodam.register.state

data class TextFieldState(
    val value: String = "",
    val isValid: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
)
