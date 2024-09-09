package com.b1nd.dodam.nightstudy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDefaultTopAppBar
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.ui.component.DodamCard
import com.b1nd.dodam.ui.component.UserItem
import kotlinx.collections.immutable.toImmutableList

@Composable
fun NightStudyScreen() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val texts = listOf(
        "First",
        "Second",
        "Third",
        "Fourth",
        "Fifth",
    )
    val items = List(5) { index ->
        DodamSegment(
            selected = selectedIndex == index,
            text = texts[index],
            onClick = { selectedIndex = index },
        )
    }.toImmutableList()

    var titleIndex by remember { mutableIntStateOf(0) }
    val text = listOf(
        "심자 진행 중",
        "심자 대기 중"
    )
    val item = List(2){ index: Int ->
        DodamSegment(
            selected = titleIndex == index,
            text = text[index],
            onClick = { titleIndex = index }
        )
    }.toImmutableList()

    val dummy1 = listOf("병준1", "병준2", "병준3", "병준4")
    val dummy2 = listOf("병준5", "병준6")

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
                        segments = items,
                        modifier = Modifier.padding(top = 12.dp),
                    )
                    DodamSegmentedButton(
                        segments = items,
                        modifier = Modifier.padding(top = 12.dp),
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 20.dp)
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
                            .padding(horizontal = 10.dp)
                            .padding(bottom = 10.dp)
                    ) {
                        items(if (titleIndex == 0) dummy1.size else dummy2.size) { listIndex ->
                            UserItem(
                                userName = if (titleIndex == 0) dummy1[listIndex] else dummy2[listIndex],
                            ) {
                                if (titleIndex == 0) {
                                    Text(
                                        text = "14일 남음",
                                        style = DodamTheme.typography.headlineMedium(),
                                        color = DodamTheme.colors.labelAssistive,
                                    )
                                }else{
                                    DodamButton(
                                        onClick = {},
                                        text = "승인하기",
                                        buttonSize = ButtonSize.Small,
                                        buttonRole = ButtonRole.Assistive
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