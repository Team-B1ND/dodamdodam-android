package com.b1nd.dodam.student.point

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.b1nd.dodam.data.point.model.ScoreType
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamDialog
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.ui.effect.shimmerEffect
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterial3Api
@Composable
internal fun PointScreen(viewModel: PointViewModel = koinViewModel(), popBackStack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var titleIndex by remember { mutableIntStateOf(0) }
    val text = listOf(
        "기숙사",
        "학교",
    )
    val item = List(2) { index: Int ->
        DodamSegment(
            selected = titleIndex == index,
            text = text[index],
            onClick = { titleIndex = index },
        )
    }.toImmutableList()

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect {
            when (it) {
                is Event.ShowDialog -> {
                    showDialog = true
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.load()
    }

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            viewModel.load()
            isRefreshing = false
        },
    )

    if (showDialog) {
        DodamDialog(
            title = "상벌점을 불러오지 못했어요",
            text = "확인",
            confirmButton = {
                showDialog = false
            },
        )
    }
    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "내 상벌점",
                onBackClick = popBackStack,
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter,
        ) {
            PullRefreshIndicator(
                modifier = Modifier
                    .zIndex(1f),
                refreshing = isRefreshing,
                state = pullRefreshState,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 24.dp, start = 24.dp, top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                DodamSegmentedButton(
                    segments = item,
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "상점",
                            color = DodamTheme.colors.labelAssistive,
                            style = DodamTheme.typography.body1Bold(),
                        )
                        Text(
                            text = if (uiState.isLoading) {
                                "0점"
                            } else {
                                if (titleIndex == 0) {
                                    "${uiState.dormitoryPoint.second}점"
                                } else {
                                    "${uiState.schoolPoint.second}점"
                                }
                            },
                            color = DodamTheme.colors.primaryNormal,
                            style = DodamTheme.typography.title1Bold(),
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "벌점",
                            color = DodamTheme.colors.labelAssistive,
                            style = DodamTheme.typography.body1Bold(),
                        )
                        Text(
                            text = if (uiState.isLoading) {
                                "0점"
                            } else {
                                if (titleIndex == 0) {
                                    "${uiState.dormitoryPoint.first}점"
                                } else {
                                    "${uiState.schoolPoint.first}점"
                                }
                            },
                            color = DodamTheme.colors.statusNegative,
                            style = DodamTheme.typography.title1Bold(),
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(DodamTheme.colors.lineAlternative),
                )

                Text(
                    text = "상벌점 발급 내역",
                    color = DodamTheme.colors.labelNormal,
                    style = DodamTheme.typography.headlineBold(),
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    if (uiState.isLoading) {
                        items(5) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(2.dp),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(20.dp)
                                            .background(shimmerEffect(), RoundedCornerShape(4.dp)),
                                    )
                                    Box(
                                        modifier = Modifier
                                            .width(80.dp)
                                            .height(14.dp)
                                            .background(shimmerEffect(), RoundedCornerShape(4.dp)),
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Box(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(20.dp)
                                        .background(shimmerEffect(), RoundedCornerShape(4.dp)),
                                )
                            }
                        }
                    } else {
                        items(
                            items = if (titleIndex == 0) {
                                uiState.dormitoryPointReasons
                            } else {
                                uiState.schoolPointReasons
                            },
                            key = { it.id },
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(2.dp),
                                ) {
                                    Text(
                                        text = it.reason.reason,
                                        color = DodamTheme.colors.labelNormal,
                                        style = DodamTheme.typography.headlineBold(),
                                    )
                                    Text(
                                        text = "${it.teacher.name} · ${it.issueAt}",
                                        color = DodamTheme.colors.labelAssistive,
                                        style = DodamTheme.typography.labelMedium(),
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Text(
                                    text = "${it.reason.score}점",
                                    color = if (it.reason.scoreType == ScoreType.MINUS) {
                                        DodamTheme.colors.statusNegative
                                    } else {
                                        DodamTheme.colors.primaryNormal
                                    },
                                    style = DodamTheme.typography.headlineBold(),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
