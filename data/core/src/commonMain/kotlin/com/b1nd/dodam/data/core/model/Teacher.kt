package com.b1nd.dodam.data.core.model

import com.b1nd.dodam.network.core.model.TeacherResponse

data class Teacher(
    val name: String,
    val position: String,
    val tel: String,
)

fun TeacherResponse.toModel() = Teacher(
    name = name,
    position = position,
    tel = tel,
)
