package com.b1nd.dodam.setting.model

data class SettingUiState(
    val isLoading: Boolean = false,
    val profile: String? = null,
    val name: String = ""
)