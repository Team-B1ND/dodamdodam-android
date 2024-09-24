package com.b1nd.dodam.outing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OutScreen(viewModel: OutViewModel = koinViewModel(), navigateToApprove: (title: Int) -> Unit) {
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
        viewModel.load()
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
                                .fillMaxWidth()
                                .padding(vertical = 20.dp)
                                .clip(shape = RoundedCornerShape(18.dp))
                                .background(DodamTheme.colors.backgroundNormal),
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
                                            ),
                                    )
                                }
                            }
                        }
                    }

                    is OutPendingUiState.Success -> {
                        val cnt =
                            if (titleIndex == 0) data.outPendingCount else data.sleepoverPendingCount
                        val members = if (titleIndex == 0) data.outMembers else data.sleepoverMembers
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
                        }.filter { studentData ->
                            val minutesRemaining = remainingMinutes(studentData.endAt.time.toString())
                            minutesRemaining > 0
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(bottom = 60.dp)
                                .padding(vertical = 20.dp),
                        ) {
                            if (cnt != 0) {
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
                                            text = "${cnt}명 ",
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
                                            navigateToApprove(
                                                titleIndex,
                                            )
                                        },
                                        text = "승인하러 가기",
                                        buttonRole = ButtonRole.Assistive,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp)
                                            .padding(top = 12.dp, bottom = 16.dp),
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
                                        .background(DodamTheme.colors.backgroundNormal),

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
                                                filteredMemberList[listIndex].endAt.time.toString(),
                                            )
                                            val minutes = remainingMinutes(
                                                filteredMemberList[listIndex].endAt.time.toString(),
                                            )
                                            val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
                                            val time = currentDate.daysUntil(filteredMemberList[listIndex].endAt.date)
                                            DodamMember(
                                                name = filteredMemberList[listIndex].student.name,
                                                modifier = Modifier
                                                    .padding(bottom = 12.dp),
                                                icon = null,
                                            ) {
                                                Text(
                                                    text = if (titleIndex == 0) {
                                                        if (hours > 0) "${hours}시간 남음" else "${minutes}분 남음"
                                                    } else {
                                                        if (time > 1) "${time}일 남음" else "오늘 복귀"
                                                    },
                                                    style = DodamTheme.typography.headlineMedium(),
                                                    color = if (titleIndex == 0) {
                                                        if (hours > 0 || minutes > 30 || time > 1) {
                                                            DodamTheme.colors.labelAssistive
                                                        } else {
                                                            DodamTheme.colors.primaryNormal
                                                        }
                                                    } else {
                                                        if (time <= 1) {
                                                            DodamTheme.colors.primaryNormal
                                                        } else {
                                                            DodamTheme.colors.labelAssistive
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
            }
        }
    }
}

fun remainingHours(endTime: String): Int {
    val currentTime = Clock.System.now().toLocalDateTime(timeZone = TimeZone.currentSystemDefault()).time
    val endParts = endTime.split(":")

    val endHour = endParts[0].toInt()
    val endMinute = endParts[1].toInt()

    val currentTotalMinutes = currentTime.hour * 60 + currentTime.minute
    val endTotalMinutes = endHour * 60 + endMinute

    val totalEndMinutesAdjusted = if (endTotalMinutes < currentTotalMinutes) endTotalMinutes + 24 * 60 else endTotalMinutes
    val diffMinutes = totalEndMinutesAdjusted - currentTotalMinutes

    return diffMinutes / 60
}

fun remainingMinutes(endTime: String): Int {
    val currentTime = Clock.System.now().toLocalDateTime(timeZone = TimeZone.currentSystemDefault()).time
    val endParts = endTime.split(":")

    val endHour = endParts[0].toInt()
    val endMinute = endParts[1].toInt()

    val currentTotalMinutes = currentTime.hour * 60 + currentTime.minute
    val endTotalMinutes = endHour * 60 + endMinute

    val diffMinutes = if (endTotalMinutes < currentTotalMinutes) {
        // 현재 시간이 끝나는 시간을 넘기면 0분을 리턴
        0
    } else {
        endTotalMinutes - currentTotalMinutes
    }

    return diffMinutes % 60
}
