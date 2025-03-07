package com.b1nd.dodam.club

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.club.di.clubViewModelModule
import com.b1nd.dodam.club.model.Club
import com.b1nd.dodam.club.model.ClubJoin
import com.b1nd.dodam.club.model.ClubPermission
import com.b1nd.dodam.club.model.ClubSideEffect
import com.b1nd.dodam.club.model.ClubType
import com.b1nd.dodam.club.model.ClubUiState
import com.b1nd.dodam.club.model.JoinedClubUiState
import com.b1nd.dodam.club.repository.ClubRepository
import com.b1nd.dodam.common.result.Result
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin

class ClubViewModel : ViewModel(), KoinComponent {
    private val clubRepository: ClubRepository by inject()

    private val _state = MutableStateFlow(ClubUiState())
    val state = _state.asStateFlow()

    fun getClub() {
        _state.update {
            it.copy(
                joinedClubUiState = JoinedClubUiState.Loading,
            )
        }

        viewModelScope.launch {
            launch {
                clubRepository.getClubJoined().collect { result ->
                    when (result) {
                        is Result.Error -> {
                            result.error.printStackTrace()
                            _state.update {
                                it.copy(
                                    joinedClubUiState = JoinedClubUiState.Error,
                                )
                            }
                            return@collect
                        }

                        Result.Loading -> {

                        }

                        is Result.Success -> {
                            val joinedSelfClub = result.data.filter {
                                it.type == ClubType.SELF_DIRECT_ACTIVITY_CLUB
                            }.toImmutableList()

                            val joinedClub = result.data.filter {
                                it.type == ClubType.CREATIVE_ACTIVITY_CLUB
                            }.toImmutableList()

                            _state.update {
                                it.copy(
                                    joinedClubUiState = JoinedClubUiState.Success(
                                        joinedClubList = joinedClub,
                                        joinedSelfClubList = joinedSelfClub
                                    ),
                                    clubSideEffect = if (joinedClub.isNotEmpty()) ClubSideEffect.Exist else ClubSideEffect.NotExist
                                )
                            }

                            return@collect
                        }
                    }
                }
            }


            launch {
                clubRepository.getClubMyCreated().collect { result ->
                    when (result) {
                        is Result.Error -> {
                            result.error.printStackTrace()
                            _state.update {
                                it.copy(
                                    joinedClubUiState = JoinedClubUiState.Error,
                                )
                            }
                            return@collect
                        }

                        Result.Loading -> {

                        }

                        is Result.Success -> {
                            val createClub = result.data.toImmutableList()

                            _state.update {
                                it.copy(
                                    createdClubList = createClub
                                )
                            }

                            return@collect
                        }
                    }
                }
            }

            launch {
                clubRepository.getClubJoinRequestReceived().collect { result ->
                    when (result) {
                        is Result.Error -> {
                            result.error.printStackTrace()
                            _state.update {
                                it.copy(
                                    joinedClubUiState = JoinedClubUiState.Error,
                                )
                            }
                            return@collect
                        }

                        Result.Loading -> {

                        }

                        is Result.Success -> {
                            val receivedClub = result.data.toImmutableList()
                            _state.update {
                                it.copy(
                                    receivedCLub = receivedClub
                                )
                            }

                            return@collect
                        }
                    }
                }
            }

        }
    }

    fun acceptClub(
        id: Int
    ) {
        viewModelScope.launch {
            clubRepository.postClubJoinRequestsAllow(id).collect { result ->
                when (result) {
                    is Result.Error -> {
                        result.error.printStackTrace()
                        _state.update {
                            it.copy(
                                joinedClubUiState = JoinedClubUiState.Error,
                            )
                        }
                        return@collect
                    }

                    Result.Loading -> {

                    }

                    is Result.Success -> {

                        return@collect
                    }
                }

            }
        }
    }

    fun rejectClub(
        id: Int
    ) {
        viewModelScope.launch {
            clubRepository.deleteClubJoinRequest(id).collect { result ->
                when (result) {
                    is Result.Error -> {
                        result.error.printStackTrace()
                        _state.update {
                            it.copy(
                                joinedClubUiState = JoinedClubUiState.Error,
                            )
                        }
                        return@collect
                    }

                    Result.Loading -> {

                    }

                    is Result.Success -> {

                        return@collect
                    }
                }

            }
        }
    }

    fun getAllClub() {
        viewModelScope.launch {
            clubRepository.getClubList().collect { result ->
                when (result) {
                    is Result.Error -> {
                        result.error.printStackTrace()
                        _state.update {
                            it.copy(
                                joinedClubUiState = JoinedClubUiState.Error,
                            )
                        }
                        return@collect
                    }

                    Result.Loading -> {

                    }

                    is Result.Success -> {
                        val allClub = result.data.toImmutableList()
                        val allClubList = allClub.filter {
                            it.type == ClubType.CREATIVE_ACTIVITY_CLUB
                        }.toImmutableList()

                        val allSelfClubList = allClub.filter {
                            it.type == ClubType.SELF_DIRECT_ACTIVITY_CLUB
                        }.toImmutableList()

                        _state.update {
                            it.copy(
                                allClubList = allClubList,
                                allSelfClubList = allSelfClubList
                            )
                        }

                        return@collect
                    }
                }

            }
        }

    }

    fun applyClub(
        clubId: List<Int>,
        introduce: List<String>,
        selfClubId: List<Int>?,
        selfIntroduce: List<String>?
    ) {
        viewModelScope.launch {
            clubId.forEachIndexed { index, item ->
                clubRepository.postClubJoinRequests(
                    clubId = item,
                    clubPriority = "CREATIVE_ACTIVITY_CLUB_${index + 1}",
                    introduce = introduce[index]
                ).collect { result ->
                    when (result) {
                        is Result.Error -> {
                            result.error.printStackTrace()
                            _state.update {
                                it.copy(
                                    joinedClubUiState = JoinedClubUiState.Error,
                                )
                            }
                            return@collect
                        }

                        Result.Loading -> {

                        }

                        is Result.Success -> {

                            return@collect
                        }
                    }

                }
            }
            if (!selfClubId.isNullOrEmpty() && !selfIntroduce.isNullOrEmpty()) {
                clubId.forEachIndexed { index, item ->
                    clubRepository.postClubJoinRequests(
                        clubId = item,
                        clubPriority = null,
                        introduce = selfIntroduce[index]
                    ).collect { result ->
                        when (result) {
                            is Result.Error -> {
                                result.error.printStackTrace()
                                _state.update {
                                    it.copy(
                                        joinedClubUiState = JoinedClubUiState.Error,
                                    )
                                }
                                return@collect
                            }

                            Result.Loading -> {

                            }

                            is Result.Success -> {

                                return@collect
                            }
                        }

                    }
                }
            }
        }
    }
}
