package com.b1nd.dodam.club.model

import com.b1nd.dodam.club.model.request.ClubJoinRequest
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

enum class MyClubPage {
    MY,
    JOIN,
}

data class MyClubUiState(
    val joinedClubUiState: JoinedClubUiState = JoinedClubUiState.Loading,
    val createdClubList: ImmutableList<Club> = persistentListOf(),
    val createdSelfClubList: ImmutableList<Club> = persistentListOf(),
    val receivedCLub: ImmutableList<ClubJoin> = persistentListOf(),
    val allClubList: ImmutableList<Club> = persistentListOf(),
    val allSelfClubList: ImmutableList<Club> = persistentListOf(),
    val requestJoinClub: ImmutableList<ClubJoinResponse> = persistentListOf(),
    val requestJoinSelfClub: ImmutableList<ClubJoinResponse> = persistentListOf(),
)

sealed interface JoinedClubUiState {
    data class Success(
        val joinedClubList: ImmutableList<ClubMyJoined>,
        val joinedSelfClubList: ImmutableList<ClubMyJoined>,
    ) : JoinedClubUiState
    data object Loading : JoinedClubUiState
    data object Error : JoinedClubUiState
}
