package com.b1nd.dodam.member.model

data class Teacher(
    val id: Int,
    val position: String,
    val tel: String,
)

internal fun TeacherResponse.toModel(): Teacher = Teacher(
    id = id,
    position = position,
    tel = tel,
)
