package com.b1nd.dodam.data.nightstudy.model

import com.b1nd.dodam.network.nightstudy.model.NightStudyStudentResponse

data class NightStudyStudent(
    val id: Long,
    val name: String,
    val grade: Int,
    val room: Int,
    val number: Int,
    val phone: String,
    val profileImage: String?,
    val isBanned: Boolean,
)

internal fun NightStudyStudentResponse.toModel(): NightStudyStudent = NightStudyStudent(
    id = id,
    name = name,
    grade = grade,
    room = room,
    number = number,
    phone = phone,
    profileImage = profileImage,
    isBanned = isBanned,
)
