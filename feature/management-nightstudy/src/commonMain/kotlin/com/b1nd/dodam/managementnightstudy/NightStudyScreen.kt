package com.b1nd.dodam.managementnightstudy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.window.Dialog
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.CalendarDate
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamButtonDialog
import com.b1nd.dodam.designsystem.component.DodamDatePickerBottomSheet
import com.b1nd.dodam.designsystem.component.DodamDefaultTopAppBar
import com.b1nd.dodam.designsystem.component.DodamEmpty
import com.b1nd.dodam.designsystem.component.DodamModalBottomSheet
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.rememberDodamDatePickerState
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.managementnightstudy.state.NightStudySideEffect
import com.b1nd.dodam.managementnightstudy.state.NightStudyUiState
import com.b1nd.dodam.managementnightstudy.viewmodel.NightStudyViewModel
import com.b1nd.dodam.ui.component.DodamMember
import com.b1nd.dodam.ui.component.SnackbarState
import com.b1nd.dodam.ui.effect.shimmerEffect
import com.b1nd.dodam.ui.util.addFocusCleaner
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterialApi::class, KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
@Composable
fun NightStudyScreen(
    viewModel: NightStudyViewModel = koinViewModel(),
    navigateToApproveStudy: () -> Unit,
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

    var selectedItemIndex by remember { mutableStateOf(-1) }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDodamDatePickerState()
    var endBanDate by remember {
        mutableStateOf(
            Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault()).date,
        )
    }

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

    val state by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefresh,
        onRefresh = viewModel::refresh,
    )

    LaunchedEffect(key1 = true) {
        viewModel.load()
    }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
        ) {
            DodamButtonDialog(
                confirmButtonText = "정지",
                confirmButton = {
                },
                confirmButtonRole = ButtonRole.Negative,
                dismissButton = { showDialog = false },
                dismissButtonRole = ButtonRole.Assistive,
                title = "정지 사유와 기한을 작성해주세요",

            )
        }
    }
    LaunchedEffect(true) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is NightStudySideEffect.Failed -> {
                    showSnackbar(SnackbarState.ERROR, sideEffect.throwable.message.toString())
                }
                NightStudySideEffect.SuccessBan -> {
                    showSnackbar(SnackbarState.SUCCESS, "심자 정지에 성공하였습니다.")
                    viewModel.load()
                }
            }
        }
    }

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
                DodamDefaultTopAppBar(
                    title = "심야 자습",
                    modifier = Modifier.statusBarsPadding(),
                )
            },
        ) {
            if (showDatePicker) {
                DodamDatePickerBottomSheet(
                    onDismissRequest = { showDatePicker = false },
                    sheetState = rememberModalBottomSheetState(true),
                    state = datePickerState,
                    title = "정지 종료일",
                    shape = RoundedCornerShape(
                        topStart = DodamTheme.shapes.extraLarge.topStart,
                        topEnd = DodamTheme.shapes.extraLarge.topEnd,
                        bottomStart = CornerSize(0.dp),
                        bottomEnd = CornerSize(0.dp),
                    ),
                    isValidDate = { calendarDate: CalendarDate ->
                        val currentDate = Clock.System.now()
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                        val date = LocalDate(
                            calendarDate.year,
                            calendarDate.month,
                            calendarDate.dayOfMonth,
                        )
                        date >= currentDate
                    },
                    onClickDate = { date, isValid ->
                        if (!isValid) return@DodamDatePickerBottomSheet

                        val localDate = LocalDate(date.year, date.month, date.dayOfMonth)

                        datePickerState.selectedDate = date
                        endBanDate = localDate
                    },
                    onClickSuccess = {
                        showDatePicker = false
                    },
                )
            }
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
                            text = "${state.detailMember.name}님의 심야 자습 정지",
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
                                AskNightStudyCard(
                                    text = "정지 종료일",
                                    action = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        ) {
                                            Text(
                                                text = formatToMonthDay(endBanDate.toString()),
                                                style = DodamTheme.typography.headlineRegular(),
                                                color = DodamTheme.colors.primaryNormal,
                                            )
                                            Icon(
                                                modifier = Modifier.size(24.dp),
                                                imageVector = DodamIcons.Calendar.value,
                                                contentDescription = "달력 아이콘",
                                                tint = DodamTheme.colors.primaryNormal,
                                            )
                                        }
                                    },
                                    onClick = {
                                        datePickerState.selectedDate = CalendarDate(
                                            year = endBanDate.year,
                                            month = endBanDate.monthNumber,
                                            dayOfMonth = endBanDate.dayOfMonth,
                                            utcTimeMillis = endBanDate.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds(),
                                        )

                                        showDatePicker = true
                                    },
                                )
                            }

                            var banReason by remember { mutableStateOf("") }

                            DodamTextField(
                                value = banReason,
                                onValueChange = { banReason = it },
                                label = "정지사유를 입력해주세요",
                            )

                            Row(
                                modifier = Modifier.padding(top = 16.dp),
                            ) {
                                DodamButton(
                                    onClick = {
                                        selectedItemIndex = -1
                                    },
                                    text = "취소",
                                    buttonSize = _root_ide_package_.com.b1nd.dodam.designsystem.component.ButtonSize.Large,
                                    buttonRole = ButtonRole.Assistive,
                                    modifier = Modifier.weight(2f),
                                    enabled = state.nightStudyUiState != NightStudyUiState.Loading,
                                    loading = state.nightStudyUiState == NightStudyUiState.Loading,
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                DodamButton(
                                    onClick = {
                                        viewModel.ban(state.detailMember.id, banReason, endBanDate.toString())
                                        selectedItemIndex = -1
                                    },
                                    text = "정지하기",
                                    buttonSize = _root_ide_package_.com.b1nd.dodam.designsystem.component.ButtonSize.Large,
                                    buttonRole = ButtonRole.Negative,
                                    modifier = Modifier.weight(3f),
                                    enabled = state.nightStudyUiState != NightStudyUiState.Loading,
                                    loading = state.nightStudyUiState == NightStudyUiState.Loading,
                                )
                            }
                        }
                    },
                )
            }
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
                    Column(
                        modifier = Modifier.padding(bottom = 20.dp),
                    ) {
                        DodamSegmentedButton(
                            segments = gradeItem,
                            modifier = Modifier.padding(top = 12.dp),
                        )
                        DodamSegmentedButton(
                            segments = roomItem,
                            modifier = Modifier.padding(top = 12.dp),
                        )
                        DodamTextField(
                            value = searchStudent,
                            onValueChange = {
                                searchStudent = it
                            },
                            label = "학생 검색",
                            onClickRemoveRequest = {
                                searchStudent = ""
                            },
                        )
                    }

                    when (val data = state.nightStudyUiState) {
                        is NightStudyUiState.Success -> {
                            val members = data.ingData
                            val filteredMemberList = members.filter { studentData ->
                                when {
                                    gradeIndex == 0 && roomIndex == 0 -> true
                                    gradeIndex == 0 && roomIndex != 0 -> studentData.student.room == roomIndex
                                    gradeIndex != 0 && roomIndex == 0 -> studentData.student.grade == gradeIndex
                                    else -> studentData.student.grade == gradeIndex && studentData.student.room == roomIndex
                                }
                            }.let { filteredList ->
                                if (searchStudent.isNotEmpty()) {
                                    filteredList.filter { it.student.name.contains(searchStudent) }
                                } else {
                                    filteredList
                                }
                            }
                            LazyColumn(
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(18.dp)),
                            ) {
                                item {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(shape = RoundedCornerShape(18.dp))
                                            .background(DodamTheme.colors.backgroundNormal),
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .padding(start = 16.dp, top = 16.dp),
                                        ) {
                                            Text(
                                                text = "현재 ",
                                                color = DodamTheme.colors.labelStrong,
                                                style = DodamTheme.typography.headlineBold(),
                                            )
                                            Text(
                                                text = "${data.pendingCnt}명 ",
                                                color = DodamTheme.colors.primaryNormal,
                                                style = DodamTheme.typography.headlineBold(),
                                            )
                                            Text(
                                                text = "승인 대기 중 ",
                                                color = DodamTheme.colors.labelStrong,
                                                style = DodamTheme.typography.headlineBold(),
                                            )
                                        }

                                        DodamButton(
                                            onClick = {
                                                navigateToApproveStudy()
                                            },
                                            text = "승인하러 가기",
                                            buttonRole = ButtonRole.Assistive,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp)
                                                .padding(top = 12.dp, bottom = 16.dp),
                                        )
                                    }
                                }
                                item {
                                    Spacer(modifier = Modifier.height(20.dp))
                                }
                                item {
                                    Column(
                                        modifier = Modifier
                                            .wrapContentHeight()
                                            .fillMaxWidth()
                                            .clip(shape = RoundedCornerShape(18.dp))
                                            .background(DodamTheme.colors.backgroundNormal)
                                            .padding(horizontal = 10.dp),
                                    ) {
                                        Text(
                                            text = "심자 자습중인 학생",
                                            color = DodamTheme.colors.labelStrong,
                                            style = DodamTheme.typography.headlineBold(),
                                            modifier = Modifier
                                                .padding(vertical = 10.dp),
                                        )
                                        filteredMemberList.fastForEachIndexed { index, nightStudy ->
                                            DodamMember(
                                                name = filteredMemberList[index].student.name,
                                                modifier = Modifier
                                                    .padding(bottom = 12.dp),
                                                icon = null,
                                            ) {
                                                Text(
                                                    text = with(filteredMemberList[index].student) {
                                                        "$grade$room${pad2(number)}"
                                                    },
                                                    style = DodamTheme.typography.headlineMedium(),
                                                    color = DodamTheme.colors.labelAssistive,
                                                )

                                                Spacer(modifier = Modifier.weight(1f))

                                                Text(
                                                    text = if (filteredMemberList[index].doNeedPhone) "O" else "X",
                                                    style = DodamTheme.typography.headlineMedium(),
                                                    color = DodamTheme.colors.labelAssistive,
                                                )
                                                Spacer(modifier = Modifier.weight(2f))
                                                DodamButton(
                                                    onClick = {
                                                        selectedItemIndex = index
                                                        viewModel.detailMember(filteredMemberList[index])
                                                    },
                                                    text = "심자 정지",
                                                    buttonSize = ButtonSize.Small,
                                                    buttonRole = ButtonRole.Negative,
                                                    modifier = Modifier
                                                        .padding(horizontal = 8.dp)
                                                        .padding(top = 4.dp, bottom = 4.dp),
                                                )
                                            }
                                        }
                                    }
                                }
                                item {
                                    Spacer(modifier = Modifier.height(80.dp))
                                }
                            }
                        }

                        NightStudyUiState.Error -> {
                            Spacer(modifier = Modifier.height(20.dp))
                            DodamEmpty(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = viewModel::load,
                                title = "심야 자습을 불러올 수 없어요.",
                                buttonText = "다시 불러오기",
                            )
                        }

                        NightStudyUiState.Loading -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(18.dp))
                                    .background(DodamTheme.colors.backgroundNormal),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(width = 170.dp, height = 40.dp)
                                        .padding(top = 16.dp, start = 10.dp)
                                        .background(
                                            shimmerEffect(),
                                            RoundedCornerShape(8.dp),
                                        ),
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(46.dp)
                                        .padding(horizontal = 12.dp)
                                        .padding(top = 12.dp, bottom = 10.dp)
                                        .background(
                                            shimmerEffect(),
                                            RoundedCornerShape(8.dp),
                                        ),
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .clip(shape = RoundedCornerShape(18.dp))
                                    .background(DodamTheme.colors.backgroundNormal),
                            ) {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .size(width = 139.dp, height = 32.dp)
                                            .padding(top = 10.dp, start = 10.dp)
                                            .background(
                                                shimmerEffect(),
                                                RoundedCornerShape(8.dp),
                                            ),
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp)
                                        .padding(top = 12.dp),
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .height(48.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .background(
                                                    shimmerEffect(),
                                                    RoundedCornerShape(50.dp),
                                                ),
                                        )
                                        Box(
                                            modifier = Modifier
                                                .size(width = 71.dp, height = 27.dp)
                                                .padding(start = 8.dp)
                                                .background(
                                                    shimmerEffect(),
                                                    RoundedCornerShape(8.dp),
                                                ),
                                        )
                                        Spacer(modifier = Modifier.weight(1f))

                                        Box(
                                            modifier = Modifier
                                                .size(width = 71.dp, height = 27.dp)
                                                .background(
                                                    shimmerEffect(),
                                                    RoundedCornerShape(8.dp),
                                                ),
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Row(
                                        modifier = Modifier
                                            .height(48.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .background(
                                                    shimmerEffect(),
                                                    RoundedCornerShape(50.dp),
                                                ),
                                        )
                                        Box(
                                            modifier = Modifier
                                                .size(width = 71.dp, height = 27.dp)
                                                .padding(start = 8.dp)
                                                .background(
                                                    shimmerEffect(),
                                                    RoundedCornerShape(8.dp),
                                                ),
                                        )
                                        Spacer(modifier = Modifier.weight(1f))

                                        Box(
                                            modifier = Modifier
                                                .size(width = 71.dp, height = 27.dp)
                                                .background(
                                                    shimmerEffect(),
                                                    RoundedCornerShape(8.dp),
                                                ),
                                        )
                                    }
                                }
                            }
                        }
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

@Composable
private fun AskNightStudyCard(modifier: Modifier = Modifier, text: String, action: @Composable () -> Unit, onClick: () -> Unit) {
    Row(
        modifier = modifier.clickable(
            onClick = onClick,
            indication = rememberBounceIndication(),
            interactionSource = remember { MutableInteractionSource() },
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = DodamTheme.colors.labelAlternative,
            style = DodamTheme.typography.headlineMedium(),
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier.padding(vertical = 8.dp),
        ) {
            action()
        }
    }
}

private fun formatToMonthDay(dateStr: String): String {
    // dateStr 예: "2025-06-21"
    val parts = dateStr.split("-")
    if (parts.size != 3) return dateStr

    val month = parts[1].toIntOrNull() ?: return dateStr
    val day = parts[2].toIntOrNull() ?: return dateStr

    return "${month}월 ${day}일"
}

fun pad2(n: Int): String = if (n < 10) "0$n" else "$n"
