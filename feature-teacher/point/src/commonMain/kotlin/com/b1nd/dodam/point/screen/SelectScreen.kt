package com.b1nd.dodam.point.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamEmpty
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.point.getDodamSegment
import com.b1nd.dodam.point.model.PointLoadingUiState
import com.b1nd.dodam.point.model.PointStudentModel
import com.b1nd.dodam.ui.component.DodamMember
import com.b1nd.dodam.ui.component.DodamMemberLoadingCard
import com.b1nd.dodam.ui.icons.ColoredCheckmarkCircleFilled
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun SelectScreen(
    uiState: PointLoadingUiState,
    onClickStudent: (student: PointStudentModel) -> Unit,
    onClickNextPage: () -> Unit,
    reloadStudent: () -> Unit,
    popBackStack: () -> Unit,
) {
    var selectGrade by remember { mutableStateOf("전체") }
    var selectRoom by remember { mutableStateOf("전체") }
    val onSelectGrade: (String) -> Unit = {
        selectGrade = it
    }
    val onSelectRoom: (String) -> Unit = {
        selectRoom = it
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "상벌점 관리",
                onBackClick = popBackStack,
            )
        },
        bottomBar = {
            if (uiState is PointLoadingUiState.Success) {
                val selectUserCount = uiState.students.filter { it.selected }.size
                DodamButton(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .navigationBarsPadding()
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                    onClick = onClickNextPage,
                    text = "${selectUserCount}명 선택하기",
                    buttonRole = ButtonRole.Primary,
                    buttonSize = ButtonSize.Large,
                )
            }
        },
        containerColor = DodamTheme.colors.backgroundNormal,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            DodamSegmentedButton(
                modifier = Modifier.fillMaxWidth(),
                segments = persistentListOf(
                    getDodamSegment("전체", selectGrade, onSelectGrade),
                    getDodamSegment("1학년", selectGrade, onSelectGrade),
                    getDodamSegment("2학년", selectGrade, onSelectGrade),
                    getDodamSegment("3학년", selectGrade, onSelectGrade),
                ),
            )
            Spacer(modifier = Modifier.height(12.dp))
            DodamSegmentedButton(
                modifier = Modifier.fillMaxWidth(),
                segments = persistentListOf(
                    getDodamSegment("전체", selectRoom, onSelectRoom),
                    getDodamSegment("1반", selectRoom, onSelectRoom),
                    getDodamSegment("2반", selectRoom, onSelectRoom),
                    getDodamSegment("3반", selectRoom, onSelectRoom),
                    getDodamSegment("4반", selectRoom, onSelectRoom),
                ),
            )

            Spacer(modifier = Modifier.height(20.dp))

            when (uiState) {
                PointLoadingUiState.Error -> {
                    DodamEmpty(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = reloadStudent,
                        title = "학생 명단을 불러올 수 없어요.",
                        buttonText = "다시 불러오기",
                        border = BorderStroke(
                            width = 1.dp,
                            color = DodamTheme.colors.lineAlternative,
                        ),
                    )
                }
                is PointLoadingUiState.Loading -> {
                    DodamMemberLoadingCard()
                    Spacer(modifier = Modifier.height(12.dp))
                    DodamMemberLoadingCard()
                }
                is PointLoadingUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(
                            items = uiState
                                .students
                                .filter {
                                    val room = "${it.room}반"
                                    val grade = "${it.grade}학년"

                                    if (selectRoom == "전체" && selectGrade == "전체") {
                                        true
                                    } else if (selectRoom == room && (selectGrade == "전체" || selectGrade == grade)) {
                                        true
                                    } else if (selectGrade == grade && (selectRoom == "전체" || selectRoom == room)) {
                                        true
                                    } else {
                                        false
                                    }
                                },
                            key = { it.id },
                        ) {
                            DodamMember(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = rememberBounceIndication(),
                                        onClick = { onClickStudent(it) },
                                    ),
                                icon = it.profileImage,
                                name = it.name,
                                content = {
                                    if (it.selected) {
                                        Image(
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .size(24.dp),
                                            imageVector = ColoredCheckmarkCircleFilled,
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
