package com.b1nd.dodam.wakeup_song

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.b1nd.dodam.dds.component.DodamSmallTopAppBar
import com.b1nd.dodam.dds.component.button.DodamLargeFilledButton
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.BodyMedium
import com.b1nd.dodam.dds.style.LabelLarge
import com.b1nd.dodam.dds.style.LabelSmall
import com.b1nd.dodam.dds.style.TitleMedium
import com.b1nd.dodam.ui.util.NoInteractionSource
import com.b1nd.dodam.wakeup_song.viewmodel.WakeupSongViewModel
import com.b1nd.dodam.wakeupsong.model.WakeupSong

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WakeupSongScreen(
    onClickAddWakeupSong: () -> Unit,
    popBackStack: () -> Unit,
    viewModel: WakeupSongViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            DodamSmallTopAppBar(
                title = {
                    BodyLarge(text = "기상송")
                },
                onNavigationIconClick = popBackStack,
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
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
                            .fillMaxWidth()
                    )
                }
                if (!uiState.allowedWakeupSongs.isNullOrEmpty()) {
                    items(uiState.allowedWakeupSongs?.size ?: 0) { index ->
                        val allowedSong =
                            (uiState.allowedWakeupSongs ?: emptyList())[index]
                        WakeupSongCard(
                            wakeupSong = allowedSong,
                            index = index + 1,
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
                        )
                    }
                }
                stickyHeader {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        modifier = Modifier.fillMaxWidth(),
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
                            interactionSource = remember { NoInteractionSource() }
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
                            interactionSource = remember { NoInteractionSource() }
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "MY")
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                    val currentWakeupSong =
                        if (selectedTabIndex == 0) uiState.pendingWakeupSongs
                        else uiState.myWakeupSongs
                    if (!currentWakeupSong.isNullOrEmpty()) {
                        this@LazyColumn.items(
                            currentWakeupSong.size
                        ) { index ->
                            val wakeupSong =
                                currentWakeupSong[index]
                            WakeupSongCard(wakeupSong = wakeupSong)
                        }
                    } else {
                        Spacer(modifier = Modifier.height(40.dp))
                        LabelLarge(text = if (selectedTabIndex == 0) "대기중인 기상송이 없어요" else "신청한 기상송이 없어요")
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.weight(1.0f))
            DodamLargeFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                onClick = { onClickAddWakeupSong() }
            ) {
                BodyLarge(text = "기상송 신청하기")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WakeupSongCard(
    wakeupSong: WakeupSong,
    index: Int? = null,
) {
    val uriHandler = LocalUriHandler.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                uriHandler.openUri(wakeupSong.videoUrl)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
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
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = wakeupSong.channelTitle,
                        modifier = Modifier.basicMarquee(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    )
                    LabelSmall(
                        text = wakeupSong.createdAt.toString(),
                        modifier = Modifier.basicMarquee(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}
