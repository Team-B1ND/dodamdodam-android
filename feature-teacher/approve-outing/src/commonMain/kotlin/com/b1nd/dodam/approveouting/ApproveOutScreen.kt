package com.b1nd.dodam.approveouting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.approveouting.model.ApproveSideEffect
import com.b1nd.dodam.approveouting.model.OutPendingUiState
import com.b1nd.dodam.approveouting.viewmodel.ApproveOutViewModel
import com.b1nd.dodam.common.utiles.getDate
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamEmpty
import com.b1nd.dodam.designsystem.component.DodamModalBottomSheet
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.ui.component.DodamMember
import com.b1nd.dodam.ui.component.DodamMemberLoadingCard
import com.b1nd.dodam.ui.component.SnackbarState
import com.b1nd.dodam.ui.icons.ColoredCheckmarkCircle
import com.b1nd.dodam.ui.util.addFocusCleaner
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ApproveOutScreen(
    viewModel: ApproveOutViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    title: Int,
    showSnackbar: (state: SnackbarState, message: String) -> Unit,
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

    var titleIndex by remember { mutableIntStateOf(0) }
    val text = listOf(
        "외출",
        "외박",
    )
    val item = List(2) { index: Int ->
        DodamSegment(
            selected = titleIndex == index,
            text = text[index],
            onClick = { titleIndex = index },
        )
    }.toImmutableList()

    var searchStudent by remember { mutableStateOf("") }

    var selectedItemIndex by remember { mutableStateOf(-1) }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true) {
        titleIndex = if (title == 0) 0 else 1
        viewModel.load()
    }

    LaunchedEffect(true) {
        viewModel.sideEffect.collect {
            when (it) {
                is ApproveSideEffect.Failed -> {
                    showSnackbar(SnackbarState.ERROR, it.throwable.message.toString())
                }
                ApproveSideEffect.SuccessApprove -> {
                    showSnackbar(SnackbarState.SUCCESS, "신청을 승인하였습니다.")
                }
                ApproveSideEffect.SuccessReject -> {
                    showSnackbar(SnackbarState.SUCCESS, "신청을 거절하였습니다.")
                }
            }
        }
    }

    val state by viewModel.state.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefresh,
        onRefresh = viewModel::refresh,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
        contentAlignment = Alignment.TopCenter,
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .addFocusCleaner(focusManager),
            topBar = {
                DodamTopAppBar(
                    title = "외출/외박 승인",
                    modifier = Modifier.statusBarsPadding(),
                    onBackClick = onBackClick,
                )
            },
            containerColor = DodamTheme.colors.backgroundNormal,
        ) {
            if (selectedItemIndex >= 0) {
                DodamModalBottomSheet(
                    shape = RoundedCornerShape(
                        topStart = 28.dp,
                        topEnd = 28.dp,
                    ),
                    onDismissRequest = { selectedItemIndex = -1 },
                    title = {
                        Text(
                            text = "${state.detailMember.name}님의 ${if (titleIndex == 0) "외출" else "외박"}정보",
                            style = DodamTheme.typography.heading1Bold(),
                            color = DodamTheme.colors.labelNormal,
                            modifier = Modifier.padding(bottom = 16.dp),
                        )
                    },
                    content = {
                        Column {
                            Row(
                                modifier = Modifier.padding(bottom = 12.dp),
                            ) {
                                Text(
                                    text = if (titleIndex == 0) "외출 일시" else "외박 날짜",
                                    style = DodamTheme.typography.headlineMedium(),
                                    color = DodamTheme.colors.labelAssistive,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = state.detailMember.start,
                                    style = DodamTheme.typography.headlineMedium(),
                                    color = DodamTheme.colors.labelNeutral,
                                )
                            }
                            Row(
                                modifier = Modifier.padding(bottom = 12.dp),
                            ) {
                                Text(
                                    text = if (titleIndex == 0) "복귀 일시" else "복귀 날짜",
                                    style = DodamTheme.typography.headlineMedium(),
                                    color = DodamTheme.colors.labelAssistive,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = state.detailMember.end,
                                    style = DodamTheme.typography.headlineMedium(),
                                    color = DodamTheme.colors.labelNeutral,
                                )
                            }
                            Row {
                                Text(
                                    text = if (titleIndex == 0) "외출 사유" else "외박 사유",
                                    style = DodamTheme.typography.headlineMedium(),
                                    color = DodamTheme.colors.labelAssistive,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = state.detailMember.reason,
                                    style = DodamTheme.typography.headlineMedium(),
                                    color = DodamTheme.colors.labelNeutral,
                                )
                            }

                            Row(
                                modifier = Modifier.padding(top = 16.dp),
                            ) {
                                DodamButton(
                                    onClick = {
                                        if (titleIndex == 1) {
                                            viewModel.rejectSleepover(state.detailMember.id)
                                        } else {
                                            viewModel.rejectGoing(state.detailMember.id)
                                        }
                                        selectedItemIndex = -1
                                    },
                                    text = "거절하기",
                                    buttonSize = ButtonSize.Large,
                                    buttonRole = ButtonRole.Negative,
                                    modifier = Modifier.weight(1f),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                DodamButton(
                                    onClick = {
                                        if (titleIndex == 1) {
                                            viewModel.allowSleepover(state.detailMember.id)
                                        } else {
                                            viewModel.allowGoing(state.detailMember.id)
                                        }
                                        selectedItemIndex = -1
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
                    when (val data = state.outPendingUiState) {
                        OutPendingUiState.Error -> {
                            DodamEmpty(
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .fillMaxWidth(),
                                onClick = viewModel::load,
                                title = "외출 명단을 불러올 수 없어요.",
                                buttonText = "다시 불러오기",
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = DodamTheme.colors.lineAlternative,
                                ),
                            )
                        }

                        OutPendingUiState.Loading -> {
                            Column(
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                DodamMemberLoadingCard()
                                DodamMemberLoadingCard()
                            }
                        }

                        is OutPendingUiState.Success -> {
                            val members =
                                if (titleIndex == 0) data.outMembers else data.sleepoverMembers
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
                                    DodamMember(
                                        name = filteredMemberList[index].student.name,
                                        icon = null,
                                        modifier = Modifier
                                            .padding(bottom = 12.dp)
                                            .clickable {
                                                selectedItemIndex = index
                                                if (titleIndex == 1) {
                                                    viewModel.detailMember(
                                                        name = filteredMemberList[index].student.name,
                                                        start = getDate(filteredMemberList[index].startAt.date),
                                                        end = getDate(filteredMemberList[index].endAt.date),
                                                        reason = filteredMemberList[index].reason,
                                                        id = filteredMemberList[index].id,
                                                    )
                                                } else {
                                                    viewModel.detailMember(
                                                        name = filteredMemberList[index].student.name,
                                                        start = getTime(filteredMemberList[index].startAt.toString()),
                                                        end = getTime(filteredMemberList[index].endAt.toString()),
                                                        reason = filteredMemberList[index].reason,
                                                        id = filteredMemberList[index].id,
                                                    )
                                                }
                                            },
                                        content = {
                                            if (index == selectedItemIndex) {
                                                Image(
                                                    modifier = Modifier
                                                        .align(Alignment.CenterVertically)
                                                        .size(24.dp),
                                                    imageVector = ColoredCheckmarkCircle,
                                                    contentDescription = null,
                                                    colorFilter = ColorFilter.tint(DodamTheme.colors.primaryNormal),
                                                )
                                            }
                                        },
                                    )
                                }
                            }
                        }

                        else -> {}
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = state.isRefresh,
            state = pullRefreshState,
        )
    }
}

fun getTime(time: String): String {
    val date1 = time.split("T")[0].split("-")[1].toInt()
    val date2 = time.split("T")[0].split("-")[2].toInt()
    val hour = time.split("T")[1].split(":")[0].toInt()
    val minute = time.split("T")[1].split(":")[1].toInt()

    return "${date1}월 ${date2}일 $hour:$minute"
}
