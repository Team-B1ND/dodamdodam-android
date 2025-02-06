package com.b1nd.dodam.groupdetail.model

import com.b1nd.dodam.data.division.model.DivisionInfo
import com.b1nd.dodam.data.division.model.DivisionMember
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class GroupDetailUiState(
    val isLoading: Boolean = false,
    val divisionInfo: DivisionInfo? = null,
    val divisionAdminMembers: ImmutableList<DivisionMember> = persistentListOf(),
    val divisionWriterMembers: ImmutableList<DivisionMember> = persistentListOf(),
    val divisionReaderMembers: ImmutableList<DivisionMember> = persistentListOf(),
    val divisionPendingCnt: Int = 0,
)
