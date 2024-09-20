package com.b1nd.dodam.data.meal.model

import com.b1nd.dodam.network.meal.model.MenuResponse

data class Menu(
    val name: String,
    val allergies: List<Int>,
)

internal fun MenuResponse.toModel(): Menu = Menu(
    name = name,
    allergies = allergies,
)
