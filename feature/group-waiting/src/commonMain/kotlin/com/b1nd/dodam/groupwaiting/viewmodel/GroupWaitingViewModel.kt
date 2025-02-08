package com.b1nd.dodam.groupwaiting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.core.model.MemberRole
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.division.DivisionRepository
import com.b1nd.dodam.groupwaiting.model.GroupWaitingUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class GroupWaitingViewModel: ViewModel(), KoinComponent {

    private val dispatcher: CoroutineDispatcher by inject(named(DispatcherType.IO))
    private val divisionRepository: DivisionRepository by inject()

    private val _uiState = MutableStateFlow(GroupWaitingUiState())
    val uiState = _uiState.asStateFlow()

    fun load(divisionId: Int) {
        viewModelScope.launch(dispatcher) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                )
            }
            divisionRepository.getDivisionMembers(
                id = divisionId,
                status = Status.PENDING,
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                students = result.data.filter { it.role == MemberRole.STUDENT }.toImmutableList(),
                                teachers = result.data.filter { it.role == MemberRole.TEACHER }.toImmutableList(),
                                admins = result.data.filter { it.role == MemberRole.ADMIN }.toImmutableList(),
                                parents = result.data.filter { it.role == MemberRole.PARENT }.toImmutableList(),
                            )
                        }
                    }
                    Result.Loading -> {}
                    is Result.Error -> {
                        result.error.printStackTrace()
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }
}