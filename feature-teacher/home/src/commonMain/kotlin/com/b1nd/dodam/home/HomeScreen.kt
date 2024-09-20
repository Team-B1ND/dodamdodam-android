package com.b1nd.dodam.home

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ActionIcon
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamContentTopAppBar
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.home.card.BannerCard
import com.b1nd.dodam.home.card.MealCard
import com.b1nd.dodam.home.card.NightStudyCard
import com.b1nd.dodam.home.card.OutCard
import com.b1nd.dodam.home.card.ScheduleCard
import com.b1nd.dodam.ui.icons.DodamLogo
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.plus
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterialApi::class)
@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navigateToMeal: () -> Unit,
    navigateToOuting: () -> Unit,
    navigateToNightStudy: () -> Unit,
    navigateToSleep: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.run {
                loadMeal()
                loadOuting()
                loadNightStudy()
                loadSchedule()
                loadBanner()
            }
        },
    )

    LaunchedEffect(state.mealUiState) {
        isRefreshing = false
    }

    Scaffold(
        modifier = Modifier.background(DodamTheme.colors.backgroundNeutral),
        topBar = {
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
                    ActionIcon(
                        icon = DodamIcons.Bell,
                        onClick = {},
                        enabled = false
                    ),
                ),
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DodamTheme.colors.backgroundNeutral)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item {
                    BannerCard(
                        state = state.bannerUiState,
                    )
                }
                item {
                    MealCard(
                        state = state.mealUiState,
                        showShimmer = state.showShimmer,
                        onClickContent = navigateToMeal,
                        onClickRefresh = viewModel::loadMeal,
                    )
                }

                item {
                    OutCard(
                        showShimmer = state.showShimmer,
                        uiState = state.outUiState,
                        onRefreshClick = viewModel::loadOuting,
                        onOutingClick = navigateToOuting,
                        onSleepoverClick = navigateToSleep,
                    )
                }

                item {
                    NightStudyCard(
                        showShimmer = state.showShimmer,
                        uiState = state.nightStudyUiState,
                        onContentClick = navigateToNightStudy,
                        onRefreshClick = viewModel::loadNightStudy,
                    )
                }

                item {
                    ScheduleCard(
                        uiState = state.scheduleUiState,
                        showShimmer = state.showShimmer,
                        fetchSchedule = viewModel::loadSchedule,
                        onContentClick = {},
                    )
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

@Composable
internal fun DefaultText(onClick: () -> Unit, label: String, body: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBounceIndication(),
            ),
        contentAlignment = Alignment.Center,

    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.CenterStart),
        ) {
            Text(
                text = label,
                style = DodamTheme.typography.caption1Medium(),
                color = DodamTheme.colors.labelAlternative,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = body,
                style = DodamTheme.typography.body1Bold().copy(fontWeight = FontWeight.Bold),
                color = DodamTheme.colors.primaryNormal,
            )
        }
    }
}

@Composable
internal fun InnerCountCard(modifier: Modifier = Modifier, title: String, content: String, buttonText: String, showButton: Boolean, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = title,
                style = DodamTheme.typography.labelMedium(),
                color = DodamTheme.colors.labelAssistive,
            )
            Text(
                text = content,
                style = DodamTheme.typography.heading2Medium(),
                color = DodamTheme.colors.labelNormal,
            )
        }
        if (showButton) {
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            DodamButton(
                text = buttonText,
                onClick = onClick,
                buttonRole = ButtonRole.Assistive,
                buttonSize = ButtonSize.Small,
            )
        }
    }
}
