package com.b1nd.dodam.member.model

data class Teacher(
    val name: String,
    val position: String,
    val tel: String,
)

internal fun TeacherResponse.toModel(): Teacher = Teacher(
    name = name,
    position = position,
    tel = tel,
)
