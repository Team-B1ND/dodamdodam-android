package com.b1nd.dodam.student.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.DodamContentTopAppBar
import com.b1nd.dodam.designsystem.component.DodamDialog
import com.b1nd.dodam.designsystem.foundation.DodamIcons
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
import kotlinx.collections.immutable.persistentListOf
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
    role: String
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showDialog by remember {
        mutableStateOf(false)
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                showDialog = false
            },
        ) {
            DodamDialog(
                confirmButton = {
                    showDialog = false
                },
                title = "아직 준비 중인 기능이에요!",
                text = "전체 일정을 조회하시려면 도담도담 웹사이트를 이용해 주세요.",
            )
        }
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
        role = role
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
    role: String
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
                DodamContentTopAppBar(
                    modifier = Modifier
                        .background(DodamTheme.colors.backgroundNeutral)
                        .statusBarsPadding(),
                    content = {
                        Column {
                            Row {
                                Spacer(Modifier.width(16.dp))
                                Icon(
                                    modifier = Modifier.width(90.dp),
                                    imageVector = DodamLogo,
                                    contentDescription = null,
                                    tint = DodamTheme.colors.primaryNormal,
                                )
                            }
                        }
                    },
                    actionIcons = persistentListOf(
//                        TODO 알림 아이콘 활성화
//                        ActionIcon(
//                            icon = DodamIcons.Bell,
//                            onClick = {},
//                            enabled = false,
//                        ),
                    ),
                )
                AnimatedVisibility(scrollState.canScrollBackward) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(DodamTheme.colors.fillNeutral),
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
                    .background(DodamTheme.colors.backgroundNeutral)
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
                        navigateToMeal = navigateToMeal,
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
                        DodamTheme.colors.primaryNormal
                    } else {
                        DodamTheme.colors.labelDisabled
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
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBounceIndication(),
            )
            .padding(6.dp),
    ) {
        Text(
            text = label,
            color = DodamTheme.colors.labelAlternative,
            style = DodamTheme.typography.caption1Medium(),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = body,
            color = DodamTheme.colors.primaryNormal,
            style = DodamTheme.typography.body1Bold(),
        )
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
                color = DodamTheme.colors.backgroundNormal,
                shape = DodamTheme.shapes.large,
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (showNextButton) {
                        Modifier.clickable(
                            indication = rememberBounceIndication(),
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
                        color = DodamTheme.colors.primaryAlternative,
                        shape = RoundedCornerShape(100),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(16.dp),
                    imageVector = icon,
                    contentDescription = "icon",
                    tint = DodamTheme.colors.staticWhite,
                )
            }

            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                color = DodamTheme.colors.labelStrong,
                style = DodamTheme.typography.headlineBold(),
            )

            Spacer(modifier = Modifier.weight(1f))

            if (showNextButton) {
                Icon(
                    modifier = Modifier
                        .size(16.dp),
                    imageVector = DodamIcons.ChevronRight.value,
                    contentDescription = "next",
                    tint = DodamTheme.colors.labelNormal.copy(alpha = 0.5f),
                )
            }
        }

        content()

        Spacer(modifier = Modifier.height(10.dp))
    }
}
