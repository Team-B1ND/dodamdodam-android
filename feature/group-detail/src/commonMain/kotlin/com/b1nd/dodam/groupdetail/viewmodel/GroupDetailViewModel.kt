package com.b1nd.dodam.groupdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.division.DivisionRepository
import com.b1nd.dodam.data.division.model.DivisionPermission
import com.b1nd.dodam.data.division.model.isAdmin
import com.b1nd.dodam.groupdetail.model.GroupDetailUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
}