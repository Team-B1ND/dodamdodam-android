package com.b1nd.dodam.notice.model

import com.b1nd.dodam.data.division.model.DivisionOverview
import com.b1nd.dodam.data.notice.model.Notice
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class NoticeUiState(
    val isFirstLoading: Boolean = true,
    val isLoading: Boolean = false,
    val isDivisionLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val divisionList: ImmutableList<DivisionOverview> = persistentListOf(),
    val noticeLastCategoryId: Int = 0,
    val noticeLastId: Int? = null,
    val noticeList: ImmutableList<Notice> = persistentListOf(),
    val isSearchLoading: Boolean = false,
    val searchNoticeLastText: String = "",
    val searchNoticeLastId: Int? = null,
    val searchNoticeList: ImmutableList<Notice> = persistentListOf(),
)
