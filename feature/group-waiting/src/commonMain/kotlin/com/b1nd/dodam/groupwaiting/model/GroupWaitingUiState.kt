package com.b1nd.dodam.groupwaiting.model

import com.b1nd.dodam.data.division.model.DivisionMember
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class GroupWaitingUiState(
    val isLoading: Boolean = false,
    val students: ImmutableList<DivisionMember> = persistentListOf(),
    val teachers: ImmutableList<DivisionMember> = persistentListOf(),
    val admins: ImmutableList<DivisionMember> = persistentListOf(),
    val parents: ImmutableList<DivisionMember> = persistentListOf(),
)