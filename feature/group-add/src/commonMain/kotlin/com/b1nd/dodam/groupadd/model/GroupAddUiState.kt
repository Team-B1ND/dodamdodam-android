package com.b1nd.dodam.groupadd.model

import com.b1nd.dodam.data.division.model.DivisionMember
import com.b1nd.dodam.data.division.model.DivisionOverview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

data class GroupAddUiState(
    val isLoading: Boolean = false,
    val addLoading: Boolean = false,
    val lastLoadId: Int = 0,
    val divisions: ImmutableList<GroupAddDivisionOverview> = persistentListOf(),
    val divisionMembers: ImmutableMap<Int, ImmutableList<DivisionMember>> = persistentMapOf(),
)

data class GroupAddDivisionOverview(
    val id: Int,
    val name: String,
    val isOpen: Boolean,
)