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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDefaultTopAppBar
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.ui.component.DodamMember
import kotlinx.collections.immutable.toImmutableList


@Composable
fun OutingScreen() {
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
                Column(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(18.dp))
                            .background(DodamTheme.colors.staticWhite)
                    ){
                        Row(
                            modifier = Modifier
                                .padding(start = 16.dp, top = 16.dp)
                        ) {
                            Text(
                                text = "현재 ",
                                color = DodamTheme.colors.labelStrong,
                                style = DodamTheme.typography.headlineBold())
                            Text(
                                text = "12명 ",
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
                    Column(
                        modifier = Modifier
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
                            items(500) { listIndex ->
                                DodamMember(
                                    name = "학준혁",
                                    modifier = Modifier
                                        .padding(bottom = 12.dp),
                                    icon = null,
                                ) {
                                    Text(
                                        text = "30분 남음",
                                        style = DodamTheme.typography.headlineMedium(),
                                        color = DodamTheme.colors.primaryNormal,
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