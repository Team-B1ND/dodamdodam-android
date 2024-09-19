package com.b1nd.dodam.nightstudy

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDefaultTopAppBar
import com.b1nd.dodam.designsystem.component.DodamModalBottomSheet
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.nightstudy.state.DetailMember
import com.b1nd.dodam.nightstudy.state.NightStudyUiState
import com.b1nd.dodam.nightstudy.viewmodel.NightStudyViewModel
import com.b1nd.dodam.ui.component.DodamMember
import com.b1nd.dodam.ui.effect.shimmerEffect
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NightStudyScreen(viewModel: NightStudyViewModel = koinViewModel()) {
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
        "진행 중",
        "대기 중",
    )
    val item = List(2) { index: Int ->
        DodamSegment(
            selected = titleIndex == index,
            text = text[index],
            onClick = { titleIndex = index },
        )
    }.toImmutableList()

    var bottomSheet by remember { mutableStateOf(false) }
    var searchStudent by remember { mutableStateOf("") }

    var detailMember by remember { mutableStateOf(DetailMember()) }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.load()
    }

    Scaffold(
        topBar = {
            DodamDefaultTopAppBar(
                title = "심야 자습",
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
            if (bottomSheet) {
                DodamModalBottomSheet(
                    shape = RoundedCornerShape(
                        topStart = 28.dp,
                        topEnd = 28.dp,
                    ),
                    onDismissRequest = { bottomSheet = false },
                    title = {
                        Text(
                            text = "${detailMember.name}의 심야 자습 정보",
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
                                Text(text = "시작 날짜", style = DodamTheme.typography.headlineMedium(), color = DodamTheme.colors.labelAssistive)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = detailMember.startDay, style = DodamTheme.typography.headlineMedium(), color = DodamTheme.colors.labelNeutral)
                            }
                            Row(
                                modifier = Modifier.padding(bottom = 12.dp),
                            ) {
                                Text(text = "종료 날짜", style = DodamTheme.typography.headlineMedium(), color = DodamTheme.colors.labelAssistive)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = detailMember.endDay, style = DodamTheme.typography.headlineMedium(), color = DodamTheme.colors.labelNeutral)
                            }
                            Row(
                                modifier = Modifier.padding(bottom = 12.dp),
                            ) {
                                Text(text = "자습 장소", style = DodamTheme.typography.headlineMedium(), color = DodamTheme.colors.labelAssistive)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = detailMember.place, style = DodamTheme.typography.headlineMedium(), color = DodamTheme.colors.labelNeutral)
                            }
                            Row(
                                modifier = Modifier.padding(bottom = 12.dp),
                            ) {
                                Text(text = "학습 계획", style = DodamTheme.typography.headlineMedium(), color = DodamTheme.colors.labelAssistive)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = detailMember.content, style = DodamTheme.typography.headlineMedium(), color = DodamTheme.colors.labelNeutral)
                            }
                            if (detailMember.doNeedPhone) {
                                Row {
                                    Text(
                                        text = "휴대폰 사용",
                                        style = DodamTheme.typography.headlineMedium(),
                                        color = DodamTheme.colors.labelAssistive,
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = detailMember.reasonForPhone!!,
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
                                        viewModel.reject(detailMember.id)
                                        bottomSheet = false
                                        viewModel.load()
                                    },
                                    text = "거절하기",
                                    buttonSize = ButtonSize.Large,
                                    buttonRole = ButtonRole.Negative,
                                    modifier = Modifier.weight(1f),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                DodamButton(
                                    onClick = {
                                        viewModel.allow(detailMember.id)
                                        bottomSheet = false
                                        viewModel.load()
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                        .wrapContentHeight()
                        .clip(shape = RoundedCornerShape(18.dp))
                        .background(DodamTheme.colors.staticWhite),
                ) {
                    when (val data = uiState.nightStudyUiState) {
                        is NightStudyUiState.Success -> {
                            val members = if (titleIndex == 0) data.ingData else data.pendingData
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
                            Text(
                                text = if (titleIndex == 0) "심자 자습중인 학생" else "심자 대기중인 학생",
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
                                    DodamMember(
                                        name = filteredMemberList[listIndex].student.name ?: "",
                                        modifier = Modifier
                                            .padding(bottom = 12.dp),
                                        icon = null,
                                    ) {
                                        val currentDate = Clock.System.now().toLocalDateTime(
                                            TimeZone.currentSystemDefault(),
                                        ).date
                                        val end = filteredMemberList[listIndex].endAt.date

                                        val a = currentDate.daysUntil(end)

                                        val memberData = filteredMemberList[listIndex]
                                        val detailData = DetailMember(
                                            id = memberData.id,
                                            name = memberData.student.name,
                                            startDay = "${
                                                memberData.startAt.date.toString().split(
                                                    "-",
                                                )[1].toInt()}월 ${memberData.startAt.date.toString().split("-")[2].toInt()}일",
                                            endDay = "${
                                                memberData.endAt.date.toString().split(
                                                    "-",
                                                )[1].toInt()}월 ${memberData.endAt.date.toString().split("-")[2].toInt()}일",
                                            place = memberData.place,
                                            content = memberData.content,
                                            doNeedPhone = memberData.doNeedPhone,
                                            reasonForPhone = memberData.reasonForPhone,
                                        )

                                        if (titleIndex == 0) {
                                            Text(
                                                text = if (a == 1) "오늘 종료" else "${a}일 남음",
                                                style = DodamTheme.typography.headlineMedium(),
                                                color = if (a == 1) DodamTheme.colors.primaryNormal else DodamTheme.colors.labelAssistive,
                                            )
                                        } else {
                                            DodamButton(
                                                onClick = {
                                                    bottomSheet = true
                                                    detailMember = detailData
                                                },
                                                text = "승인하기",
                                                buttonSize = ButtonSize.Small,
                                                buttonRole = ButtonRole.Assistive,
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        NightStudyUiState.Error -> {}
                        NightStudyUiState.Loading -> {
                            Column(
                                modifier = Modifier
                                    .size(width = 360.dp, height = 166.dp),
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
                    }
                }
            }
        }
    }
}
