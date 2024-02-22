package com.b1nd.dodam.student.home.model

data class HomeUiState(
    val isLoading: Boolean = false,
    val meal: Triple<String, String, String>,
) {
    constructor() : this(
        meal = Triple("아침을 불러오고 있어요.", "점심을 불러오고 있어요.", "저녁을 불러오고 있어요.")
    )
}
