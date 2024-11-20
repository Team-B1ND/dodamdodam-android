package com.b1nd.dodam.askwakeupsong

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.DodamButtonDialog
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.ui.effect.shimmerEffect
import com.b1nd.dodam.ui.util.addFocusCleaner
import com.b1nd.dodam.wakeupsong.model.MelonChartSong
import com.b1nd.dodam.wakeupsong.model.SearchWakeupSong
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AskWakeupSongScreen(viewModel: AskWakeupSongViewModel = koinViewModel(), popBackStack: () -> Unit, showToast: (String, String) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val density = LocalDensity.current
    var keyWord by remember {
        mutableStateOf("")
    }
    var isFocus by remember {
        mutableStateOf(false)
    }
    val rankTextWidth = with(density) { DodamTheme.typography.labelBold().fontSize.toDp() } * 3

    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is Event.ShowToast -> {
                    if (uiState.isError) {
                        showToast("ERROR", event.message)
                    } else {
                        showToast("SUCCESS", event.message)
                    }
                }

                is Event.PopBackStack -> {
                    popBackStack()
                }
            }
        }
    }
    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .addFocusCleaner(focusManager),
                title = "기상송\n검색해 주세요",
                onBackClick = popBackStack,
                type = TopAppBarType.Medium,
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
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
                    label = "제목, 아티스트 혹은 링크",
                    onClickRemoveRequest = {
                        keyWord = ""
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        viewModel.searchWakeupSong(keyWord = keyWord)
                        focusManager.clearFocus()
                    }),
                )
            }
            Spacer(modifier = Modifier.height(40.dp))

            LazyColumn {
                if (!uiState.isSearchLoading && uiState.searchWakeupSongs.isNotEmpty()) {
                    items(uiState.searchWakeupSongs.size) { index ->
                        WakeupSongCard(
                            melonChartSong = uiState.searchWakeupSongs[index],
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                    ) {
                        Text(
                            text = "이런 노래는 어떤가요?",
                            style = DodamTheme.typography.headlineBold(),
                            color = DodamTheme.colors.labelNormal,
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "요즘 인기있는 노래를 바로 신청해보세요",
                            style = DodamTheme.typography.labelMedium(),
                            color = DodamTheme.colors.labelNormal,
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                if (!uiState.isLoading) {
                    items(uiState.melonChartSongs.size) { index ->
                        WakeupSongCard(
                            rankWidth = rankTextWidth,
                            melonChartSong = uiState.melonChartSongs[index],
                            index = index + 1,
                        )
                    }
                } else {
                    item {
                        WakeupSongShimmer(
                            rankWidth = rankTextWidth,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WakeupSongShimmer(rankWidth: Dp) {
    Column {
        repeat(5) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .height(20.dp)
                                .width(rankWidth / 2)
                                .clip(RoundedCornerShape(8.dp))
                                .background(shimmerEffect()),
                        )

                        Spacer(modifier = Modifier.width(rankWidth / 2))

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
fun WakeupSongCard(viewModel: AskWakeupSongViewModel = koinViewModel(), rankWidth: Dp, melonChartSong: MelonChartSong, index: Int? = null) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable(
                indication = rememberBounceIndication(),
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    showDialog = true
                },
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false },
            ) {
                DodamButtonDialog(
                    title = "기상송을 신청하시겠어요?",
                    body = "${melonChartSong.artist} - '${melonChartSong.name}'",
                    dismissButton = {
                        showDialog = false
                    },
                    confirmButton = {
                        viewModel.postWakeupSong(
                            melonChartSong.artist,
                            melonChartSong.name,
                        )
                        showDialog = false
                    },
                )
            }
        }
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Spacer(modifier = Modifier.width(8.dp))
                index?.let {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .width(rankWidth),
                        text = index.toString(),
                        style = DodamTheme.typography.labelBold(),
                        color = DodamTheme.colors.primaryNormal,
                        textAlign = TextAlign.Start,
                    )
                }

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
                    Text(
                        modifier = Modifier.basicMarquee(),
                        text = melonChartSong.name,
                        color = DodamTheme.colors.labelNormal,
                        style = DodamTheme.typography.body1Bold(),
                    )

                    Text(
                        modifier = Modifier.basicMarquee(),
                        text = melonChartSong.artist,
                        color = DodamTheme.colors.labelNormal,
                        style = DodamTheme.typography.caption1Medium(),
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
fun WakeupSongCard(viewModel: AskWakeupSongViewModel = koinViewModel(), melonChartSong: SearchWakeupSong) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = rememberBounceIndication(),
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    showDialog = true
                },
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false },
            ) {
                DodamButtonDialog(
                    title = "기상송을 신청하시겠어요?",
                    body = "${melonChartSong.channelTitle} - '${melonChartSong.videoTitle}'",
                    dismissButton = {
                        showDialog = false
                    },
                    confirmButton = {
                        viewModel.postWakeupSong(
                            melonChartSong.channelTitle,
                            melonChartSong.videoTitle,
                        )
                        showDialog = false
                    },
                )
            }
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
                    Text(
                        modifier = Modifier.basicMarquee(),
                        text = melonChartSong.videoTitle,
                        color = DodamTheme.colors.labelNormal,
                        style = DodamTheme.typography.body1Bold(),
                    )
                    Text(
                        text = melonChartSong.channelTitle,
                        modifier = Modifier.basicMarquee(),
                        color = DodamTheme.colors.labelAlternative,
                        style = DodamTheme.typography.caption1Medium(),
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
