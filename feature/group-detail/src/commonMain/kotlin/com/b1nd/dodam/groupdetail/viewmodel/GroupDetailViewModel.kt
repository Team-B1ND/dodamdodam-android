package com.b1nd.dodam.groupdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.division.DivisionRepository
import com.b1nd.dodam.data.division.model.DivisionMember
import com.b1nd.dodam.data.division.model.DivisionPermission
import com.b1nd.dodam.data.division.model.isAdmin
import com.b1nd.dodam.groupdetail.model.GroupDetailSideEffect
import com.b1nd.dodam.groupdetail.model.GroupDetailUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class GroupDetailViewModel: ViewModel(), KoinComponent {

    @Dispatcher(DispatcherType.IO)
    private val dispatcher: CoroutineDispatcher by inject(named(DispatcherType.IO))
    private val divisionRepository: DivisionRepository by inject()

    private val _uiState = MutableStateFlow(GroupDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<GroupDetailSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun load(id: Int) {

        viewModelScope.launch(dispatcher) {
            launch {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                    )
                }
                divisionRepository.getDivision(
                    id = id
                ).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    divisionInfo = result.data,
                                )
                            }
                        }
                        Result.Loading -> {}
                        is Result.Error -> {
                            result.error.printStackTrace()
                        }
                    }
                }
            }
            launch {
                divisionRepository.getDivisionMembers(
                    id = id,
                    status = Status.ALLOWED,
                ).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _uiState.update {
                                it.copy(
                                    divisionAdminMembers = result.data.filter { it.permission.isAdmin() }.toImmutableList(),
                                    divisionWriterMembers = result.data.filter { it.permission == DivisionPermission.WRITER }.toImmutableList(),
                                    divisionReaderMembers = result.data.filter { it.permission == DivisionPermission.READER }.toImmutableList(),
                                )
                            }
                        }
                        Result.Loading -> {}
                        is Result.Error -> {
                            result.error.printStackTrace()
                        }
                    }
                }
            }

            launch {
                divisionRepository.getDivisionMembersCnt(
                    id = id,
                    status = Status.PENDING,
                ).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _uiState.update {
                                it.copy(
                                    divisionPendingCnt = result.data,
                                )
                            }
                        }
                        Result.Loading -> {}
                        is Result.Error -> {
                            result.error.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    fun kickMember(divisionId: Int, memberId: Int) {
        viewModelScope.launch(dispatcher) {
            divisionRepository.deleteDivisionMembers(
                divisionId = divisionId,
                memberId = listOf(memberId),
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                divisionAdminMembers = it.divisionAdminMembers.filter { it.id != memberId }.toImmutableList(),
                                divisionWriterMembers = it.divisionWriterMembers.filter { it.id != memberId }.toImmutableList(),
                                divisionReaderMembers = it.divisionReaderMembers.filter { it.id != memberId }.toImmutableList(),
                            )
                        }
                    }
                    Result.Loading -> {}
                    is Result.Error -> {}
                }
            }
        }
    }

    fun requestJoinDivision(id: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    requestLoading = true
                )
            }
            divisionRepository.postDivisionApplyRequest(
                divisionId = id,
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                requestLoading = false
                            )
                        }
                    }
                    Result.Loading -> {}
                    is Result.Error -> {
                        result.error.printStackTrace()
                    }
                }
            }
        }
    }

    fun upperPermission(divisionId: Int, divisionMember: DivisionMember) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    requestLoading = true
                )
            }
            val editPermission = when (divisionMember.permission) {
                DivisionPermission.READER -> DivisionPermission.WRITER
                DivisionPermission.WRITER -> DivisionPermission.ADMIN
                DivisionPermission.ADMIN -> DivisionPermission.ADMIN
            }
            divisionRepository.patchDivisionMemberPermission(
                divisionId = divisionId,
                memberId = divisionMember.id,
                permission = editPermission,
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        editStateMemberScreenPermission(
                            divisionMember = divisionMember,
                            beforePermission = divisionMember.permission,
                            afterPermission = editPermission
                        )
                        _sideEffect.send(GroupDetailSideEffect.SuccessEditPermission)
                    }
                    Result.Loading -> {}
                    is Result.Error -> {
                        result.error.printStackTrace()
                        _sideEffect.send(GroupDetailSideEffect.FailedEditPermission(result.error))
                        _uiState.update {
                            it.copy(
                                requestLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun lowerPermission(divisionId: Int, divisionMember: DivisionMember) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    requestLoading = true
                )
            }
            val editPermission = when (divisionMember.permission) {
                DivisionPermission.READER -> DivisionPermission.READER
                DivisionPermission.WRITER -> DivisionPermission.READER
                DivisionPermission.ADMIN -> DivisionPermission.WRITER
            }
            divisionRepository.patchDivisionMemberPermission(
                divisionId = divisionId,
                memberId = divisionMember.id,
                permission = editPermission,
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        editStateMemberScreenPermission(
                            divisionMember = divisionMember,
                            beforePermission = divisionMember.permission,
                            afterPermission = editPermission
                        )
                        _sideEffect.send(GroupDetailSideEffect.SuccessEditPermission)
                    }
                    Result.Loading -> {}
                    is Result.Error -> {
                        result.error.printStackTrace()
                        _sideEffect.send(GroupDetailSideEffect.FailedEditPermission(result.error))
                        _uiState.update {
                            it.copy(
                                requestLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun editStateMemberScreenPermission(
        divisionMember: DivisionMember,
        beforePermission: DivisionPermission,
        afterPermission: DivisionPermission,
    ) {
        val readers = _uiState.value.divisionReaderMembers.toMutableList()
        val writers = _uiState.value.divisionWriterMembers.toMutableList()
        val admins = _uiState.value.divisionAdminMembers.toMutableList()

        when (beforePermission) {
            DivisionPermission.READER -> {
                readers.removeAll {
                    it.memberId == divisionMember.memberId
                }
            }
            DivisionPermission.WRITER -> {
                writers.removeAll {
                    it.memberId == divisionMember.memberId
                }
            }
            DivisionPermission.ADMIN -> {
                admins.removeAll {
                    it.memberId == divisionMember.memberId
                }
            }
        }

        when (afterPermission) {
            DivisionPermission.READER -> {
                readers.add(divisionMember.copy(
                    permission = afterPermission
                ))
            }

            DivisionPermission.WRITER -> {
                writers.add(divisionMember.copy(
                    permission = afterPermission
                ))
            }

            DivisionPermission.ADMIN -> {
                admins.add(divisionMember.copy(
                    permission = afterPermission
                ))
            }
        }


        _uiState.update {
            it.copy(
                requestLoading = false,
                divisionAdminMembers = admins.toImmutableList(),
                divisionWriterMembers = writers.toImmutableList(),
                divisionReaderMembers = readers.toImmutableList(),
            )
        }
    }
}