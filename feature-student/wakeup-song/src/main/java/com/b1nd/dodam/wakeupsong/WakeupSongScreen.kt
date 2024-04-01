package com.b1nd.dodam.wakeupsong

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.b1nd.dodam.dds.component.DodamDialog
import com.b1nd.dodam.dds.component.DodamSmallTopAppBar
import com.b1nd.dodam.dds.component.DodamToast
import com.b1nd.dodam.dds.component.button.DodamCTAButton
import com.b1nd.dodam.dds.component.button.DodamLargeFilledButton
import com.b1nd.dodam.dds.foundation.DodamColor
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.BodyMedium
import com.b1nd.dodam.dds.style.CheckmarkCircleFilledIcon
import com.b1nd.dodam.dds.style.LabelLarge
import com.b1nd.dodam.dds.style.TitleLarge
import com.b1nd.dodam.dds.style.TitleMedium
import com.b1nd.dodam.ui.util.NoInteractionSource
import com.b1nd.dodam.wakeupsong.model.WakeupSong
import com.b1nd.dodam.wakeupsong.viewmodel.Event
import com.b1nd.dodam.wakeupsong.viewmodel.WakeupSongViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WakeupSongScreen(onClickAddWakeupSong: () -> Unit, popBackStack: () -> Unit, viewModel: WakeupSongViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is Event.DeleteWakeupSong -> {
                    snackbarHostState.showSnackbar("기상송을 삭제했어요")
                    viewModel.getMyWakeupSong()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            DodamSmallTopAppBar(
                title = {
                    BodyLarge(text = "기상송")
                },
                onNavigationIconClick = popBackStack,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Column {
                    DodamToast(text = it.visuals.message, trailingIcon = {
                        CheckmarkCircleFilledIcon(
                            modifier = Modifier
                                .size(20.dp)
                                .drawBehind {
                                    drawRoundRect(
                                        color = DodamColor.White,
                                        topLeft = Offset(12f, 12f),
                                        size = Size(30f, 30f),
                                    )
                                },
                        )
                    })
                    Spacer(modifier = Modifier.height(90.dp))
                }
            }
        },

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    TitleMedium(
                        text = "내일의 기상송",
                        modifier = Modifier
                            .padding(top = 10.dp, start = 16.dp)
                            .fillMaxWidth(),
                    )
                }
                if (!uiState.allowedWakeupSongs.isEmpty()) {
                    items(uiState.allowedWakeupSongs.size) { index ->
                        val allowedSong =
                            (uiState.allowedWakeupSongs)[index]
                        WakeupSongCard(
                            wakeupSong = allowedSong,
                            index = index + 1,
                            selectedTabIndex = selectedTabIndex,
                        )
                    }
                } else {
                    item {
                        LabelLarge(
                            text = "승인된 기상송이 없어요.",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 40.dp),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.tertiary,
                        )
                    }
                }
                stickyHeader {
                    Column {
                        TabRow(
                            selectedTabIndex = selectedTabIndex,
                            modifier = Modifier
                                .fillMaxWidth(),
                            containerColor = MaterialTheme.colorScheme.background,
                            indicator = { tabPositions ->
                                if (selectedTabIndex < tabPositions.size) {
                                    TabRowDefaults.SecondaryIndicator(
                                        Modifier
                                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                            .padding(horizontal = 24.dp)
                                            .clip(CircleShape),
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                }
                            },
                        ) {
                            Tab(
                                selected = selectedTabIndex == 0,
                                onClick = {
                                    selectedTabIndex = 0
                                },
                                selectedContentColor = MaterialTheme.colorScheme.onSurface,
                                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
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
                                selectedContentColor = MaterialTheme.colorScheme.onSurface,
                                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
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

                if (currentWakeupSong.isNotEmpty()) {
                    items(
                        items = currentWakeupSong,
                        key = { it.id },
                    ) { wakeupSong ->
                        WakeupSongCard(
                            wakeupSong = wakeupSong,
                            selectedTabIndex = selectedTabIndex,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                } else {
                    item {
                        LabelLarge(
                            text = if (selectedTabIndex == 0) "대기중인 기상송이 없어요" else "신청한 기상송이 없어요",
                            color = MaterialTheme.colorScheme.tertiary,
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }

            DodamCTAButton(
                onClick = onClickAddWakeupSong,
                showBackground = true,
                backgroundColor = MaterialTheme.colorScheme.background,
            ) {
                BodyLarge(text = "기상송 신청하기")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WakeupSongCard(viewModel: WakeupSongViewModel = hiltViewModel(), wakeupSong: WakeupSong, index: Int? = null, selectedTabIndex: Int) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    val uriHandler = LocalUriHandler.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (selectedTabIndex == 1) {
                    Modifier.combinedClickable(
                        onClick = {
                            uriHandler.openUri(wakeupSong.videoUrl)
                        },
                        onLongClick = {
                            showDialog = true
                        },
                    )
                } else {
                    Modifier.clickable {
                        uriHandler.openUri(wakeupSong.videoUrl)
                    }
                },
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (showDialog) {
            DodamDialog(
                onDismissRequest = {
                    showDialog = false
                },
                confirmText = {
                    Row {
                        DodamLargeFilledButton(
                            onClick = {
                                showDialog = false
                            },
                            modifier = Modifier.weight(1.0f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            ),
                        ) {
                            Text(text = "취소")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        DodamLargeFilledButton(
                            onClick = {
                                viewModel.deleteWakeupSong(wakeupSong.id)
                                showDialog = false
                            },
                            modifier = Modifier.weight(1.0f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = MaterialTheme.colorScheme.onError,
                            ),
                        ) {
                            Text(text = "삭제")
                        }
                    }
                },
                text = {
                    Text(
                        text = wakeupSong.videoTitle,
                        maxLines = 3,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                        ),
                    )
                },
                title = { TitleLarge(text = "기상송을 삭제하시겠어요?") },
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                index?.let {
                    Text(
                        text = index.toString(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.CenterVertically),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }

                AsyncImage(
                    modifier = Modifier
                        .height(70.dp)
                        .width(120.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    model = wakeupSong.thumbnailUrl,
                    contentDescription = "profile_image",
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    BodyMedium(
                        text = wakeupSong.videoTitle,
                        modifier = Modifier.basicMarquee(),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = wakeupSong.channelTitle,
                        modifier = Modifier.basicMarquee(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}