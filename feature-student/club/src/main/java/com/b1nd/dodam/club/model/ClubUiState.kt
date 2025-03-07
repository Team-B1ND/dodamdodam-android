package com.b1nd.dodam.club.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

enum class ClubPage {
    MY,
    JOIN,
}

data class ClubUiState(
    val joinedClubUiState: JoinedClubUiState = JoinedClubUiState.Loading,
    val clubSideEffect: ClubSideEffect = ClubSideEffect.NotExist,
    val createdClubList: ImmutableList<Club> = persistentListOf(),
    val receivedCLub: ImmutableList<ClubJoin> = persistentListOf(),
    val allClubList: ImmutableList<Club> = persistentListOf(),
    val allSelfClubList: ImmutableList<Club> = persistentListOf()
)


sealed interface JoinedClubUiState {
    data class Success(
        val joinedClubList: ImmutableList<Club>,
        val joinedSelfClubList: ImmutableList<Club>,
    ) : JoinedClubUiState
    data object Loading : JoinedClubUiState
    data object Error : JoinedClubUiState
}
