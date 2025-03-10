package com.b1nd.dodam.notice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.division.DivisionRepository
import com.b1nd.dodam.data.division.model.DivisionOverview
import com.b1nd.dodam.data.notice.NoticeRepository
import com.b1nd.dodam.data.notice.model.NoticeStatus
import com.b1nd.dodam.notice.model.NoticeUiState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NoticeViewModel : ViewModel(), KoinComponent {

    private val divisionRepository: DivisionRepository by inject()
    private val noticeRepository: NoticeRepository by inject()

    private val _uiState = MutableStateFlow(NoticeUiState())
    val uiState = _uiState.asStateFlow()

    private var loadSearchJob: Job? = null
    private var loadCategoryJob: Job? = null

    fun loadDivision() {
        viewModelScope.launch {
            divisionRepository.getMyDivisions(
                lastId = 0,
                limit = 9999,
                keyword = "",
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                divisionList = result.data.toMutableList().apply {
                                    add(0, DivisionOverview(0, "전체"))
                                }.toImmutableList(),
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

    fun loadNextNoticeWithCategory(categoryId: Int) {
        // 1. 같은 카테고리로 페이징 -> 중복된 로드를 막기 위해, isLoading을 통해 막아야함.
        // 2. 중간에 카테고리 변경으로 새로 갱신

        if (_uiState.value.isLoading && categoryId == _uiState.value.noticeLastCategoryId) return

        loadCategoryJob?.cancel()
        loadCategoryJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                )
            }
            if (categoryId != _uiState.value.noticeLastCategoryId) {
                _uiState.update {
                    it.copy(
                        noticeLastId = null,
                        noticeLastCategoryId = categoryId,
                        noticeList = persistentListOf(),
                    )
                }
            }

            (
                if (categoryId == 0) {
                    noticeRepository.getNotice(
                        keyword = null,
                        lastId = _uiState.value.noticeLastId,
                        limit = PAGE_SIZE,
                        status = NoticeStatus.CREATED,
                    )
                } else {
                    noticeRepository.getNoticeWithCategory(
                        id = categoryId,
                        lastId = _uiState.value.noticeLastId,
                        limit = PAGE_SIZE,
                    )
                }
                ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            if (result.data.isEmpty()) {
                                return@update it.copy(
                                    isLoading = false,
                                )
                            }

                            val newData = it.noticeList.toMutableList().apply {
                                addAll(result.data)
                            }
                            it.copy(
                                noticeLastId = newData.lastOrNull()?.id,
                                noticeList = newData.toImmutableList(),
                                isLoading = false,
                            )
                        }
                    }
                    Result.Loading -> {}
                    is Result.Error -> {
                        result.error.printStackTrace()
                        _uiState.update { it.copy(isLoading = false) }
                    }
                }
            }
        }
    }

    fun loadNextNoticeWithKeyword(keyword: String) {
        if (keyword == _uiState.value.searchNoticeLastText && _uiState.value.isSearchLoading) {
            return
        }

        if (keyword != _uiState.value.searchNoticeLastText) {
            _uiState.update {
                it.copy(
                    searchNoticeLastText = keyword,
                    searchNoticeLastId = null,
                    searchNoticeList = persistentListOf(),
                )
            }
        }

        loadSearchJob?.cancel()
        loadSearchJob = viewModelScope.launch {
            _uiState.update { it.copy(isSearchLoading = true) }

            noticeRepository.getNotice(
                keyword = keyword,
                lastId = _uiState.value.searchNoticeLastId,
                limit = PAGE_SIZE,
                status = NoticeStatus.CREATED,
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            val newData = it.searchNoticeList.toMutableList().apply {
                                addAll(result.data)
                            }
                            it.copy(
                                searchNoticeLastId = newData.lastOrNull()?.id,
                                searchNoticeList = newData.toImmutableList(),
                                isSearchLoading = false,
                            )
                        }
                    }
                    Result.Loading -> {}
                    is Result.Error -> {
                        result.error.printStackTrace()
                        _uiState.update { it.copy(isSearchLoading = false) }
                    }
                }
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
