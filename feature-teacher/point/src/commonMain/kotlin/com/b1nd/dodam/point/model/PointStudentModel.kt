package com.b1nd.dodam.point.model

import com.b1nd.dodam.member.model.MyInfo

data class PointStudentModel(
    val id: Int = 0,
    val name: String = "",
    val grade: Int = 0,
    val room: Int = 0,
    val number: Int = 0,
    val selected: Boolean = false,
    val profileImage: String? = null
)

internal fun MyInfo.toPointStudentModel() =
    PointStudentModel(
        id = student?.id?: 0,
        name = name,
        grade = student?.grade?: 0,
        room = student?.room?: 0,
        number = student?.number?: 0,
        profileImage = profileImage
    )