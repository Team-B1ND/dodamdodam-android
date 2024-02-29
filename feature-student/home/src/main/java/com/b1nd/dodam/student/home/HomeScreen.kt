package com.b1nd.dodam.student.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.b1nd.dodam.designsystem.component.DodamContainer
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.shimmerEffect
import com.b1nd.dodam.designsystem.icons.Bell
import com.b1nd.dodam.designsystem.icons.DodamLogo
import com.b1nd.dodam.designsystem.icons.ForkAndKnife
import com.b1nd.dodam.designsystem.icons.Note

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()
    val mealPagerState = rememberPagerState { 3 }
    val wakeupSongPagerState = rememberPagerState { uiState.wakeupSongs.size }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Spacer(
                modifier = Modifier.height(
                    36.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                ),
            )

            DodamContainer(
                icon = ForkAndKnife,
                title = "오늘의 급식",
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                ) {
                    if (uiState.isLoading) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(20.dp)
                                    .background(
                                        shimmerEffect(),
                                        RoundedCornerShape(4.dp),
                                    ),
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(Modifier.fillMaxWidth()) {
                                Box(
                                    modifier = Modifier
                                        .weight(0.7f)
                                        .height(20.dp)
                                        .background(
                                            shimmerEffect(),
                                            RoundedCornerShape(4.dp),
                                        ),
                                )
                                Spacer(modifier = Modifier.weight(0.3f))
                            }
                        }
                    } else {
                        HorizontalPager(state = mealPagerState) { page ->
                            Text(
                                text = when (page) {
                                    0 -> uiState.meal.first
                                    1 -> uiState.meal.second
                                    else -> uiState.meal.third
                                },
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        Row(
                            Modifier.align(Alignment.End),
                        ) {
                            repeat(mealPagerState.pageCount) { iteration ->
                                val color = if (mealPagerState.currentPage == iteration) {
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
            }

            DodamContainer(
                icon = Note,
                title = "오늘의 기상송",
                showNextButton = true,
                onNextClick = {},
            ) {
                Column(
                    modifier = Modifier
                        .animateContentSize(),
                ) {
                    if (uiState.isLoading) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(
                                        shimmerEffect(),
                                        RoundedCornerShape(12.dp),
                                    ),
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Row(Modifier.fillMaxWidth()) {
                                    Box(
                                        modifier = Modifier
                                            .weight(0.9f)
                                            .height(20.dp)
                                            .background(
                                                shimmerEffect(),
                                                RoundedCornerShape(4.dp),
                                            ),
                                    )
                                    Spacer(modifier = Modifier.weight(0.1f))
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Box(
                                    modifier = Modifier
                                        .width(50.dp)
                                        .height(20.dp)
                                        .background(
                                            shimmerEffect(),
                                            RoundedCornerShape(4.dp),
                                        ),
                                )
                            }
                        }
                    } else {
                        HorizontalPager(state = wakeupSongPagerState) { page ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    model = uiState.wakeupSongs[page].thumbnailUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Text(
                                        text = uiState.wakeupSongs[page].videoTitle,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = uiState.wakeupSongs[page].channelTitle,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.tertiary,
                                    )
                                }
                            }
                        }
                        Row(
                            Modifier.align(Alignment.End),
                        ) {
                            repeat(wakeupSongPagerState.pageCount) { iteration ->
                                val color = if (wakeupSongPagerState.currentPage == iteration) {
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
            }
        }

        DodamTopAppBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                .statusBarsPadding(),
            containerColor = Color.Transparent,
            titleIcon = {
                Icon(
                    modifier = Modifier.height(22.dp),
                    imageVector = DodamLogo,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
            icon = Bell,
            onIconClick = {
                // TODO : Navigate to notification screen
            },
        )
    }
}
