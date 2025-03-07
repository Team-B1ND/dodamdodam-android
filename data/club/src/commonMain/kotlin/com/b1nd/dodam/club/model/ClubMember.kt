package com.b1nd.dodam.club.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class ClubMember(
    val isLeader: Boolean?,
    val students: ImmutableList<ClubMemberStudent>,
)

internal fun ClubMemberResponse.toModel(): ClubMember = ClubMember(
    isLeader = isLeader,
    students = students.map {
        ClubMemberStudent(
            id = it.id,
            status = it.status.toClubState(),
            permissions = it.permission.toClubPermission(),
            studentId = it.studentId,
            name = it.name,
            grade = it.grade,
            room = it.room,
            number = it.number,
            profileImage = it.profileImage,
        )
    }.toImmutableList(),

)
