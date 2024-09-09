package com.b1nd.dodam.nightstudy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDefaultTopAppBar
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.nightstudy.state.NightStudyUiState
import com.b1nd.dodam.nightstudy.viewmodel.NightStudyViewModel
import com.b1nd.dodam.ui.component.UserItem
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NightStudyScreen(
    viewModel: NightStudyViewModel = koinViewModel()
) {
    var classIndex by remember { mutableIntStateOf(0) }
    val classNumber = listOf(
        "전체",
        "1학년",
        "2학년",
        "3학년",
    )
    val classItem = List(4) { index ->
        DodamSegment(
            selected = classIndex == index,
            text = classNumber[index],
            onClick = { classIndex = index },
        )
    }.toImmutableList()

    var gradeIndex by remember { mutableIntStateOf(0) }
    val gradeNumber = listOf(
        "전체",
        "1반",
        "2반",
        "3반",
        "4반",
        "5반",
    )
    val gradeItem = List(6) { index ->
        DodamSegment(
            selected = gradeIndex == index,
            text = gradeNumber[index],
            onClick = { gradeIndex = index },
        )
    }.toImmutableList()

    var titleIndex by remember { mutableIntStateOf(0) }
    val text = listOf(
        "심자 진행 중",
        "심자 대기 중"
    )
    val item = List(2) { index: Int ->
        DodamSegment(
            selected = titleIndex == index,
            text = text[index],
            onClick = { titleIndex = index }
        )
    }.toImmutableList()

    val uiState by viewModel.uiState.collectAsState()


    val pending = listOf("병준5", "병준6")

    LaunchedEffect(key1 = true) {
        viewModel.check()
    }

    Scaffold(
        topBar = {
            DodamDefaultTopAppBar(
                title = "심야 자습",
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DodamTheme.colors.backgroundNeutral)
                .padding(it),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                Column {
                    DodamSegmentedButton(
                        segments = item,
                        modifier = Modifier.padding(top = 12.dp),
                    )
                    DodamSegmentedButton(
                        segments = classItem,
                        modifier = Modifier.padding(top = 12.dp),
                    )
                    DodamSegmentedButton(
                        segments = gradeItem,
                        modifier = Modifier.padding(top = 12.dp),
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 20.dp)
                        .wrapContentHeight()
                        .clip(shape = RoundedCornerShape(18.dp))
                        .background(DodamTheme.colors.staticWhite),
                ) {
                    Text(
                        text = if (titleIndex == 0) "심자 자습중인 학생" else "심자 대기중인 학생",
                        color = DodamTheme.colors.labelStrong,
                        style = DodamTheme.typography.headlineBold(),
                        modifier = Modifier
                            .padding(top = 16.dp, start = 10.dp, bottom = 6.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(bottom = 10.dp)
                    ) {
                        when (uiState.nightStudyUiState) {
                            is NightStudyUiState.Success -> {
                                val studying = (uiState.nightStudyUiState as NightStudyUiState.Success).data
                                items(if (titleIndex == 0) studying.size else pending.size) { listIndex ->
                                    UserItem(
                                        userName = if (titleIndex == 0) studying[listIndex]?.student?.name ?: "" else pending[listIndex],
                                    ) {
                                        val start =
                                            studying[listIndex]?.startAt?.date.toString().split("-")[2].toInt()
                                        val end =
                                            studying[listIndex]?.endAt?.date.toString().split("-")[2].toInt()

                                        val a = end - start

                                        if (titleIndex == 0) {
                                            Text(
                                                text = if (a == 1)"오늘 종료" else "${a}일 남음",
                                                style = DodamTheme.typography.headlineMedium(),
                                                color = if (a == 1) DodamTheme.colors.primaryNormal else DodamTheme.colors.labelAssistive,
                                            )
                                        } else {
                                            DodamButton(
                                                onClick = {},
                                                text = "승인하기",
                                                buttonSize = ButtonSize.Small,
                                                buttonRole = ButtonRole.Assistive
                                            )
                                        }
                                    }
                                }
                            }
                            NightStudyUiState.Error -> {}
                            NightStudyUiState.Loading -> {}
                        }
                    }
                }
            }
        }
    }
}