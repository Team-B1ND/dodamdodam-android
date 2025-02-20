package com.b1nd.dodam.noticecreate

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.b1nd.dodam.data.division.model.DivisionOverview
import com.b1nd.dodam.noticecreate.model.NoticeCreateSideEffect
import com.b1nd.dodam.noticecreate.screen.NoticeCreateFirstScreen
import com.b1nd.dodam.noticecreate.screen.NoticeCreateSecondScreen
import com.b1nd.dodam.noticecreate.viewmodel.NoticeCreateViewModel
import com.b1nd.dodam.ui.component.SnackbarState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

private enum class NoticeCreatePage {
    First,
    Second,
}

@OptIn(KoinExperimentalAPI::class)
@Composable
fun NoticeCreateScreen(
    viewModel: NoticeCreateViewModel = koinViewModel(),
    showSnackbar: (state: SnackbarState, message: String) -> Unit,
    popBackStack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var nowPage by remember { mutableStateOf(NoticeCreatePage.First) }
    var selectCategories: ImmutableList<DivisionOverview> by remember { mutableStateOf(persistentListOf(
        DivisionOverview(
            id = 0,
            name = "전체"
        )
    )) }

    LaunchedEffect(true) {
        viewModel.loadDivisions()
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is NoticeCreateSideEffect.FailedCreate -> {
                    showSnackbar(SnackbarState.ERROR, sideEffect.throwable.message ?: "공지 작성에 실패했습니다.")
                }
                NoticeCreateSideEffect.SuccessCreate -> {
                    showSnackbar(SnackbarState.SUCCESS, "공지 작성에 성공했습니다!")
                    popBackStack()
                }
            }
        }
    }

    AnimatedVisibility(
        visible = nowPage == NoticeCreatePage.First,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        NoticeCreateFirstScreen(
            viewModel = viewModel,
            uiState = uiState,
            title = title,
            onTitleValueChange = {
                title = it
            },
            content = content,
            onBodyValueChange = {
                content = it
            },
            popBackStack = popBackStack,
            onNextClick = {
                nowPage = NoticeCreatePage.Second
            },
        )
    }

    AnimatedVisibility(
        visible = nowPage == NoticeCreatePage.Second,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        NoticeCreateSecondScreen(
            isLoading = uiState.isUploadLoading,
            selectDivisions = selectCategories,
            divisions = uiState.divisions,
            popBackStack = {
                nowPage = NoticeCreatePage.First
            },
            onClickSuccess = {
                viewModel.createNotice(
                    title = title,
                    content = content,
                    files = uiState.files + uiState.images,
                    divisions = if (selectCategories.size == 1 && selectCategories.first().id == 0) uiState.divisions else selectCategories
                )
            },
            onClickCategory = {
                selectCategories = selectCategories.toMutableList().apply {
                    if (selectCategories.contains(it)) {
                        if (selectCategories.size == 1 && it.id == 0) {
                            return@apply
                        }
                        remove(it)
                    } else {
                        if (it.id == 0) {
                            removeAll(selectCategories)
                        } else {
                            removeAll {
                                it.id == 0
                            }
                        }
                        add(it)
                    }
                }.toImmutableList()
            },
        )
    }
}
