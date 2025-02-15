package com.b1nd.dodam.parnet.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamContentTopAppBar
import com.b1nd.dodam.designsystem.component.DodamDialog
import com.b1nd.dodam.student.home.card.BannerCard
import com.b1nd.dodam.student.home.card.MealCard
import com.b1nd.dodam.student.home.card.ScheduleCard
import com.b1nd.dodam.student.home.model.BannerUiState
import com.b1nd.dodam.student.home.model.MealUiState
import com.b1nd.dodam.student.home.model.ScheduleUiState
import com.b1nd.dodam.ui.icons.DodamLogo
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun ParentHomeScreen(viewModel: ParentHomeViewModel = koinViewModel(), navigateToMeal: () -> Unit) {
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
        scheduleUiState = uiState.scheduleUiState,
        showShimmer = uiState.showShimmer,
        fetchMeal = viewModel::fetchMeal,
        fetchSchedule = viewModel::fetchSchedule,
        onRefresh = {
            viewModel.run {
                fetchMeal()
                fetchSchedule()
                fetchBanner()
            }
        },
        navigateToMeal = navigateToMeal,
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
private fun HomeScreen(
    bannerUiState: BannerUiState,
    mealUiState: MealUiState,
    scheduleUiState: ScheduleUiState,
    showShimmer: Boolean,
    fetchMeal: () -> Unit,
    fetchSchedule: () -> Unit,
    onRefresh: () -> Unit,
    navigateToMeal: () -> Unit,
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
