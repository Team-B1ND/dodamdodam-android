package com.b1nd.dodam.editmemberinfo.model

data class EditMemberInfoUiState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val image: String? = null,
    val isLoading: Boolean = false,
)
