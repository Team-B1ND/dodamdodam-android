package com.b1nd.dodam.join_club

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamButtonDialog
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun JoinClubScreen(
    modifier: Modifier = Modifier,
    viewModel: JoinClubViewModel = koinViewModel(),
    popBackStack: () -> Unit,
) {
    JoinClubScreen(
        popBackStack = popBackStack,
        modifier = modifier
    )
}


@Composable
private fun JoinClubScreen(
    popBackStack: () -> Unit,
    modifier: Modifier,
) {
    var titleIndex by remember { mutableIntStateOf(0) }
    val firstClub = remember { mutableStateOf("") }
    val secondClub = remember { mutableStateOf("") }
    val thirdClub = remember { mutableStateOf("") }
    var showClubPicker by remember { mutableStateOf(false) }
    val free = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val text = listOf(
        "창체",
        "자율",
    )
    LaunchedEffect(key1 = titleIndex) {
        Log.d("selectedItem", "JoinClubScreen: $titleIndex")
    }
    if (showClubPicker) {
        Dialog(
            onDismissRequest = { showClubPicker = false },
        ) {
            DodamButtonDialog(
                confirmButton = {
                    showClubPicker = false
//                    viewModel.askOuting(
//                        reason = outingReason,
//                        startAt = LocalDateTime(outingDate, outingStartTime),
//                        endAt = LocalDateTime(outingDate, outingEndTime),
//                        isDinner = true,
//                    )
                },
                confirmButtonText = "신청 완료하기",
                confirmButtonRole = ButtonRole.Primary,
                dismissButton = {
                    showClubPicker = false
//                    viewModel.askOuting(
//                        reason = outingReason,
//                        startAt = LocalDateTime(outingDate, outingStartTime),
//                        endAt = LocalDateTime(outingDate, outingEndTime),
//                        isDinner = false,
//                    )
                },
                dismissButtonText = "취소",
                dismissButtonRole = ButtonRole.Assistive,
                title = "정말 확실합니까?",
                body = "창체동아리 : \n" +
                        "1지망 B1ND, \n" +
                        "2지망 ALT, \n" +
                        "3지망 DUKAMI\n" +
                        "\n" +
                        "자율동아리 : \n" +
                        "Drop, \n" +
                        "Draw !, \n" +
                        "InD, \n" +
                        "씨범자리뺏기\n" +
                        "위 동아리로 신청을 넣겠습니까?",
            )
        }
    }
    val item = List(2) { index: Int ->
        DodamSegment(
            selected = titleIndex == index,
            text = text[index],
            onClick = {
                titleIndex = index
                Log.d("doog", "JoinClubScreen: $titleIndex")
            },
        )
    }.toImmutableList()
    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "동아리 신청",
                onBackClick = popBackStack,
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp, start = 16.dp, top = 16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                DodamSegmentedButton(
                    segments = item
                )
                if (titleIndex == 1) {
                    ClubCard(
                        bind = false,
                        introduce = free.value,
                        onRemoveClick = {
                            free.value = ""
                        },
                        onIntroduceChange = {
                            free.value = it
                        }
                    )
                } else {
                    ClubCard(
                        num = 1,
                        bind = true,
                        introduce = firstClub.value,
                        onIntroduceChange = {
                            firstClub.value = it
                        },
                        modifier = modifier
                            .fillMaxWidth(),
                        onRemoveClick = {
                            firstClub.value = ""
                        }
                    )
                    ClubCard(
                        num = 2,
                        bind = true,
                        introduce = secondClub.value,
                        onIntroduceChange = {
                            secondClub.value = it
                        },
                        modifier = modifier
                            .fillMaxWidth(),
                        onRemoveClick = {
                            secondClub.value = ""
                        }
                    )
                    ClubCard(
                        num = 3,
                        bind = true,
                        introduce = thirdClub.value,
                        onIntroduceChange = {
                            thirdClub.value = it
                        },
                        modifier = modifier
                            .fillMaxWidth(),
                        onRemoveClick = {
                            thirdClub.value = ""
                        }
                    )
                    Spacer(Modifier.height(48.dp))
                }
            }
            DodamButton(
                onClick = {
                    showClubPicker = true
                },
                text = "동아리 입부 신청하기",
                enabled = firstClub.value != "" && secondClub.value != "" && thirdClub.value != "",
                modifier = modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun ClubCard(
    modifier: Modifier = Modifier,
    num: Int = 1,
    bind: Boolean,
    introduce: String,
    onIntroduceChange: (String) -> Unit,
    onRemoveClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = DodamTheme.colors.backgroundNormal,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Row {
            if (bind) {
                Text(
                    text = "${num}순위 : ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700),
                )
                Text(
                    text = "B1ND",
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF0083F0)
                )
            } else {
                Text(
                    text = "자율동아리 : ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700),
                )
                Text(
                    text = "InD",
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF0083F0)
                )
            }
        }
        Spacer(Modifier.height(14.dp))
        DodamTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = introduce,
            onValueChange = onIntroduceChange,
            singleLine = false,
            minLines = 6,
            maxLines = 8,
            label = "자기소개",
            onClickRemoveRequest = onRemoveClick
        )
        Spacer(Modifier.height(7.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = introduce.length.toString(),
                color = DodamTheme.colors.primaryNormal,
                fontSize = 16.sp
            )
            Text(
                text = "/300",
                color = DodamTheme.colors.labelAssistive,
                fontSize = 16.sp
            )
        }
    }
}
