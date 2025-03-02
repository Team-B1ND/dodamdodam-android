package com.b1nd.dodam.club.model

import kotlinx.collections.immutable.ImmutableList

enum class ClubPage {
    LIST,
    DETAIL
}
data class ClubUiState(
    val clubPendingUiState: ClubPendingUiState = ClubPendingUiState.Loading,
    val detailClub: DetailClub = DetailClub(),
)

sealed interface ClubPendingUiState {
    data class Success(
        val clubPendingList: ClubPendingList,
        val detailClubMember: DetailClubAndMember
    ) : ClubPendingUiState
    data object Loading : ClubPendingUiState
    data object Error : ClubPendingUiState
}

data class ClubPendingList(
    val creativeClubs: ImmutableList<Club>,
    val selfClubs: ImmutableList<Club>,
)

data class DetailClubAndMember(
    val club: Club,
    val clubMember: ImmutableList<ClubMember>
)

data class DetailClub(
    val id: Long,
    val isLoading: Boolean,
    val name: String,
    val type: ClubType,
    val leader: String?,
    val shortDescription: String,
) {
    constructor(): this(0,true,"",ClubType.CREATIVE_ACTIVITY_CLUB,"","")
}
