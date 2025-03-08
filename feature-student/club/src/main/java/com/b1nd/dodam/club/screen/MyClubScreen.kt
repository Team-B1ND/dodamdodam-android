package com.b1nd.dodam.club.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.b1nd.dodam.club.MyClubViewModel
import com.b1nd.dodam.club.model.Club
import com.b1nd.dodam.club.model.ClubJoin
import com.b1nd.dodam.club.model.ClubMyJoined
import com.b1nd.dodam.club.model.ClubState
import com.b1nd.dodam.club.model.JoinedClubUiState
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamButtonDialog
import com.b1nd.dodam.designsystem.component.DodamEmpty
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.component.DodamMemberLoadingCard
import com.b1nd.dodam.ui.effect.shimmerEffect
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MyClubScreen(
    modifier: Modifier = Modifier,
    viewModel: MyClubViewModel = koinViewModel(),
    popBackStack: () -> Unit,
    onNavigateToJoin: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getClub()
        viewModel.getJoinRequest()
    }

    val state by viewModel.state.collectAsState()

    val scrollState = rememberScrollState()

    var joinedClubList = emptyList<ClubMyJoined>()
    var joinedSelfClubList = emptyList<ClubMyJoined>()
    var receivedClubList = emptyList<ClubJoin>()
    var createdClubList = emptyList<Club>()
    var createdSelfClubList = emptyList<Club>()

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            viewModel.getClub()
            viewModel.getJoinRequest()
            isRefreshing = false
        },
    )

    when (state.joinedClubUiState) {
        is JoinedClubUiState.Success -> {
            joinedClubList = (state.joinedClubUiState as JoinedClubUiState.Success).joinedClubList
            joinedSelfClubList =
                (state.joinedClubUiState as JoinedClubUiState.Success).joinedSelfClubList
            receivedClubList = state.receivedCLub
            createdClubList = (state.createdClubList)
            createdSelfClubList = (state.createdSelfClubList)
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
    val createdSelfClubStateList = createdSelfClubList.map {
        it.state
    }
    val joinedSelfClubNameList = joinedSelfClubList.map {
        it.name
    }
    val receivedClubNameList = receivedClubList.map {
        it.club.name
    }

    val showDialog = remember { mutableStateOf(false) }
    val showRejectDialog = remember { mutableStateOf(false) }
    val selectedSelfClubId = remember { mutableIntStateOf(0) }
    val selectedSelfClubName = remember { mutableStateOf("") }

    val uriHandler = LocalUriHandler.current
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
                body = "이 선택은 되돌릴 수 없습니다",
            )
        }
    }

    val showClubDialog = remember { mutableStateOf(false) }

    if (showClubDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false },
        ) {
            DodamButtonDialog(
                confirmButton = {
                    uriHandler.openUri("https://dodam.b1nd.com/club")
                    showClubDialog.value = false
                },
                dismissButton = {
                    showClubDialog.value = false
                },
                title = "추가 신청은 웹에서 해주세요",
                confirmButtonText = "수락",
                dismissButtonText = "취소",
                body = "도담도담 웹으로 추가 신청 하러 가기",
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
                title = "정말 ${selectedSelfClubName.value}의 부원 제안을 거절하겠습니까?",
                confirmButtonText = "거절",
                dismissButtonText = "취소",
                body = "이 선택은 되돌릴 수 없습니다, 신중히 결정해주세요.",
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
            Column(
                modifier = modifier
                    .verticalScroll(scrollState),
            ) {
                when (state.joinedClubUiState) {
                    is JoinedClubUiState.Success -> {
                        DodamEmpty(
                            title = if (joinedClubList.isEmpty()) {
                                "아직 동아리에 신청하지 않았어요! \n" +
                                    "신청 마감 : 2025. 03. 19."
                            } else {
                                "신청 마감 : 2025. 03. 19."
                            },
                            buttonText = "동아리 입부 신청하기",
                            onClick = {
                                if (joinedClubList.isNotEmpty() || state.requestJoinClub.isNotEmpty() || state.requestJoinSelfClub.isNotEmpty()) {
                                    showClubDialog.value = true
                                } else {
                                    onNavigateToJoin()
                                }
                            },
                        )
                        Spacer(Modifier.height(12.dp))
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .background(
                                    color = DodamTheme.colors.backgroundNormal,
                                    shape = RoundedCornerShape(12.dp),
                                )
                                .padding(16.dp),
                        ) {
                            Text(
                                text = "소속된 동아리",
                                style = DodamTheme.typography.headlineBold(),
                                color = DodamTheme.colors.labelNormal,
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = "창체",
                                style = DodamTheme.typography.caption2Bold(),
                                color = DodamTheme.colors.labelNormal,
                            )
                            Spacer(Modifier.height(8.dp))
                            Row(
                                modifier = modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                if (joinedClubNameList.isNotEmpty()) {
                                    Text(
                                        text = joinedClubNameList[0],
                                        style = DodamTheme.typography.body2Medium(),
                                        color = DodamTheme.colors.labelNormal,
                                    )
                                    Box(
                                        modifier = modifier
                                            .background(
                                                color = DodamTheme.colors.primaryAssistive,
                                                shape = RoundedCornerShape(size = 8.dp),
                                            )
                                            .padding(vertical = 4.dp, horizontal = 8.dp),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = "입부 완료",
                                            style = DodamTheme.typography.caption2Bold(),
                                            color = DodamTheme.colors.primaryNormal,
                                        )
                                    }
                                } else {
                                    Text(
                                        text = "입부한 창체 동아리가 없습니다",
                                        style = DodamTheme.typography.labelRegular(),
                                        color = DodamTheme.colors.labelNormal,
                                    )
                                }
                            }
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = "자율",
                                style = DodamTheme.typography.caption2Bold(),
                                color = DodamTheme.colors.labelNormal,
                            )
                            Spacer(Modifier.height(8.dp))
                            Column(
                                modifier = modifier
                                    .fillMaxWidth(),
                            ) {
                                if (joinedSelfClubNameList.isNotEmpty()) {
                                    joinedSelfClubNameList.forEachIndexed { _, item ->
                                        Text(
                                            text = item,
                                            style = DodamTheme.typography.body2Medium(),
                                            color = DodamTheme.colors.labelNormal,
                                        )
                                    }
                                    Spacer(Modifier.height(8.dp))
                                } else {
                                    Text(
                                        text = "입부한 자율 동아리가 없습니다",
                                        style = DodamTheme.typography.labelRegular(),
                                        color = DodamTheme.colors.labelNormal,
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
                                    shape = RoundedCornerShape(12.dp),
                                )
                                .padding(16.dp),
                        ) {
                            Text(
                                text = "내 신청",
                                style = DodamTheme.typography.headlineBold(),
                                color = DodamTheme.colors.labelNormal,
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = "창체",
                                style = DodamTheme.typography.caption2Bold(),
                                color = DodamTheme.colors.labelNormal,
                            )
                            Spacer(Modifier.height(8.dp))
                            if (state.requestJoinClub.isNotEmpty()) {
                                state.requestJoinClub.forEachIndexed { index, item ->
                                    Row(
                                        modifier = modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        Text(
                                            text = "${index + 1}지망",
                                            style = DodamTheme.typography.body2Medium(),
                                            color = DodamTheme.colors.labelNormal,
                                        )
                                        Text(
                                            text = item.club.name,
                                            style = DodamTheme.typography.body2Medium(),
                                            color = DodamTheme.colors.labelNormal,
                                        )
                                    }
                                    Spacer(Modifier.height(8.dp))
                                }
                            } else {
                                Text(
                                    text = "신청한 창체 동아리가 없습니다",
                                    style = DodamTheme.typography.labelRegular(),
                                    color = DodamTheme.colors.labelNormal,
                                )
                            }
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = "자율",
                                style = DodamTheme.typography.caption2Bold(),
                                color = DodamTheme.colors.labelNormal,
                            )
                            if (state.requestJoinSelfClub.isNotEmpty()) {
                                state.requestJoinSelfClub.forEachIndexed { _, item ->
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        text = item.club.name,
                                        style = DodamTheme.typography.body2Medium(),
                                        color = DodamTheme.colors.labelNormal,
                                    )
                                }
                            } else {
                                Text(
                                    text = "신청한 자율 동아리가 없습니다",
                                    style = DodamTheme.typography.labelRegular(),
                                    color = DodamTheme.colors.labelNormal,
                                )
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        Column(
                            modifier = modifier
                                .fillMaxWidth(),
                        ) {
                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = DodamTheme.colors.backgroundNormal,
                                        shape = RoundedCornerShape(12.dp),
                                    )
                                    .padding(16.dp),
                            ) {
                                Text(
                                    text = "내 개설 신청",
                                    style = DodamTheme.typography.headlineBold(),
                                    color = DodamTheme.colors.labelNormal,
                                )
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    text = "창체",
                                    style = DodamTheme.typography.caption2Bold(),
                                    color = DodamTheme.colors.labelNormal,
                                )
                                Spacer(Modifier.height(8.dp))
                                if (createdClubList.isNotEmpty()) {
                                    createdClubList.forEachIndexed { index, item ->
                                        Row(
                                            modifier = modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                        ) {
                                            Text(
                                                text = item.name,
                                                style = DodamTheme.typography.body2Medium(),
                                                color = DodamTheme.colors.labelNormal,
                                            )
                                            Image(
                                                imageVector = createdClubStateList.getOrNull(
                                                    index,
                                                )
                                                    ?.let { state ->
                                                        when (state) {
                                                            ClubState.PENDING -> DodamIcons.Clock.value
                                                            ClubState.ALLOWED -> DodamIcons.CheckmarkCircleFilled.value
                                                            ClubState.REJECTED -> DodamIcons.XMarkCircle.value
                                                            else -> DodamIcons.Bell.value
                                                        }
                                                    } ?: DodamIcons.ExclamationMarkCircle.value,
                                                contentDescription = null,
                                                colorFilter = createdClubStateList.getOrNull(
                                                    index,
                                                )
                                                    ?.let { state ->
                                                        when (state) {
                                                            ClubState.PENDING -> ColorFilter.tint(DodamTheme.colors.statusCautionary)
                                                            ClubState.ALLOWED -> ColorFilter.tint(DodamTheme.colors.primaryNormal)
                                                            ClubState.REJECTED -> ColorFilter.tint(DodamTheme.colors.statusNegative)

                                                            else -> ColorFilter.tint(DodamTheme.colors.statusCautionary)
                                                        }
                                                    } ?: ColorFilter.tint(DodamTheme.colors.statusCautionary),
                                            )
                                        }
                                        Spacer(Modifier.height(8.dp))
                                    }
                                } else {
                                    Text(
                                        text = "개설 신청한 창체 동아리가 없습니다",
                                        style = DodamTheme.typography.body2Medium(),
                                        color = DodamTheme.colors.labelNormal,
                                    )
                                }
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    text = "자율",
                                    style = DodamTheme.typography.caption2Bold(),
                                    color = DodamTheme.colors.labelNormal,
                                )
                                Spacer(Modifier.height(8.dp))
                                if (createdSelfClubList.isNotEmpty()) {
                                    createdSelfClubList.forEachIndexed { index, item ->
                                        Row(
                                            modifier = modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                        ) {
                                            Text(
                                                text = item.name,
                                                style = DodamTheme.typography.body2Medium(),
                                                color = DodamTheme.colors.labelNormal,
                                            )
                                            Image(
                                                imageVector = createdClubStateList.getOrNull(
                                                    index,
                                                )
                                                    ?.let { state ->
                                                        when (state) {
                                                            ClubState.PENDING -> DodamIcons.Clock.value
                                                            ClubState.ALLOWED -> DodamIcons.CheckmarkCircleFilled.value
                                                            ClubState.REJECTED -> DodamIcons.XMarkCircle.value
                                                            else -> DodamIcons.Bell.value
                                                        }
                                                    } ?: DodamIcons.ExclamationMarkCircle.value,
                                                contentDescription = null,
                                                colorFilter = createdClubStateList.getOrNull(
                                                    index,
                                                )
                                                    ?.let { state ->
                                                        when (state) {
                                                            ClubState.PENDING -> ColorFilter.tint(DodamTheme.colors.statusCautionary)
                                                            ClubState.ALLOWED -> ColorFilter.tint(DodamTheme.colors.primaryNormal)
                                                            ClubState.REJECTED -> ColorFilter.tint(DodamTheme.colors.statusNegative)

                                                            else -> ColorFilter.tint(DodamTheme.colors.statusCautionary)
                                                        }
                                                    } ?: ColorFilter.tint(DodamTheme.colors.statusCautionary),
                                            )
                                        }
                                        Spacer(Modifier.height(8.dp))
                                    }
                                } else {
                                    Text(
                                        text = "개설 신청한 자율 동아리가 없습니다",
                                        style = DodamTheme.typography.body2Medium(),
                                        color = DodamTheme.colors.labelNormal,
                                    )
                                }
                            }

                            Spacer(Modifier.height(12.dp))

                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = DodamTheme.colors.backgroundNormal,
                                        shape = RoundedCornerShape(12.dp),
                                    )
                                    .padding(16.dp),
                            ) {
                                Text(
                                    text = "받은 자율 부원 제안",
                                    style = DodamTheme.typography.headlineBold(),
                                    color = DodamTheme.colors.labelNormal,
                                )
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    text = "자율",
                                    style = DodamTheme.typography.caption2Bold(),
                                    color = DodamTheme.colors.labelNormal,
                                )
                                Spacer(Modifier.height(8.dp))
                                if (receivedClubList.isNotEmpty()) {
                                    receivedClubNameList.forEachIndexed { index, item ->
                                        Row(
                                            modifier = modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                        ) {
                                            Text(
                                                text = item,
                                                style = DodamTheme.typography.body2Medium(),
                                                color = DodamTheme.colors.labelNormal,
                                            )
                                            Row {
                                                Image(
                                                    imageVector = DodamIcons.CheckmarkCircle.value,
                                                    contentDescription = null,
                                                    modifier = modifier
                                                        .clickable {
                                                            showDialog.value = true
                                                            selectedSelfClubId.intValue =
                                                                receivedClubList[index].id
                                                            selectedSelfClubName.value = item
                                                        },
                                                    colorFilter = ColorFilter.tint(DodamTheme.colors.primaryNormal),
                                                )
                                                Spacer(Modifier.width(16.dp))
                                                Image(
                                                    imageVector = DodamIcons.XMarkCircle.value,
                                                    contentDescription = null,
                                                    modifier = modifier
                                                        .clickable {
                                                            showRejectDialog.value = true
                                                            selectedSelfClubId.intValue =
                                                                receivedClubList[index].id
                                                            selectedSelfClubName.value = item
                                                        },
                                                    colorFilter = ColorFilter.tint(DodamTheme.colors.statusNegative),
                                                )
                                            }
                                        }
                                        Spacer(Modifier.height(8.dp))
                                    }
                                } else {
                                    Text(
                                        text = "받은 부원 제안이 없습니다",
                                        style = DodamTheme.typography.labelRegular(),
                                        color = DodamTheme.colors.labelNormal,
                                    )
                                }
                            }
                            Spacer(Modifier.height(20.dp))
                        }
                    }

                    else -> {
                        DodamLoadingClub(160)
                        DodamLoadingClub(210)
                        DodamLoadingClub(200)
                        DodamLoadingClub(400)
                        DodamLoadingClub(200)
                    }
                }
            }
        }
    }
}

@Composable
private fun DodamLoadingClub(height: Int) {
    Spacer(modifier = Modifier.height(12.dp))
    Box(modifier = Modifier.fillMaxWidth().height(height.dp).background(shape = RoundedCornerShape(12.dp), brush = shimmerEffect()))
    Spacer(modifier = Modifier.height(12.dp))
}
