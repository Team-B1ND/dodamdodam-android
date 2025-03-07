package com.b1nd.dodam.club

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.club.model.Club
import com.b1nd.dodam.club.model.ClubMember
import com.b1nd.dodam.club.model.ClubMemberStudent
import com.b1nd.dodam.club.model.ClubPendingList
import com.b1nd.dodam.club.model.ClubPendingUiState
import com.b1nd.dodam.club.model.ClubPermission
import com.b1nd.dodam.club.model.ClubSideEffect
import com.b1nd.dodam.club.model.ClubState
import com.b1nd.dodam.club.model.ClubType
import com.b1nd.dodam.club.model.ClubUiState
import com.b1nd.dodam.club.model.DetailClub
import com.b1nd.dodam.club.model.DetailClubAndMember
import com.b1nd.dodam.club.repository.ClubRepository
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.core.model.Teacher
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class ClubViewModel : ViewModel(), KoinComponent {
    private val clubRepository: ClubRepository by inject()
    private val dispatcher: CoroutineDispatcher by inject(named(DispatcherType.IO))

    private val _sideEffect = MutableSharedFlow<ClubSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val _state = MutableStateFlow(ClubUiState())
    val state = _state.asStateFlow()

    private val defaultDetailClubMember = DetailClubAndMember(
        club = Club(
            id = -1,
            name = "",
            shortDescription = "",
            description = "",
            subject = "",
            type = ClubType.CREATIVE_ACTIVITY_CLUB,
            image = "",
            teacher = Teacher(
                name = "",
                position = "",
                tel = "",
            ),
            state = ClubState.PENDING,
        ),
        clubMember =
        ClubMember(
            isLeader = false,
            students = persistentListOf(
                ClubMemberStudent(
                    id = -1,
                    status = ClubState.PENDING,
                    permissions = ClubPermission.CLUB_MEMBER,
                    studentId = -1,
                    name = "",
                    grade = -1,
                    room = -1,
                    number = -1,
                    profileImage = "",
                ),
            ),
        ),
    )

    fun loadClubList() = viewModelScope.launch {
        _state.update {
            it.copy(
                clubPendingUiState = ClubPendingUiState.Loading,
            )
        }
        var creativeClubs: ImmutableList<Club>
        var selfClubs: ImmutableList<Club>
        clubRepository.getClubList().collect { club ->
            when (club) {
                is Result.Error -> {
                    club.error.printStackTrace()
                    _state.update {
                        it.copy(
                            clubPendingUiState = ClubPendingUiState.Error,
                        )
                    }
                    return@collect
                }

                Result.Loading -> {
                }

                is Result.Success -> {
                    creativeClubs =
                        club.data.filter { it.state == ClubState.PENDING && it.type == ClubType.CREATIVE_ACTIVITY_CLUB }
                            .toImmutableList()
                    selfClubs =
                        club.data.filter { it.state == ClubState.PENDING && it.type == ClubType.SELF_DIRECT_ACTIVITY_CLUB }
                            .toImmutableList()

                    _state.update {
                        it.copy(
                            clubPendingUiState = ClubPendingUiState.Success(
                                clubPendingList = ClubPendingList(
                                    creativeClubs = creativeClubs,
                                    selfClubs = selfClubs,
                                ),
                                detailClubMember = defaultDetailClubMember,
                            ),
                        )
                    }
                    return@collect
                }
            }
        }
    }

    fun loadDetailClub(id: Long, club: Club) = viewModelScope.launch {
        _state.update {
            it.copy(
                clubPendingUiState = ClubPendingUiState.Loading,
            )
        }
        clubRepository.getClubMember(id.toInt()).collect { member ->
            when (member) {
                is Result.Error -> {
                    member.error.printStackTrace()
                    _state.update {
                        it.copy(
                            clubPendingUiState = ClubPendingUiState.Error,
                        )
                    }
                }

                Result.Loading -> {}
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            clubPendingUiState = ClubPendingUiState.Success(
                                clubPendingList = ClubPendingList(
                                    creativeClubs = persistentListOf(),
                                    selfClubs = persistentListOf(),
                                ),
                                detailClubMember = DetailClubAndMember(
                                    club = club,
                                    clubMember = ClubMember(
                                        isLeader = member.data.isLeader,
                                        students = member.data.students.map { ww ->
                                            ClubMemberStudent(
                                                id = ww.id,
                                                status = ww.status,
                                                permissions = ww.permissions,
                                                studentId = ww.studentId,
                                                name = ww.name,
                                                grade = ww.grade,
                                                room = ww.room,
                                                number = ww.number,
                                                profileImage = ww.profileImage,
                                            )
                                        }.toImmutableList(),
                                    ),
                                ),
                            ),
                        )
                    }
                    return@collect
                }
            }
        }
    }

    fun detailMember(id: Long, name: String, type: ClubType, shortDescription: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    detailClub = DetailClub(
                        id = id,
                        name = name,
                        type = type,
                        isLoading = true,
                        leader = "",
                        shortDescription = shortDescription,
                    ),
                )
            }
            val leader = loadLeaderName(id)
            _state.update {
                it.copy(
                    detailClub = DetailClub(
                        id = id,
                        name = name,
                        type = type,
                        isLoading = false,
                        leader = leader,
                        shortDescription = shortDescription,
                    ),
                )
            }
        }
    }

    fun postClubState(id: Int, state: ClubState, reason: String?) {
        viewModelScope.launch {
            clubRepository.patchClubState(
                clubIds = persistentListOf(id),
                status = state,
                reason = reason,
            ).collect {
                when (it) {
                    is Result.Error -> {
                        _sideEffect.emit(ClubSideEffect.Failed(it.error))
                        it.error.printStackTrace()
                    }

                    Result.Loading -> {}
                    is Result.Success -> {
                        if (state == ClubState.ALLOWED) {
                            _sideEffect.emit(ClubSideEffect.SuccessApprove)
                        } else {
                            _sideEffect.emit(ClubSideEffect.SuccessReject)
                        }
                    }
                }
            }
        }
    }

    private suspend fun loadLeaderName(id: Long): String = clubRepository.getClubLeader(id.toInt())
        .filterIsInstance<Result.Success<ClubMemberStudent>>()
        .map { it.data.name }.firstOrNull() ?: "알 수 없음"
}
