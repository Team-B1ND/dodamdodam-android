package com.b1nd.dodam.askwakeupsong

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.b1nd.dodam.dds.animation.bounceClick
import com.b1nd.dodam.dds.component.DodamDialog
import com.b1nd.dodam.dds.component.DodamLargeTopAppBar
import com.b1nd.dodam.dds.component.DodamTextField
import com.b1nd.dodam.dds.component.DodamToast
import com.b1nd.dodam.dds.component.button.DodamLargeFilledButton
import com.b1nd.dodam.dds.foundation.DodamColor
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.BodyMedium
import com.b1nd.dodam.dds.style.BodySmall
import com.b1nd.dodam.dds.style.CheckmarkCircleFilledIcon
import com.b1nd.dodam.dds.style.HeadlineSmall
import com.b1nd.dodam.dds.style.TitleLarge
import com.b1nd.dodam.dds.style.TitleMedium
import com.b1nd.dodam.dds.style.XMarkCircleIcon
import com.b1nd.dodam.ui.effect.shimmerEffect
import com.b1nd.dodam.wakeupsong.model.MelonChartSong
import com.b1nd.dodam.wakeupsong.model.SearchWakeupSong

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AskWakeupSongScreen(
    viewModel: AskWakeupSongViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    var keyWord by remember {
        mutableStateOf("")
    }
    var isFocus by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is Event.ShowToast -> {
                    snackbarHostState.showSnackbar(event.message)
                }

                is Event.PopBackStack -> {
                    popBackStack()
                }
            }
        }
    }
    Scaffold(
        topBar = {
            DodamLargeTopAppBar(
                title = {
                    HeadlineSmall(text = "기상송을\n검색해주세요")
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
                    DodamToast(
                        text = it.visuals.message,
                        trailingIcon = {
                            if (!uiState.isError) {
                                CheckmarkCircleFilledIcon(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .drawBehind {
                                            drawRoundRect(
                                                color = DodamColor.White,
                                                topLeft = Offset(15f, 15f),
                                                size = Size(25f, 25f),
                                            )
                                        },
                                )
                            } else {
                                XMarkCircleIcon(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .drawBehind {
                                            drawRoundRect(
                                                color = DodamColor.White,
                                                topLeft = Offset(15f, 15f),
                                                size = Size(25f, 25f),
                                            )
                                        },
                                    tint = MaterialTheme.colorScheme.error,
                                )
                            }
                        },
                        iconColor = if (uiState.isError) MaterialTheme.colorScheme.error else DodamColor.Green,
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues),
        ) {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                DodamTextField(
                    modifier = Modifier.onFocusChanged {
                        isFocus = it.isFocused
                    },
                    value = keyWord,
                    onValueChange = { keyWord = it },
                    label = {
                        Text(text = "제목, 아티스트 혹은 링크")
                    },
                    trailingIcon = {
                        if (keyWord.isNotEmpty() && isFocus) {
                            XMarkCircleIcon(
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.clickable {
                                    keyWord = ""
                                },
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        viewModel.searchWakeupSong(keyWord = keyWord)
                        focusManager.clearFocus()
                    }),
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Row {
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    TitleMedium(text = "이런 노래는 어떤가요?")
                    Spacer(modifier = Modifier.height(2.dp))
                    BodySmall(
                        text = "요즘 인기있는 노래를 바로 신청해보세요",
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (!uiState.isLoading) {
                LazyColumn {
                    if (!uiState.isSearchLoading) {
                        items(uiState.melonChartSongs.size) { index ->
                            WakeupSongCard(
                                melonChartSong = uiState.melonChartSongs[index],
                                index = index + 1,
                            )
                        }
                    }
                }
            } else {
                WakeupSongShimmer()
            }
        }
    }
}

@Composable
fun WakeupSongShimmer() {
    Column {
        repeat(5) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Spacer(modifier = Modifier.width(16.dp))

                        Box(
                            modifier = Modifier
                                .height(67.dp)
                                .width(120.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(shimmerEffect()),
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Box(
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(80.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(shimmerEffect()),
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Box(
                                modifier = Modifier
                                    .height(16.dp)
                                    .width(25.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(shimmerEffect()),
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WakeupSongCard(
    viewModel: AskWakeupSongViewModel = hiltViewModel(),
    melonChartSong: MelonChartSong,
    index: Int? = null
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .bounceClick(onClick = {
                showDialog = true
            }),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (showDialog) {
            DodamDialog(
                onDismissRequest = {
                    showDialog = false
                },
                dismissButton = {
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
                },
                confirmButton = {
                    DodamLargeFilledButton(
                        onClick = {
                            viewModel.postWakeupSong(
                                melonChartSong.artist,
                                melonChartSong.name,
                            )
                            showDialog = false
                        },
                        modifier = Modifier.weight(1.0f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                    ) {
                        Text(text = "신청")
                    }
                },
                text = {
                    Text(
                        text = "${melonChartSong.artist} - '${melonChartSong.name}'",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                        ),
                    )
                },
                title = { TitleLarge(text = "기상송을 신청하시겠어요?") },
            )
        }
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Spacer(modifier = Modifier.width(8.dp))
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
                        .height(67.dp)
                        .width(if (index == null) 120.dp else 67.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    model = melonChartSong.thumbnail,
                    contentDescription = "profile_image",
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    BodyMedium(
                        text = melonChartSong.name,
                        modifier = Modifier.basicMarquee(),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = melonChartSong.artist,
                        modifier = Modifier.basicMarquee(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WakeupSongCard(
    viewModel: AskWakeupSongViewModel = hiltViewModel(),
    melonChartSong: SearchWakeupSong
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick(onClick = {
                showDialog = true
            }),
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
                            BodyLarge(text = "취소")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        DodamLargeFilledButton(
                            onClick = {
                                viewModel.postWakeupSong(
                                    melonChartSong.channelTitle,
                                    melonChartSong.videoTitle,
                                )
                                showDialog = false
                            },
                            modifier = Modifier.weight(1.0f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                        ) {
                            BodyLarge(text = "신청")
                        }
                    }
                },
                text = {
                    Text(
                        text = "${melonChartSong.channelTitle} - '${melonChartSong.videoTitle}'",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                        ),
                    )
                },
                title = { TitleLarge(text = "기상송을 신청하시겠어요?") },
            )
        }
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Spacer(modifier = Modifier.width(8.dp))
                AsyncImage(
                    modifier = Modifier
                        .height(67.dp)
                        .width(120.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    model = melonChartSong.thumbnail,
                    contentDescription = "profile_image",
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    BodyMedium(
                        text = melonChartSong.videoTitle,
                        modifier = Modifier.basicMarquee(),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = melonChartSong.channelTitle,
                        modifier = Modifier.basicMarquee(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
