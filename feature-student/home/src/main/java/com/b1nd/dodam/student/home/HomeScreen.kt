package com.b1nd.dodam.student.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.b1nd.dodam.dds.animation.bounceClick
import com.b1nd.dodam.dds.component.DodamDialog
import com.b1nd.dodam.dds.component.DodamTopAppBar
import com.b1nd.dodam.dds.component.button.DodamTextButton
import com.b1nd.dodam.dds.foundation.DodamIcons
import com.b1nd.dodam.dds.foundation.DodamShape
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.LabelLarge
import com.b1nd.dodam.dds.style.TitleLarge
import com.b1nd.dodam.student.home.card.BannerCard
import com.b1nd.dodam.student.home.card.MealCard
import com.b1nd.dodam.student.home.card.NightStudyCard
import com.b1nd.dodam.student.home.card.OutCard
import com.b1nd.dodam.student.home.card.ScheduleCard
import com.b1nd.dodam.student.home.card.WakeupSongCard
import com.b1nd.dodam.student.home.model.BannerUiState
import com.b1nd.dodam.student.home.model.MealUiState
import com.b1nd.dodam.student.home.model.NightStudyUiState
import com.b1nd.dodam.student.home.model.OutUiState
import com.b1nd.dodam.student.home.model.ScheduleUiState
import com.b1nd.dodam.student.home.model.WakeupSongUiState
import com.b1nd.dodam.ui.icons.DodamLogo
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navigateToAskOut: () -> Unit,
    navigateToMeal: () -> Unit,
    navigateToAskNightStudy: () -> Unit,
    navigateToAskWakeupSong: () -> Unit,
    navigateToNightStudy: () -> Unit,
    navigateToOut: () -> Unit,
    navigateToWakeupSongScreen: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showDialog by remember {
        mutableStateOf(false)
    }

    if (showDialog) {
        DodamDialog(
            onDismissRequest = {
                showDialog = false
            },
            confirmText = {
                DodamTextButton(onClick = {
                    showDialog = false
                }) {
                    BodyLarge(text = "확인")
                }
            },
            title = {
                Text(text = "아직 준비 중인 기능이에요!")
            },
            text = {
                Text(text = "전체 일정을 조회하시려면 도담도담 웹사이트를 이용해 주세요.")
            },
        )
    }

    HomeScreen(
        bannerUiState = uiState.bannerUiState,
        mealUiState = uiState.mealUiState,
        wakeupSongUiState = uiState.wakeupSongUiState,
        outUiState = uiState.outUiState,
        nightStudyUiState = uiState.nightStudyUiState,
        scheduleUiState = uiState.scheduleUiState,
        navigateToWakeupSongScreen = navigateToWakeupSongScreen,
        showShimmer = uiState.showShimmer,
        fetchMeal = viewModel::fetchMeal,
        fetchWakeupSong = viewModel::fetchWakeupSong,
        fetchNightStudy = viewModel::fetchNightStudy,
        fetchSchedule = viewModel::fetchSchedule,
        onRefresh = {
            viewModel.run {
                fetchMeal()
                fetchWakeupSong()
                fetchOut()
                fetchNightStudy()
                fetchSchedule()
                fetchBanner()
            }
        },
        navigateToAskOut = navigateToAskOut,
        navigateToAskNightStudy = navigateToAskNightStudy,
        navigateToAskWakeupSong = navigateToAskWakeupSong,
        navigateToMeal = navigateToMeal,
        navigateToNightStudy = navigateToNightStudy,
        navigateToOut = navigateToOut,
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
private fun HomeScreen(
    bannerUiState: BannerUiState,
    mealUiState: MealUiState,
    wakeupSongUiState: WakeupSongUiState,
    outUiState: OutUiState,
    nightStudyUiState: NightStudyUiState,
    scheduleUiState: ScheduleUiState,
    navigateToWakeupSongScreen: () -> Unit,
    showShimmer: Boolean,
    fetchMeal: () -> Unit,
    fetchWakeupSong: () -> Unit,
    fetchNightStudy: () -> Unit,
    fetchSchedule: () -> Unit,
    onRefresh: () -> Unit,
    navigateToAskOut: () -> Unit,
    navigateToMeal: () -> Unit,
    navigateToAskNightStudy: () -> Unit,
    navigateToAskWakeupSong: () -> Unit,
    navigateToNightStudy: () -> Unit,
    navigateToOut: () -> Unit,
) {
    val scrollState = rememberLazyListState()

    val context = LocalContext.current

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            onRefresh()
        },
    )

    LaunchedEffect(mealUiState) {
        isRefreshing = false
    }

    Scaffold(
        topBar = {
            Column {
                DodamTopAppBar(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 4.dp),
                    title = {
                        Icon(
                            modifier = Modifier.width(90.dp),
                            imageVector = DodamLogo,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    },
//                    TODO Alarm feature
//                    actions = {
//                        DodamIconButton(onClick = { /*TODO*/ }) {
//                            BellIcon(modifier = Modifier.size(28.dp))
//                        }
//                    },
                )
                AnimatedVisibility(scrollState.canScrollBackward) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.outlineVariant),
                    )
                }
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                state = scrollState,
            ) {
                item {
                    BannerCard(
                        uiState = bannerUiState,
                        context = context,
                    )
                }

                item {
                    MealCard(
                        uiState = mealUiState,
                        showShimmer = showShimmer,
                        onContentClick = navigateToMeal,
                        fetchMeal = fetchMeal,
                    )
                }

                item {
                    WakeupSongCard(
                        uiState = wakeupSongUiState,
                        onNextClick = navigateToWakeupSongScreen,
                        navigateToWakeupSongApply = navigateToAskWakeupSong,
                        showShimmer = showShimmer,
                        fetchWakeupSong = fetchWakeupSong,
                        context = context,
                    )
                }

                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutCard(
                            modifier = Modifier.weight(1f),
                            uiState = outUiState,
                            showShimmer = showShimmer,
                            navigateToOut = navigateToOut,
                            navigateToOutApply = navigateToAskOut,
                        ) {
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        NightStudyCard(
                            modifier = Modifier.weight(1f),
                            uiState = nightStudyUiState,
                            showShimmer = showShimmer,
                            navigateToAskNightStudy = navigateToAskNightStudy,
                            navigateToNightStudy = navigateToNightStudy,
                            fetchNightStudy = fetchNightStudy,
                        )
                    }
                }

                item {
                    ScheduleCard(
                        uiState = scheduleUiState,
                        showShimmer = showShimmer,
                        fetchSchedule = fetchSchedule,
                        onContentClick = { /*TODO : Navigate to Schedule screen*/ },
                    ) {
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(150.dp))
                }
            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
internal fun PagerIndicator(modifier: Modifier = Modifier, pagerState: PagerState) {
    if (pagerState.pageCount > 1) {
        Row(modifier = modifier) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    }

                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(5.dp),
                )
            }
        }
    }
}

@Composable
internal fun DefaultText(onClick: () -> Unit, label: String, body: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .bounceClick(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
            )
            .padding(6.dp),
    ) {
        LabelLarge(text = label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(4.dp))
        BodyLarge(text = body, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
internal fun DodamContainer(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    showNextButton: Boolean = false,
    onNextClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = DodamShape.Large,
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (showNextButton) {
                        Modifier.bounceClick(
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = onNextClick!!,
                        )
                    } else {
                        Modifier
                    },
                )
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(100),
                    )
                    .padding(7.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = icon,
                    contentDescription = "icon",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            TitleLarge(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 20.sp,
            )

            Spacer(modifier = Modifier.weight(1f))

            if (showNextButton) {
                Icon(
                    modifier = Modifier
                        .size(16.dp),
                    imageVector = DodamIcons.ChevronRight,
                    contentDescription = "next",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        content()

        Spacer(modifier = Modifier.height(10.dp))
    }
}
