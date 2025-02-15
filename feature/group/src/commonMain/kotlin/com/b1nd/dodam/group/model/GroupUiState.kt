package com.b1nd.dodam.group.model

import com.b1nd.dodam.data.division.model.DivisionOverview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class GroupUiState(
    val isRefresh: Boolean = false,
    val isAllLoading: Boolean = false,
    val isMyLoading: Boolean = false,
    val allGroupLastId: Int = 0,
    val myGroupLastId: Int = 0,
    val allGroups: ImmutableList<DivisionOverview> = persistentListOf(),
    val myGroups: ImmutableList<DivisionOverview> = persistentListOf(),
)
