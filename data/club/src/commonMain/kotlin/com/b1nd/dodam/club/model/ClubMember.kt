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
        it.toModel()
    }.toImmutableList(),
)


