package com.b1nd.dodam.outing

import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDefaultTopAppBar
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.outing.model.OutPendingUiState
import com.b1nd.dodam.outing.viewmodel.OutViewModel
import com.b1nd.dodam.ui.component.DodamMember
import com.b1nd.dodam.ui.effect.shimmerEffect
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.viewmodel.KoinViewModelFactory
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun OutScreen(
    viewModel: OutViewModel = koinViewModel()
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
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
//        viewModel.load()
    }

    Scaffold(
        topBar = {
            DodamDefaultTopAppBar(
                title = "외출/외박",
                modifier = Modifier.statusBarsPadding(),
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
                    OutPendingUiState.Error -> {}
                    OutPendingUiState.Loading -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp)
                                .clip(shape = RoundedCornerShape(18.dp))
                                .background(DodamTheme.colors.staticWhite)
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
                                .fillMaxWidth()
                                .padding(vertical = 20.dp)
                                .clip(shape = RoundedCornerShape(18.dp))
                                .background(DodamTheme.colors.staticWhite)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(width = 139.dp, height = 32.dp)
                                    .padding(top = 10.dp, start = 10.dp)
                                    .background(
                                        shimmerEffect(),
                                        RoundedCornerShape(8.dp),
                                    ),
                            )
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
                                            )
                                    )
                                }
                            }
                        }

                    }

                    is OutPendingUiState.Success -> {
                        val cnt =
                            if (titleIndex == 0) data.outPendingCount else data.sleepoverPendingCount
                        val members =
                            if (titleIndex == 0) data.outMembers else data.sleepoverMembers
                        var filteredMemberList = if (gradeIndex == 0 && roomIndex == 0) {
                            members
                        } else if (gradeIndex == 0 && roomIndex != 0) {
                            members.filter { studentData ->
                                studentData.student.room == roomIndex
                            }
                        } else if (gradeIndex != 0 && roomIndex == 0) {
                            members.filter { studentData ->
                                studentData.student.grade == gradeIndex
                            }
                        } else {
                            members.filter { studentData ->
                                studentData.student.grade == gradeIndex && studentData.student.room == roomIndex
                            }
                        }
                        if (searchStudent.isNotEmpty()) {
                            filteredMemberList = filteredMemberList.filter {
                                it.student.name.contains(searchStudent) == true
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp)
                                .clip(shape = RoundedCornerShape(18.dp))
                                .background(DodamTheme.colors.staticWhite)
                        ) {
                            if (cnt != 0) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(shape = RoundedCornerShape(18.dp))
                                        .background(DodamTheme.colors.staticWhite)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(start = 16.dp, top = 16.dp)
                                    ) {
                                        Text(
                                            text = "현재 ",
                                            color = DodamTheme.colors.labelStrong,
                                            style = DodamTheme.typography.headlineBold()
                                        )
                                        Text(
                                            text = "${cnt}명 ",
                                            color = DodamTheme.colors.primaryNormal,
                                            style = DodamTheme.typography.headlineBold()
                                        )
                                        Text(
                                            text = "승인 대기 중 ",
                                            color = DodamTheme.colors.labelStrong,
                                            style = DodamTheme.typography.headlineBold()
                                        )
                                    }

                                    DodamButton(
                                        onClick = {},
                                        text = "승인하러 가기",
                                        buttonRole = ButtonRole.Assistive,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp)
                                            .padding(top = 12.dp, bottom = 16.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                            if (members.size != 0) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .clip(shape = RoundedCornerShape(18.dp))
                                        .background(DodamTheme.colors.staticWhite),
                                ) {
                                    Text(
                                        text = if (titleIndex == 0) "외출 중인 학생" else "외박 중인 학생",
                                        color = DodamTheme.colors.labelStrong,
                                        style = DodamTheme.typography.headlineBold(),
                                        modifier = Modifier
                                            .padding(top = 16.dp, start = 16.dp, bottom = 16.dp),
                                    )
                                    LazyColumn(
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp),
                                    ) {
                                        items(filteredMemberList.size) { listIndex ->
                                            val hours = remainingHours(
                                                filteredMemberList[listIndex].startAt.time.toString(),
                                                filteredMemberList[listIndex].endAt.time.toString()
                                            )
                                            val minutes = remainingMinutes(
                                                filteredMemberList[listIndex].startAt.time.toString(),
                                                filteredMemberList[listIndex].endAt.time.toString()
                                            )
                                            val time = calculateDaysBetween(
                                                filteredMemberList[listIndex].startAt.date.toString(),
                                                filteredMemberList[listIndex].endAt.date.toString()
                                            )
                                            DodamMember(
                                                name = filteredMemberList[listIndex].student.name,
                                                modifier = Modifier
                                                    .padding(bottom = 12.dp),
                                                icon = null,
                                            ) {
                                                Text(
                                                    text = if (titleIndex == 0) if (hours > 0) "${hours}시간 남음" else "${minutes}분 남음" else if (time > 1) "${time}일 남음" else "오늘 복귀",
                                                    style = DodamTheme.typography.headlineMedium(),
                                                    color = if (hours > 0 || minutes > 30 || time > 1) DodamTheme.colors.labelAssistive else DodamTheme.colors.primaryNormal,
                                                )

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


fun remainingHours(startTime: String, endTime: String): Int {
    val startParts = startTime.split(":")
    val endParts = endTime.split(":")

    val startHour = startParts[0].toInt()
    val startMinute = startParts[1].toInt()

    val endHour = endParts[0].toInt()
    val endMinute = endParts[1].toInt()

    val startTotalMinutes = startHour * 60 + startMinute
    val endTotalMinutes = endHour * 60 + endMinute

    val totalEndMinutesAdjusted =
        if (endTotalMinutes < startTotalMinutes) endTotalMinutes + 24 * 60 else endTotalMinutes
    val diffMinutes = totalEndMinutesAdjusted - startTotalMinutes

    return diffMinutes / 60
}

fun remainingMinutes(startTime: String, endTime: String): Int {
    val startParts = startTime.split(":")
    val endParts = endTime.split(":")

    val startHour = startParts[0].toInt()
    val startMinute = startParts[1].toInt()

    val endHour = endParts[0].toInt()
    val endMinute = endParts[1].toInt()

    val startTotalMinutes = startHour * 60 + startMinute
    val endTotalMinutes = endHour * 60 + endMinute

    val totalEndMinutesAdjusted =
        if (endTotalMinutes < startTotalMinutes) endTotalMinutes + 24 * 60 else endTotalMinutes
    val diffMinutes = totalEndMinutesAdjusted - startTotalMinutes

    return diffMinutes % 60
}

fun calculateDaysBetween(startDate: String, endDate: String): Int {
    val monthDays = mapOf(
        1 to 31, 2 to 28, 3 to 31, 4 to 30, 5 to 31, 6 to 30,
        7 to 31, 8 to 31, 9 to 30, 10 to 31, 11 to 30, 12 to 31
    )

    fun isLeapYear(year: Int) = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))

    fun daysInMonth(year: Int, month: Int): Int {
        return if (month == 2 && isLeapYear(year)) 29 else monthDays[month] ?: 30
    }

    val startParts = startDate.split("-").map { it.toInt() }
    val endParts = endDate.split("-").map { it.toInt() }

    val startYear = startParts[0]
    val startMonth = startParts[1]
    val startDay = startParts[2]

    val endYear = endParts[0]
    val endMonth = endParts[1]
    val endDay = endParts[2]

    // 날짜 차이 계산
    var totalDays = 0

    if (startYear == endYear) {
        if (startMonth == endMonth) {
            totalDays = endDay - startDay
        } else {
            totalDays += daysInMonth(startYear, startMonth) - startDay
            for (month in (startMonth + 1) until endMonth) {
                totalDays += daysInMonth(startYear, month)
            }
            totalDays += endDay
        }
    } else {
        // 시작 연도의 남은 일 수
        totalDays += daysInMonth(startYear, startMonth) - startDay
        for (month in (startMonth + 1)..12) {
            totalDays += daysInMonth(startYear, month)
        }

        // 중간 연도 일 수
        for (year in (startYear + 1) until endYear) {
            totalDays += if (isLeapYear(year)) 366 else 365
        }

        // 종료 연도의 일 수
        for (month in 1 until endMonth) {
            totalDays += daysInMonth(endYear, month)
        }
        totalDays += endDay
    }

    return totalDays
}