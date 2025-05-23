package com.b1nd.dodam.data.core.model

import com.b1nd.dodam.network.core.model.StudentImageResponse
import com.b1nd.dodam.network.core.model.StudentResponse

data class Student(
    val id: Long,
    val name: String,
    val grade: Int,
    val room: Int,
    val number: Int,
)

data class StudentImage(
    val id: Long,
    val name: String,
    val grade: Int,
    val room: Int,
    val number: Int,
    val profileImage: String?,
)

fun StudentResponse.toModel(): Student = Student(
    id = id,
    name = name,
    grade = grade,
    room = room,
    number = number,
)

fun StudentImageResponse.toModel(): StudentImage = StudentImage(
    id = id,
    name = name,
    grade = grade,
    room = room,
    number = number,
    profileImage = profileImage,
)
