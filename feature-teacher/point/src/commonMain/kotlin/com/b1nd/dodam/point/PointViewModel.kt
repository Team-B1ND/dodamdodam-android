package com.b1nd.dodam.point

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.point.PointRepository
import com.b1nd.dodam.data.point.model.PointReason
import com.b1nd.dodam.logging.KmLogging
import com.b1nd.dodam.member.MemberRepository
import com.b1nd.dodam.point.model.PointLoadingUiState
import com.b1nd.dodam.point.model.PointSideEffect
import com.b1nd.dodam.point.model.PointStudentModel
import com.b1nd.dodam.point.model.PointUiState
import com.b1nd.dodam.point.model.toPointStudentModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class PointViewModel : ViewModel(), KoinComponent {

    private val memberRepository: MemberRepository by inject()
    private val pointRepository: PointRepository by inject()
    private val dispatcher: CoroutineDispatcher by inject(named(DispatcherType.IO))

    private val _sideEffect = MutableSharedFlow<PointSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(PointUiState())
    val uiState = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() = viewModelScope.launch(dispatcher) {
        _uiState.update {
            it.copy(
                uiState = PointLoadingUiState.Loading,
            )
        }
        val job1 = async {
            var data: ImmutableList<PointStudentModel>? = null
            memberRepository.getMemberActiveAll().collect {
                when (it) {
                    is Result.Success -> {
                        KmLogging.debug("test", "${it.data}")
                        data = it.data.map { it.toPointStudentModel() }.toImmutableList()
                    }
                    is Result.Loading -> {}
                    is Result.Error -> {
                        it.error.printStackTrace()
                    }
                }
            }
            return@async data
        }

        val job2 = async {
            var data: ImmutableList<PointReason>? = null
            pointRepository.getAllScoreReason().collect {
                when (it) {
                    is Result.Success -> {
                        data = it.data
                    }

                    is Result.Loading -> {}
                    is Result.Error -> {
                        it.error.printStackTrace()
                    }
                }
            }
            return@async data
        }

        val pointStudents = job1.await()
        val pointReasons = job2.await()

        if (pointStudents == null || pointReasons == null) {
            _uiState.update {
                it.copy(
                    uiState = PointLoadingUiState.Error,
                )
            }
            return@launch
        }

        _uiState.update {
            it.copy(
                uiState = PointLoadingUiState.Success(
                    students = pointStudents,
                    reasons = pointReasons,
                ),
            )
        }
    }

    fun clickStudent(student: PointStudentModel) = viewModelScope.launch(dispatcher) {
        var uiState = uiState.value.uiState

        if (uiState is PointLoadingUiState.Success) {
            uiState = uiState.copy(
                students = uiState.students
                    .map {
                        if (it.id == student.id) {
                            return@map it.copy(
                                selected = it.selected.not(),
                            )
                        }
                        return@map it
                    }.toImmutableList(),
            )
        }
        _uiState.update { state ->
            state.copy(
                uiState = uiState,
            )
        }
    }

    fun givePoint(students: List<PointStudentModel>, reason: PointReason) = viewModelScope.launch(dispatcher) {
        _uiState.update {
            it.copy(
                isNetworkLoading = true,
            )
        }
        pointRepository.postGivePoint(
            issueAt = DodamDate.localDateNow(),
            reasonId = reason.id,
            studentIds = students.map { it.id },
        ).collect {
            when (it) {
                is Result.Success -> {
                    _sideEffect.emit(PointSideEffect.SuccessGivePoint)

                    var uiState = uiState.value.uiState
                    if (uiState is PointLoadingUiState.Success) {
                        uiState = uiState.copy(
                            students = uiState.students.map { it.copy(selected = false) }.toImmutableList(),
                        )
                    }

                    _uiState.update {
                        it.copy(
                            uiState = uiState,
                        )
                    }
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    it.error.printStackTrace()
                    _sideEffect.emit(PointSideEffect.FailedGivePoint(it.error))
                }
            }
        }
        _uiState.update {
            it.copy(
                isNetworkLoading = false,
            )
        }
    }
}
