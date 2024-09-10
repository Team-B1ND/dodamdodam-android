package com.b1nd.dodam.nightstudy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.b1nd.dodam.nightstudy.state.DetailMember
import com.b1nd.dodam.nightstudy.state.NightStudyUiState
import com.b1nd.dodam.nightstudy.viewmodel.NightStudyViewModel
import com.b1nd.dodam.ui.component.DodamMember
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
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

    var bottomSheet by remember { mutableStateOf(false) }

    var detailMember by remember { mutableStateOf(DetailMember()) }

    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(key1 = true) {
        viewModel.load()
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
            if (bottomSheet){
                DodamModalBottomSheet(
                    onDismissRequest = {bottomSheet = false},
                    title = {
                        Text(
                            text = "${detailMember.name}의 심야 자습 정보",
                            style = DodamTheme.typography.heading1Bold(),
                            color = DodamTheme.colors.labelNormal,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    },
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(bottom = 12.dp)
                            ) {
                                Text(text = "시작 날짜", style = DodamTheme.typography.headlineMedium(), color = DodamTheme.colors.labelAssistive)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = detailMember.startDay, style = DodamTheme.typography.headlineMedium(), color = DodamTheme.colors.labelNeutral)
                            }
                            Row(
                                modifier = Modifier.padding(bottom = 12.dp)
                            ) {
                                Text(text = "종료 날짜", style = DodamTheme.typography.headlineMedium(), color = DodamTheme.colors.labelAssistive)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = detailMember.endDay, style = DodamTheme.typography.headlineMedium(), color = DodamTheme.colors.labelNeutral)
                            }
                            Row(
                                modifier = Modifier.padding(bottom = 12.dp)
                            ) {
                                Text(text = "자습 장소", style = DodamTheme.typography.headlineMedium(), color = DodamTheme.colors.labelAssistive)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = detailMember.place, style = DodamTheme.typography.headlineMedium(), color = DodamTheme.colors.labelNeutral)
                            }
                            Row(
                                modifier = Modifier.padding(bottom = 12.dp)
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
                                        color = DodamTheme.colors.labelAssistive
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = detailMember.reasonForPhone!!,
                                        style = DodamTheme.typography.headlineMedium(),
                                        color = DodamTheme.colors.labelNeutral
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                DodamButton(
                                    onClick = {
                                        viewModel.reject(detailMember.id)
                                    },
                                    text = "거절하기",
                                    buttonSize = ButtonSize.Large,
                                    buttonRole = ButtonRole.Assistive,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                DodamButton(
                                    onClick = {
                                        viewModel.allow(detailMember.id)
                                    },
                                    text = "승인하기",
                                    buttonSize = ButtonSize.Large,
                                    buttonRole = ButtonRole.Primary,
                                    modifier = Modifier.weight(1f)
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
                        .padding(vertical = 20.dp)
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
                            .padding(10.dp)
                    ) {
                        when (val data = if (titleIndex == 0) uiState.nightStudyUiState else uiState.nightStudyPendingUiState) {
                            is NightStudyUiState.Success -> {
                                val memberList = data.data
                                items(memberList.size) { listIndex ->
                                    DodamMember(
                                        name = memberList[listIndex]?.student?.name ?:"",
                                        modifier = Modifier.padding(bottom = 12.dp),
                                        icon = null
                                    ) {
                                        val start =
                                            memberList[listIndex]?.startAt?.date.toString().split("-")
                                        val end =
                                            memberList[listIndex]?.endAt?.date.toString().split("-")

                                        val a =
                                            if (end[2].toInt() > start[2].toInt()) {
                                                end[2].toInt() - start[2].toInt()
                                            } else {
                                                when (end[1].toInt()) {
                                                    1, 3, 5, 7, 8, 10, 12 -> {
                                                        (end[2].toInt() - 31) + start[2].toInt()
                                                    }

                                                    4, 6, 9, 11 -> {
                                                        (end[2].toInt() - 30) + start[2].toInt()
                                                    }

                                                    2 -> {
                                                        if (end[0].toInt() % 4 == 0 && (end[0].toInt() % 100 != 0 || end[0].toInt() % 400 == 0)) {
                                                            end[2].toInt() - 29 - start[2].toInt()
                                                        } else {
                                                            end[2].toInt() - 20 - start[2].toInt()
                                                        }
                                                    }
                                                    else -> {
                                                        0
                                                    }
                                                }
                                            }

                                        val memberData = memberList[listIndex]
                                        val detailData = DetailMember(
                                            id = memberData?.id ?:0,
                                            name = memberData?.student?.name ?:"",
                                            startDay = "${memberData?.startAt?.date.toString().split("-")[1].toInt()}월 ${memberData?.startAt?.date.toString().split("-")[2].toInt()}일",
                                            endDay =  "${memberData?.endAt?.date.toString().split("-")[1].toInt()}월 ${memberData?.endAt?.date.toString().split("-")[2].toInt()}일",
                                            place = memberData?.place ?:"",
                                            content = memberData?.content ?:"",
                                            doNeedPhone = memberData?.doNeedPhone ?: false,
                                            reasonForPhone = memberData?.reasonForPhone
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