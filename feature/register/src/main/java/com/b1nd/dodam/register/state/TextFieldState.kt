package com.b1nd.dodam.register.state

data class TextFieldState(
    val value: String = "",
    val isValid: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val focused: Boolean = false
) {
    fun getValueAsString(until: Int = 3, lastIndex: Int = 2, lastPrefix: Int? = null): String {
        fun get(index: Int) = this.value.getOrElse(index) { '0' }
        return listOf(
            "${get(0)}학년",
            "${get(1)}반",
            "${lastPrefix?.let { get(it) } ?: ""}${get(lastIndex)}번",
        ).subList(0, until).joinToString(" ")
    }
}
