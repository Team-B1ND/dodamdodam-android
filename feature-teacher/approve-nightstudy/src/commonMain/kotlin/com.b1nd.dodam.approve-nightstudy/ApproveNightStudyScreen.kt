package com.b1nd.dodam.approvenightstudy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamModalBottomSheet
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.component.DodamMember
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApproveNightStudyScreen(
    onBackClick: () -> Unit,
    viewModel: ApproveNightStudyViewModel = koinViewModel()
) {
    var gradeIndex by remember { mutableIntStateOf(0) }
    val gradeNumber = listOf(
        "전체",
        "1학년",
        "2학년",
        "3학년",
    )
    val gradeItem = List(4) { index ->
        DodamSegment(
            selected = gradeIndex == index,
            text = gradeNumber[index],
            onClick = { gradeIndex = index },
        )
    }.toImmutableList()

    var roomIndex by remember { mutableIntStateOf(0) }
    val roomNumber = listOf(
        "전체",
        "1반",
        "2반",
        "3반",
        "4반",
    )
    val roomItem = List(5) { index ->
        DodamSegment(
            selected = roomIndex == index,
            text = roomNumber[index],
            onClick = { roomIndex = index },
        )
    }.toImmutableList()


    var searchStudent by remember { mutableStateOf("") }

    var selectedItemIndex by remember { mutableStateOf(-1) }

    LaunchedEffect(key1 = true) {
        viewModel.load()
    }
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            DodamTopAppBar(
                title = "심야 자습 승인",
                modifier = Modifier.statusBarsPadding(),
                onBackClick = {
                    onBackClick()
                },
            )
        },
    ) {
        if (selectedItemIndex >= 0) {
            DodamModalBottomSheet(
                shape = RoundedCornerShape(
                    topStart = 28.dp,
                    topEnd = 28.dp,
                ),
                onDismissRequest = {
                    selectedItemIndex = -1
                },
                title = {
                    Text(
                        text = "${state.detailMember.name}님의 심야 자습 정보",
                        style = DodamTheme.typography.heading1Bold(),
                        color = DodamTheme.colors.labelNormal,
                        modifier = Modifier.padding(bottom = 16.dp),
                    )
                },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier.padding(bottom = 12.dp),
                        ) {
                            Text(
                                text = "시작 날짜",
                                style = DodamTheme.typography.headlineMedium(),
                                color = DodamTheme.colors.labelAssistive
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = state.detailMember.startDay,
                                style = DodamTheme.typography.headlineMedium(),
                                color = DodamTheme.colors.labelNeutral
                            )
                        }
                        Row(
                            modifier = Modifier.padding(bottom = 12.dp),
                        ) {
                            Text(
                                text = "종료 날짜",
                                style = DodamTheme.typography.headlineMedium(),
                                color = DodamTheme.colors.labelAssistive
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = state.detailMember.endDay,
                                style = DodamTheme.typography.headlineMedium(),
                                color = DodamTheme.colors.labelNeutral
                            )
                        }
                        Row(
                            modifier = Modifier.padding(bottom = 12.dp),
                        ) {
                            Text(
                                text = "자습 장소",
                                style = DodamTheme.typography.headlineMedium(),
                                color = DodamTheme.colors.labelAssistive
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = state.detailMember.place,
                                style = DodamTheme.typography.headlineMedium(),
                                color = DodamTheme.colors.labelNeutral
                            )
                        }
                        Row(
                            modifier = Modifier.padding(bottom = 12.dp),
                        ) {
                            Text(
                                text = "학습 계획",
                                style = DodamTheme.typography.headlineMedium(),
                                color = DodamTheme.colors.labelAssistive,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = state.detailMember.content,
                                style = DodamTheme.typography.headlineMedium(),
                                color = DodamTheme.colors.labelNeutral
                            )
                        }
                        if (state.detailMember.doNeedPhone) {
                            Row {
                                Text(
                                    text = "휴대폰 사용",
                                    style = DodamTheme.typography.headlineMedium(),
                                    color = DodamTheme.colors.labelAssistive,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = state.detailMember.reasonForPhone ?:"",
                                    style = DodamTheme.typography.headlineMedium(),
                                    color = DodamTheme.colors.labelNeutral,
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.padding(top = 16.dp),
                        ) {
                            DodamButton(
                                onClick = {
                                },
                                text = "거절하기",
                                buttonSize = ButtonSize.Large,
                                buttonRole = ButtonRole.Negative,
                                modifier = Modifier.weight(1f),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            DodamButton(
                                onClick = {
                                },
                                text = "승인하기",
                                buttonSize = ButtonSize.Large,
                                buttonRole = ButtonRole.Primary,
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                },
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DodamTheme.colors.backgroundNormal)
                .padding(it),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                Column {
                    DodamSegmentedButton(
                        segments = gradeItem,
                        modifier = Modifier.padding(top = 12.dp),
                    )
                    DodamSegmentedButton(
                        segments = roomItem,
                        modifier = Modifier.padding(top = 12.dp),
                    )
                }
                DodamTextField(
                    value = searchStudent,
                    onValueChange = {
                        searchStudent = it
                    },
                    label = "학생 검색",
                    onClickRemoveRequest = {
                        searchStudent = ""
                    },
                    modifier = Modifier,
                )
                when (val data = state.nightStudyUiState) {
                    is NightStudyUiState.Error -> {}
                    is NightStudyUiState.Loading -> {}
                    is NightStudyUiState.Success -> {
                        val members = data.pendingData
                        val filteredMemberList = members.filter { member ->
                            when {
                                gradeIndex == 0 && roomIndex == 0 -> true
                                gradeIndex == 0 && roomIndex != 0 -> member.student.room == roomIndex
                                gradeIndex != 0 && roomIndex == 0 -> member.student.grade == gradeIndex
                                else -> member.student.grade == gradeIndex && member.student.room == roomIndex
                            }
                        }.let { list ->
                            if (searchStudent.isNotEmpty()) {
                                list.filter { it.student.name.contains(searchStudent) }
                            } else {
                                list
                            }
                        }
                        LazyColumn(
                            modifier = Modifier.padding(top = 20.dp),
                        ) {
                            items(filteredMemberList.size) { index ->
                                val filterMemberData = filteredMemberList[index]
                                DodamMember(
                                    name = filterMemberData.student.name,
                                    icon = null,
                                    modifier = Modifier
                                        .padding(bottom = 12.dp)
                                        .clickable {
                                            selectedItemIndex = index
                                            viewModel.detailMember(
                                                id = filterMemberData.id,
                                                start = filterMemberData.startAt.date.toString(),
                                                end = filterMemberData.endAt.date.toString(),
                                                place = filterMemberData.place,
                                                doNeedPhone = filterMemberData.doNeedPhone,
                                                reasonForPhone = filterMemberData.reasonForPhone,
                                                reason = filterMemberData.content,
                                                name = filterMemberData.student.name
                                            )
                                        },
                                    content = {
                                        if (index == selectedItemIndex) {
                                            Image(
                                                modifier = Modifier
                                                    .align(Alignment.CenterVertically)
                                                    .size(24.dp),
                                                imageVector = DodamIcons.CheckmarkCircle.value,
                                                contentDescription = null,
                                                colorFilter = ColorFilter.tint(DodamTheme.colors.primaryNormal),
                                            )
                                        }
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}