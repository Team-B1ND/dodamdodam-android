package com.b1nd.dodam.club

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.club.model.ClubType
import com.b1nd.dodam.club.model.JoinedClubUiState
import com.b1nd.dodam.club.model.MyClubSideEffect
import com.b1nd.dodam.club.model.MyClubUiState
import com.b1nd.dodam.club.model.request.ClubJoinRequest
import com.b1nd.dodam.club.repository.ClubRepository
import com.b1nd.dodam.common.result.Result
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class MyClubViewModel : ViewModel(), KoinComponent {
    private val clubRepository: ClubRepository by inject()

    private val _state = MutableStateFlow(MyClubUiState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<ApplySideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

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
                                        joinedSelfClubList = joinedSelfClub,
                                    ),
                                    clubSideEffect = if (joinedClub.isNotEmpty()) MyClubSideEffect.Exist else MyClubSideEffect.NotExist,
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
                            val createClubList = result.data.toImmutableList()

                            val createSelfClub = createClubList.filter {
                                it.type == ClubType.SELF_DIRECT_ACTIVITY_CLUB
                            }.toImmutableList()

                            val createClub = createClubList.filter {
                                it.type == ClubType.CREATIVE_ACTIVITY_CLUB
                            }.toImmutableList()

                            _state.update {
                                it.copy(
                                    createdClubList = createClub,
                                    createdSelfClubList = createSelfClub,
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
                                    receivedCLub = receivedClub,
                                )
                            }

                            return@collect
                        }
                    }
                }
            }
        }
    }

    fun acceptClub(id: Int) {
        viewModelScope.launch {
            clubRepository.postClubJoinRequestsAllow(id).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _sideEffect.send(ApplySideEffect.Failed(result.error))

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
                        _sideEffect.send(ApplySideEffect.SuccessApply)
                        getClub()
                        return@collect
                    }
                }
            }
        }
    }

    fun rejectClub(id: Int) {
        viewModelScope.launch {
            clubRepository.deleteClubJoinRequest(id).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _sideEffect.send(ApplySideEffect.Failed(result.error))
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
                        _sideEffect.send(ApplySideEffect.SuccessReject)
                        getClub()
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
                                allSelfClubList = allSelfClubList,
                            )
                        }

                        return@collect
                    }
                }
            }
        }
    }

    fun getJoinRequest() {
        viewModelScope.launch {
            clubRepository.getClubMyJoinRequest().collect { result ->
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
                        val joinRequestClubList = result.data.toImmutableList()

                        val joinRequestClub = joinRequestClubList.filter {
                            it.club.type == ClubType.CREATIVE_ACTIVITY_CLUB
                        }.sortedBy { clubJoin ->
                            when {
                                clubJoin.clubPermission.toString().endsWith("_1") -> 1
                                clubJoin.clubPermission.toString().endsWith("_2") -> 2
                                clubJoin.clubPermission.toString().endsWith("_3") -> 3
                                else -> Int.MAX_VALUE
                            }
                        }.toImmutableList()

                        val joinRequestSelfClub = joinRequestClubList.filter {
                            it.club.type == ClubType.SELF_DIRECT_ACTIVITY_CLUB
                        }.toImmutableList()

                        _state.update {
                            it.copy(
                                requestJoinClub = joinRequestClub,
                                requestJoinSelfClub = joinRequestSelfClub,
                            )
                        }

                        return@collect
                    }
                }
            }
        }
    }

    fun applyClub(clubId: List<Int>, introduce: List<String>, selfClubId: List<Int>?, selfIntroduce: List<String>?) {
        viewModelScope.launch {
            val requestList = mutableListOf<ClubJoinRequest>()

            clubId.forEachIndexed { index, item ->
                requestList.add(
                    ClubJoinRequest(
                        clubId = item,
                        clubPriority = "CREATIVE_ACTIVITY_CLUB_${index + 1}",
                        introduction = introduce[index],
                    ),
                )
            }

            if (!selfClubId.isNullOrEmpty() && !selfIntroduce.isNullOrEmpty()) {
                if (selfClubId.size == selfIntroduce.size) {
                    selfClubId.forEachIndexed { index, item ->
                        requestList.add(
                            ClubJoinRequest(
                                clubId = item,
                                clubPriority = null,
                                introduction = selfIntroduce[index],
                            ),
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            joinedClubUiState = JoinedClubUiState.Error,
                        )
                    }
                    return@launch
                }
            }

            clubRepository.postClubJoinRequests(requestList).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _sideEffect.send(ApplySideEffect.Failed(result.error))
                        result.error.printStackTrace()
                        _state.update {
                            it.copy(
                                joinedClubUiState = JoinedClubUiState.Error,
                            )
                        }
                    }
                    Result.Loading -> {
                    }
                    is Result.Success -> {
                        _sideEffect.send(ApplySideEffect.SuccessApply)
                    }
                }
            }
        }
    }
}

sealed interface ApplySideEffect {
    data object SuccessApply : ApplySideEffect
    data object SuccessReject : ApplySideEffect
    data class Failed(val throwable: Throwable) : ApplySideEffect
}
