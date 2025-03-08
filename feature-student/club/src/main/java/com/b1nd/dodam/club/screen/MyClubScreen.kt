package com.b1nd.dodam.club.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.b1nd.dodam.club.MyClubViewModel
import com.b1nd.dodam.club.model.Club
import com.b1nd.dodam.club.model.ClubJoin
import com.b1nd.dodam.club.model.ClubState
import com.b1nd.dodam.club.model.JoinedClubUiState
import com.b1nd.dodam.club.model.MyClubSideEffect
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamButtonDialog
import com.b1nd.dodam.designsystem.component.DodamEmpty
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.component.DodamMemberLoadingCard
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MyClubScreen(
    modifier: Modifier = Modifier,
    viewModel: MyClubViewModel = koinViewModel(),
    popBackStack: () -> Unit,
    sideEffect: MyClubSideEffect,
    onNavigateToJoin: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getClub()
    }

    val state by viewModel.state.collectAsState()

    var joinedClubList = emptyList<Club>()
    var joinedSelfClubList = emptyList<Club>()
    var receivedClubList = emptyList<ClubJoin>()
    var createdClubList = emptyList<Club>()

    when (state.joinedClubUiState) {
        is JoinedClubUiState.Success -> {
            joinedClubList = (state.joinedClubUiState as JoinedClubUiState.Success).joinedClubList
            joinedSelfClubList = (state.joinedClubUiState as JoinedClubUiState.Success).joinedSelfClubList
            receivedClubList = state.receivedCLub
            createdClubList = (state.createdClubList)
        }
        else -> {
            DodamMemberLoadingCard()
        }
    }

    val joinedClubNameList = joinedClubList.map {
        it.name
    }
    val createdClubStateList = createdClubList.map {
        it.state
    }
    val joinedSelfClubNameList = joinedSelfClubList.map {
        it.name
    }
    val receivedClubNameList = receivedClubList.map {
        it.club.name
    }


    val lis = remember {
        mutableListOf("B1ND", "CNS", "DUCAMI")
    }

    val lis2 = remember {
        mutableListOf("자탄학", "inD", "Draw")
    }

    val showDialog = remember { mutableStateOf(false) }
    val showRejectDialog = remember { mutableStateOf(false) }
    val selectedSelfClubId = remember { mutableIntStateOf(0) }
    val selectedSelfClubName = remember { mutableStateOf("") }

    if (showDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false },
        ) {
            DodamButtonDialog(
                confirmButton = {
                    viewModel.acceptClub(selectedSelfClubId.intValue)
                    showDialog.value = false
                },
                dismissButton = {
                    showDialog.value = false
                },
                title = "${selectedSelfClubName.value}에\n입부 하시겠습니까?",
                confirmButtonText = "수락",
                dismissButtonText = "취소",
                body = "이 선택은 되돌릴 수 없습니다"
            )
        }
    }

    if (showRejectDialog.value) {
        Dialog(
            onDismissRequest = { showRejectDialog.value = false },
        ) {
            DodamButtonDialog(
                confirmButton = {
                    viewModel.rejectClub(selectedSelfClubId.intValue)
                    showRejectDialog.value = false
                },
                dismissButton = {
                    showRejectDialog.value = false
                },
                title = "정말 B1ND의 부원 제안을 거절하겠습니까?",
                confirmButtonText = "거절",
                dismissButtonText = "취소",
                body = "이 선택은 되돌릴 수 없습니다, 신중히 결정해주세요."
            )
        }
    }




    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DodamTheme.colors.backgroundNeutral),
        topBar = {
            Column {
                DodamTopAppBar(
                    modifier = Modifier
                        .statusBarsPadding()
                        .background(DodamTheme.colors.backgroundNeutral),
                    title = "MY",
                    onBackClick = popBackStack,
                )
            }
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .background(DodamTheme.colors.backgroundNeutral, RoundedCornerShape(16.dp)),
        ) {
            when (sideEffect) {

                MyClubSideEffect.NotExist -> {
                    DodamEmpty(
                        title = "아직 동아리에 신청하지 않았어요! \n" +
                                "신청 마감 : 2025. 03. 19.",
                        buttonText = "동아리 입부 신청하기",
                        onClick = {
                            onNavigateToJoin()
                        },
                    )
                }

                MyClubSideEffect.Apply -> {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(
                                color = DodamTheme.colors.backgroundNormal,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "내 신청",
                            fontSize = 18.sp,
                            fontWeight = FontWeight(700),
                            color = DodamTheme.colors.labelNormal
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = "창체",
                            fontSize = 12.sp,
                            color = DodamTheme.colors.labelNormal
                        )
                        Spacer(Modifier.height(8.dp))
                        for (item in lis) {
                            var n = 1
                            Row(
                                modifier = modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${n}지망",
                                    fontSize = 15.sp,
                                    color = DodamTheme.colors.labelNormal
                                )
                                Text(
                                    text = item,
                                    fontSize = 15.sp,
                                    color = DodamTheme.colors.labelNormal
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            n += 1
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "자율",
                            fontSize = 12.sp,
                            color = DodamTheme.colors.labelNormal
                        )
                        for (item in lis2) {
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = item,
                                fontSize = 15.sp,
                                color = DodamTheme.colors.labelNormal
                            )
                        }
                    }
                }

                MyClubSideEffect.Exist -> {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
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
                            Text(
                                text = "소속된 동아리",
                                fontSize = 18.sp,
                                color = DodamTheme.colors.labelNormal
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = "창체",
                                fontSize = 12.sp,
                                color = DodamTheme.colors.labelNormal
                            )
                            Spacer(Modifier.height(8.dp))
                            Row(
                                modifier = modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = joinedClubNameList[0],
                                    fontSize = 15.sp,
                                    color = DodamTheme.colors.labelNormal
                                )
                                Box(
                                    modifier = modifier
                                        .background(
                                            color = Color(0x330083F0),
                                            shape = RoundedCornerShape(size = 8.dp)
                                        )
                                        .padding(vertical = 4.dp, horizontal = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "입부 완료",
                                        color = Color(0xFF0083F0),
                                        fontSize = 12.sp,
                                    )
                                }
                            }
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = "자율",
                                fontSize = 12.sp,
                                color = DodamTheme.colors.labelNormal
                            )
                            Spacer(Modifier.height(8.dp))
                            Row(
                                modifier = modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                joinedSelfClubNameList.forEachIndexed { _, item ->
                                    Text(
                                        text = item,
                                        fontSize = 15.sp,
                                        color = DodamTheme.colors.labelNormal
                                    )
                                    Box(
                                        modifier = modifier
                                            .background(
                                                color = Color(0x330083F0),
                                                shape = RoundedCornerShape(size = 8.dp)
                                            )
                                            .padding(vertical = 4.dp, horizontal = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "입부 완료",
                                            color = Color(0xFF0083F0),
                                            fontSize = 12.sp,
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .background(
                                    color = DodamTheme.colors.backgroundNormal,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "내 개설 신청",
                                fontSize = 18.sp,
                                color = DodamTheme.colors.labelNormal
                            )
                            if (createdClubList.isNotEmpty()) {
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    text = "자율",
                                    fontSize = 12.sp,
                                    color = DodamTheme.colors.labelNormal
                                )
                                Spacer(Modifier.height(4.dp))
                            }
                            joinedSelfClubNameList.forEachIndexed { index, item ->
                                Spacer(Modifier.height(4.dp))
                                Row(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = item,
                                        fontSize = 15.sp,
                                        color = DodamTheme.colors.labelNormal
                                    )
                                    Icon(
                                        imageVector = createdClubStateList.getOrNull(index)
                                            ?.let { state ->
                                                when (state) {
                                                    ClubState.PENDING -> DodamIcons.CheckmarkCircle.value
                                                    ClubState.ALLOWED -> DodamIcons.CheckmarkCircleFilled.value
                                                    ClubState.REJECTED -> DodamIcons.XMarkCircle.value
                                                    else -> DodamIcons.Bell.value
                                                }
                                            } ?: DodamIcons.ExclamationMarkCircle.value,
                                        contentDescription = null,
                                    )
                                }
                            }

                        }

                        Spacer(Modifier.height(12.dp))

                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .background(
                                    color = DodamTheme.colors.backgroundNormal,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "받은 자율 부원 제안",
                                fontSize = 18.sp,
                                color = DodamTheme.colors.labelNormal
                            )
                            if (receivedClubList.isNotEmpty()) {
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    text = "자율",
                                    fontSize = 12.sp,
                                    color = DodamTheme.colors.labelNormal
                                )
                                Spacer(Modifier.height(4.dp))
                            }
                            receivedClubNameList.forEachIndexed { index, item ->
                                Spacer(Modifier.height(4.dp))
                                Row(
                                    modifier = modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = item,
                                        fontSize = 15.sp,
                                        color = DodamTheme.colors.labelNormal
                                    )
                                    Row {
                                        Icon(
                                            imageVector = DodamIcons.CheckmarkCircle.value,
                                            contentDescription = null,
                                            modifier = modifier
                                                .clickable {
                                                    showDialog.value = true
                                                    selectedSelfClubId.intValue =
                                                        receivedClubList[index].id
                                                    selectedSelfClubName.value = item
                                                }
                                        )
                                        Spacer(Modifier.width(16.dp))
                                        Icon(
                                            imageVector = DodamIcons.XMarkCircle.value,
                                            contentDescription = null,
                                            modifier = modifier
                                                .clickable {
                                                    showRejectDialog.value = true
                                                    selectedSelfClubId.intValue =
                                                        receivedClubList[index].id
                                                    selectedSelfClubName.value = item
                                                }
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