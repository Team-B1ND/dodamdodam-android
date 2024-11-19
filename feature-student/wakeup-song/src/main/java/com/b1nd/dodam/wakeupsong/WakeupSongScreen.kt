package com.b1nd.dodam.wakeupsong

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamButtonDialog
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.ui.effect.shimmerEffect
import com.b1nd.dodam.ui.util.NoInteractionSource
import com.b1nd.dodam.wakeupsong.model.WakeupSong
import com.b1nd.dodam.wakeupsong.viewmodel.Event
import com.b1nd.dodam.wakeupsong.viewmodel.WakeupSongViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WakeupSongScreen(
    onClickAddWakeupSong: () -> Unit,
    popBackStack: () -> Unit,
    showToast: (String, String) -> Unit,
    viewModel: WakeupSongViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is Event.DeleteWakeupSong -> {
                    showToast("SUCCESS", "기상송을 삭제했어요")
                    viewModel.getMyWakeupSongs()
                    viewModel.getPendingWakeupSongs()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.run {
            getPendingWakeupSongs()
            getMyWakeupSongs()
            getAllowedWakeupSongs()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DodamTheme.colors.backgroundNeutral),
        topBar = {
            DodamTopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .background(DodamTheme.colors.backgroundNeutral),
                title = "기상송",
                onBackClick = popBackStack,
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(DodamTheme.colors.backgroundNeutral, RoundedCornerShape(16.dp)),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    Text(
                        text = "오늘의 기상송",
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 10.dp)
                            .fillMaxWidth(),
                        style = DodamTheme.typography.headlineBold(),
                        color = DodamTheme.colors.labelNormal,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                if (!uiState.allowedWakeupSongs.isEmpty()) {
                    items(uiState.allowedWakeupSongs.size) { index ->
                        val allowedSong =
                            (uiState.allowedWakeupSongs)[index]
                        WakeupSongCard(
                            wakeupSong = allowedSong,
                            index = index + 1,
                            isMine = false,
                            selectedTabIndex = selectedTabIndex,
                        )
                    }
                } else {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "승인된 기상송이 없어요.",
                                modifier = Modifier
                                    .padding(vertical = 40.dp),
                                style = DodamTheme.typography.headlineBold(),
                                color = DodamTheme.colors.labelAssistive,
                            )
                        }
                    }
                }
                stickyHeader {
                    Column {
                        TabRow(
                            selectedTabIndex = selectedTabIndex,
                            modifier = Modifier
                                .fillMaxWidth(),
                            containerColor = DodamTheme.colors.backgroundNeutral,
                            indicator = { tabPositions ->
                                if (selectedTabIndex < tabPositions.size) {
                                    TabRowDefaults.SecondaryIndicator(
                                        Modifier
                                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                            .padding(horizontal = 8.dp)
                                            .clip(CircleShape),
                                        color = DodamTheme.colors.labelNormal,
                                    )
                                }
                            },
                        ) {
                            Tab(
                                selected = selectedTabIndex == 0,
                                onClick = {
                                    selectedTabIndex = 0
                                },
                                selectedContentColor = DodamTheme.colors.labelNormal,
                                unselectedContentColor = DodamTheme.colors.labelAssistive,
                                interactionSource = remember { NoInteractionSource() },
                            ) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(text = "대기중")
                                Spacer(modifier = Modifier.height(12.dp))
                            }

                            Tab(
                                selected = selectedTabIndex == 1,
                                onClick = {
                                    selectedTabIndex = 1
                                },
                                selectedContentColor = DodamTheme.colors.labelNormal,
                                unselectedContentColor = DodamTheme.colors.labelAssistive,
                                interactionSource = remember { NoInteractionSource() },
                            ) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(text = "MY")
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                val currentWakeupSong = if (selectedTabIndex == 0) {
                    uiState.pendingWakeupSongs
                } else {
                    uiState.myWakeupSongs
                }
                if (!uiState.isLoading) {
                    if (currentWakeupSong.isNotEmpty()) {
                        items(
                            items = currentWakeupSong,
                            key = { it.id },
                        ) { wakeupSong ->
                            WakeupSongCard(
                                wakeupSong = wakeupSong,
                                selectedTabIndex = selectedTabIndex,
                                isShimmer = uiState.isLoading,
                                isMine = selectedTabIndex == 1,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    } else {
                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = if (selectedTabIndex == 0) "대기중인 기상송이 없어요" else "신청한 기상송이 없어요",
                                style = DodamTheme.typography.headlineBold(),
                                color = DodamTheme.colors.labelAssistive,
                            )
                        }
                    }
                } else {
                    items(5) {
                        WakeupSongCard(
                            isShimmer = true,
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(150.dp))
                }
            }

            DodamButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 16.dp)
                    .align(Alignment.BottomCenter),
                onClick = onClickAddWakeupSong,
                text = "기상송 신청",
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WakeupSongCard(
    modifier: Modifier = Modifier,
    viewModel: WakeupSongViewModel = koinViewModel(),
    wakeupSong: WakeupSong? = null,
    index: Int? = null,
    selectedTabIndex: Int? = null,
    isShimmer: Boolean = false,
    isMine: Boolean = false,
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    val uriHandler = LocalUriHandler.current

    if (showDialog && !isShimmer && selectedTabIndex != null && wakeupSong != null && isMine) {
        Dialog(
            onDismissRequest = {
                showDialog = false
            },
        ) {
            DodamButtonDialog(
                confirmButton = {
                    viewModel.deleteWakeupSong(wakeupSong.id)
                    showDialog = false
                },
                dismissButton = {
                    showDialog = false
                },
                confirmButtonRole = ButtonRole.Negative,
                confirmButtonText = "삭제",
                title = "기상송을 삭제하시겠요?",
                body = wakeupSong.videoTitle,
            )
        }
    }
    if (wakeupSong != null && selectedTabIndex != null && !isShimmer) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .then(
                    if (isMine) {
                        Modifier.combinedClickable(
                            indication = rememberBounceIndication(),
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                uriHandler.openUri(wakeupSong.videoUrl)
                            },
                            onLongClick = {
                                showDialog = true
                            },
                        )
                    } else {
                        Modifier.combinedClickable(onClick = {
                            uriHandler.openUri(wakeupSong.videoUrl)
                        })
                    },
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    index?.let {
                        Text(
                            text = index.toString(),
                            style = DodamTheme.typography.labelBold(),
                            color = DodamTheme.colors.primaryNormal,
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }

                    AsyncImage(
                        modifier = Modifier
                            .height(70.dp)
                            .width(120.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        model = wakeupSong.thumbnail,
                        contentDescription = "profile_image",
                        contentScale = ContentScale.Crop,
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Row {
                            if (isMine) {
                                when (wakeupSong.status) {
                                    Status.PENDING -> {}
                                    Status.ALLOWED -> {
                                        Text(
                                            text = "(승인됨) ",
                                            style = DodamTheme.typography.body1Bold(),
                                            color = DodamTheme.colors.primaryNormal,
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                    }

                                    Status.REJECTED -> {
                                        Text(
                                            text = "(거절됨) ",
                                            style = DodamTheme.typography.body1Bold(),
                                            color = DodamTheme.colors.statusNegative,
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                    }
                                }
                            }
                            Text(
                                text = wakeupSong.videoTitle,
                                style = DodamTheme.typography.body1Bold(),
                                color = DodamTheme.colors.labelNormal,
                            )
                        }
                        Text(
                            text = wakeupSong.channelTitle,
                            modifier = Modifier
                                .basicMarquee(),
                            color = DodamTheme.colors.labelAlternative,
                            style = DodamTheme.typography.caption1Medium(),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Box(
                        modifier = Modifier
                            .height(70.dp)
                            .width(120.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(shimmerEffect()),
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(19.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(shimmerEffect()),
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(15.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(shimmerEffect()),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
