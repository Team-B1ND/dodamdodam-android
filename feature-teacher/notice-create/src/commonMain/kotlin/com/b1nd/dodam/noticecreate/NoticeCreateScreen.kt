package com.b1nd.dodam.noticecreate

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.b1nd.dodam.noticecreate.screen.NoticeCreateFirstScreen
import com.b1nd.dodam.noticecreate.screen.NoticeCreateSecondScreen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet

private enum class NoticeCreatePage {
    First,
    Second,
}

@Composable
fun NoticeCreateScreen(
    popBackStack: () -> Unit
) {

    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var nowPage by remember { mutableStateOf(NoticeCreatePage.First) }
    var selectCategories: ImmutableList<String> by remember { mutableStateOf(persistentListOf("전체")) }
    val categories: ImmutableList<String> = persistentListOf("전체", "B1ND", "CNS", "알트", "모디", "런데이")

    AnimatedVisibility(
        visible = nowPage == NoticeCreatePage.First,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        NoticeCreateFirstScreen(
            title = title,
            onTitleValueChange = {
                title = it
            },
            body = body,
            onBodyValueChange = {
                body = it
            },
            popBackStack = popBackStack,
            onNextClick = {
                nowPage = NoticeCreatePage.Second
            }
        )
    }

    AnimatedVisibility(
        visible = nowPage == NoticeCreatePage.Second,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        NoticeCreateSecondScreen(
            selectCategory = selectCategories,
            categories = categories,
            popBackStack = {
                nowPage = NoticeCreatePage.First
            },
            onClickSuccess = {},
            onClickCategory = {
                selectCategories = selectCategories.toMutableList().apply {
                    if (selectCategories.contains(it)) {

                        if (selectCategories.size == 1 && it == "전체") {
                            return@apply
                        }
                        remove(it)
                    } else {
                        if (it == "전체") {
                            removeAll(selectCategories)
                        } else {
                            remove("전체")
                        }
                        add(it)
                    }
                }.toImmutableList()
            }
        )
    }
}