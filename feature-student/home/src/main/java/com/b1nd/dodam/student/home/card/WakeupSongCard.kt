package com.b1nd.dodam.student.home.card

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.b1nd.dodam.dds.animation.LoadingDotsIndicator
import com.b1nd.dodam.dds.animation.bounceClick
import com.b1nd.dodam.dds.foundation.DodamIcons
import com.b1nd.dodam.dds.foundation.DodamShape
import com.b1nd.dodam.dds.style.BodyMedium
import com.b1nd.dodam.dds.style.LabelLarge
import com.b1nd.dodam.student.home.DefaultText
import com.b1nd.dodam.student.home.DodamContainer
import com.b1nd.dodam.student.home.PagerIndicator
import com.b1nd.dodam.student.home.model.WakeupSongUiState
import com.b1nd.dodam.ui.effect.shimmerEffect

@ExperimentalFoundationApi
@Composable
internal fun WakeupSongCard(
    uiState: WakeupSongUiState,
    onNextClick: () -> Unit,
    navigateToWakeupSongApply: () -> Unit,
    showShimmer: Boolean,
    fetchWakeupSong: () -> Unit,
    context: Context,
) {
    DodamContainer(
        icon = DodamIcons.Note,
        title = "오늘의 기상송",
        showNextButton = true,
        onNextClick = onNextClick,
        content = {
            if (!showShimmer) {
                when (uiState) {
                    is WakeupSongUiState.Success -> {
                        val wakeupSongs = remember { uiState.data }
                        val wakeupSongPagerState = rememberPagerState { wakeupSongs.size }

                        if (wakeupSongs.isEmpty()) {
                            DefaultText(
                                onClick = navigateToWakeupSongApply,
                                label = "승인된 기상송이 없어요",
                                body = "기상송 신청하기",
                            )
                        } else {
                            Column {
                                HorizontalPager(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp)
                                        .bounceClick(
                                            onClick = {
                                                context.startActivity(
                                                    Intent(
                                                        Intent.ACTION_VIEW,
                                                        Uri.parse(wakeupSongs[wakeupSongPagerState.currentPage].videoUrl),
                                                    ),
                                                )
                                            },
                                            interactionSource = remember { MutableInteractionSource() },
                                        )
                                        .padding(6.dp),
                                    state = wakeupSongPagerState,
                                ) { page ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {
                                        AsyncImage(
                                            modifier = Modifier
                                                .size(width = 120.dp, height = 67.dp)
                                                .clip(DodamShape.Small),
                                            model = wakeupSongs[page].thumbnail,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                        )

                                        Spacer(modifier = Modifier.width(12.dp))

                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                        ) {
                                            BodyMedium(
                                                modifier = Modifier.basicMarquee(),
                                                text = wakeupSongs[page].videoTitle,
                                                color = MaterialTheme.colorScheme.onSurface,
                                            )

                                            Spacer(modifier = Modifier.height(4.dp))

                                            LabelLarge(
                                                modifier = Modifier.basicMarquee(),
                                                text = wakeupSongs[page].channelTitle,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                fontWeight = FontWeight.Normal,
                                            )
                                        }
                                    }
                                }

                                PagerIndicator(
                                    modifier = Modifier
                                        .align(Alignment.End)
                                        .padding(end = 16.dp),
                                    pagerState = wakeupSongPagerState,
                                )
                            }
                        }
                    }

                    is WakeupSongUiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            LoadingDotsIndicator()
                        }
                    }

                    is WakeupSongUiState.Error -> {
                        DefaultText(
                            onClick = fetchWakeupSong,
                            label = "기상송을 불러올 수 없어요",
                            body = "다시 불러오기",
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                shimmerEffect(),
                                DodamShape.Medium,
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
            }
        },
    )
}
