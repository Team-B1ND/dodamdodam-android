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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDefaultTopAppBar
import com.b1nd.dodam.designsystem.component.DodamEmpty
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.nightstudy.state.NightStudyUiState
import com.b1nd.dodam.nightstudy.viewmodel.NightStudyViewModel
import com.b1nd.dodam.ui.component.DodamMember
import com.b1nd.dodam.ui.effect.shimmerEffect
import com.b1nd.dodam.ui.util.addFocusCleaner
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun NightStudyScreen(viewModel: NightStudyViewModel = koinViewModel(), navigateToApproveStudy: () -> Unit) {
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

    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefresh,
        onRefresh = viewModel::refresh,
    )

    LaunchedEffect(key1 = true) {
        viewModel.load()
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

                    when (val data = uiState.nightStudyUiState) {
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
                                                val currentDate =
                                                    Clock.System.now().toLocalDateTime(
                                                        TimeZone.currentSystemDefault(),
                                                    ).date
                                                val end = filteredMemberList[index].endAt.date

                                                val a = currentDate.daysUntil(end)

                                                Text(
                                                    text = if (a <= 1) "오늘 종료" else "${a}일 남음",
                                                    style = DodamTheme.typography.headlineMedium(),
                                                    color = if (a <= 1) DodamTheme.colors.primaryNormal else DodamTheme.colors.labelAssistive,
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
            refreshing = uiState.isRefresh,
            state = pullRefreshState,
        )
    }
}
