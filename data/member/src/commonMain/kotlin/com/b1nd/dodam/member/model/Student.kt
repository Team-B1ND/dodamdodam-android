package com.b1nd.dodam.member.model

data class Student(
    val grade: Int,
    val id: Int,
    val name: String,
    val number: Int,
    val room: Int,
)

internal fun StudentResponse.toModel(): Student = Student(
    grade = grade,
    id = id,
    name = name,
    number = number,
    room = room,
)
