package com.b1nd.dodam.club

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.club.model.ClubType
import com.b1nd.dodam.club.model.JoinedClubUiState
import com.b1nd.dodam.club.model.MyClubSideEffect
import com.b1nd.dodam.club.model.MyClubUiState
import com.b1nd.dodam.club.repository.ClubRepository
import com.b1nd.dodam.common.result.Result
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MyClubViewModel : ViewModel(), KoinComponent {
    private val clubRepository: ClubRepository by inject()

    private val _state = MutableStateFlow(MyClubUiState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

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
                            val createClub = result.data.toImmutableList()

                            _state.update {
                                it.copy(
                                    createdClubList = createClub,
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
                        _event.emit(Event.ShowToast("동아리 수락에 실패했어요"))
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
                        _event.emit(Event.ShowToast("동아리 수락에 성공했어요"))
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
                        _event.emit(Event.ShowToast("동아리 거절에 실패했어요"))
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
                        _event.emit(Event.ShowToast("동아리 거절에 성공했어요"))
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
                        Log.d("Bad", "getAllClub: Error")
                        return@collect
                    }

                    Result.Loading -> {
                        Log.d("Bad", "getAllClub: Loading")
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
                        Log.d("Bad", "getAllClub: Success")

                        return@collect
                    }
                }
            }
        }
    }

    fun applyClub(clubId: List<Int>, introduce: List<String>, selfClubId: List<Int>?, selfIntroduce: List<String>?) {
        Log.d("ClubData", "Starting applyClub with: clubId=$clubId, introduce=$introduce, selfClubId=$selfClubId, selfIntroduce=$selfIntroduce")

        viewModelScope.launch {
            // Creative Activity Clubs
            clubId.forEachIndexed { index, item ->
                Log.d("ClubData", "Processing creative club: id=$item, priority=CREATIVE_ACTIVITY_CLUB_${index + 1}, introduce=${introduce[index]}")

                clubRepository.postClubJoinRequests(
                    clubId = item,
                    clubPriority = "CREATIVE_ACTIVITY_CLUB_${index + 1}",
                    introduce = introduce[index],
                ).collect { result ->
                    when (result) {
                        is Result.Error -> {
                            _event.emit(Event.ShowToast("동아리 신청에 실패했어요"))
                            Log.d("ClubData", "Creative club application failed: $item, ${introduce[index]}")
                            result.error.printStackTrace()
                            _state.update {
                                it.copy(
                                    joinedClubUiState = JoinedClubUiState.Error,
                                )
                            }
                            return@collect
                        }
                        Result.Loading -> {
                            Log.d("ClubData", "Creative club application loading: $item")
                        }
                        is Result.Success -> {
                            Log.d("ClubData", "Creative club application successful: $item")
                            _event.emit(Event.ShowToast("동아리 신청에 성공했어요"))
                            return@collect
                        }
                    }
                }
            }

            if (!selfClubId.isNullOrEmpty() && !selfIntroduce.isNullOrEmpty()) {
                if (selfClubId.size != selfIntroduce.size) {
                    Log.e("ClubData", "Error: selfClubId.size (${selfClubId.size}) != selfIntroduce.size (${selfIntroduce.size})")
                }
                selfClubId.forEachIndexed { index, item ->

                    clubRepository.postClubJoinRequests(
                        clubId = item,
                        clubPriority = null,
                        introduce = selfIntroduce[index],
                    ).collect { result ->
                        when (result) {
                            is Result.Error -> {
                                Log.d("ClubData", "Self club application failed: $item, ${selfIntroduce[index]}")
                                result.error.printStackTrace()
                                _state.update {
                                    it.copy(
                                        joinedClubUiState = JoinedClubUiState.Error,
                                    )
                                }
                                return@collect
                            }
                            Result.Loading -> {
                                Log.d("ClubData", "Self club application loading: $item")
                            }
                            is Result.Success -> {
                                Log.d("ClubData", "Self club application successful: $item")
                                return@collect
                            }
                        }
                    }
                }
            }
            Log.d("ClubData", "All club applications completed")
        }
    }
}

sealed interface Event {
    data class ShowToast(val message: String) : Event
}
