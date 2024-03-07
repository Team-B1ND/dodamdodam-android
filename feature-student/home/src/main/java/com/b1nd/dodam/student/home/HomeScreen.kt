package com.b1nd.dodam.student.home

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.outing.model.OutType
import com.b1nd.dodam.designsystem.component.DodamCircularProgress
import com.b1nd.dodam.designsystem.component.DodamContainer
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.shimmerEffect
import com.b1nd.dodam.designsystem.icons.Bell
import com.b1nd.dodam.designsystem.icons.DodamLogo
import com.b1nd.dodam.designsystem.icons.Door
import com.b1nd.dodam.designsystem.icons.ForkAndKnife
import com.b1nd.dodam.designsystem.icons.MoonPlus
import com.b1nd.dodam.designsystem.icons.Note
import kotlinx.datetime.toJavaLocalDateTime
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()
    val mealPagerState = rememberPagerState { uiState.meal.filterNotNull().size }
    val wakeupSongPagerState = rememberPagerState { uiState.wakeupSongs.size }

    val context = LocalContext.current
    val current = LocalDateTime.now()

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
                title = "오늘의 " + when (uiState.meal.filterNotNull()[mealPagerState.currentPage]) {
                    uiState.meal[0] -> "조식"
                    uiState.meal[1] -> "중식"
                    uiState.meal[2] -> "석식"
                    else -> "급식"
                },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(),
                    ) {
                        if (uiState.isLoading) {
                            Column(
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {
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

                            if (uiState.meal.filterNotNull().isEmpty()) {
                                Column(modifier = it) {
                                    Text(
                                        text = "오늘은 급식이 없어요",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.tertiary,
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "내일 급식 보러가기",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                }
                            } else {
                                HorizontalPager(
                                    modifier = it,
                                    state = mealPagerState
                                ) { page ->
                                    Text(
                                        text = uiState.meal.filterNotNull()[page],
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                                if (uiState.meal.filterNotNull().size > 1) {
                                    Row(
                                        Modifier
                                            .align(Alignment.End)
                                            .padding(end = 16.dp),
                                    ) {
                                        repeat(mealPagerState.pageCount) { iteration ->
                                            val color =
                                                if (mealPagerState.currentPage == iteration) {
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
                },
                onClickContent = {}
            )

            DodamContainer(
                icon = Note,
                title = "오늘의 기상송",
                showNextButton = true,
                onNextClick = {
                    // TODO : Navigate to wakeup song screen
                },
                content = {
                    Column(
                        modifier = Modifier
                            .animateContentSize(),
                    ) {
                        if (uiState.isLoading) {
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
                            if (uiState.wakeupSongs.isEmpty()) {
                                Column(modifier = it) {
                                    Text(
                                        text = "승인된 기상송이 없어요",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.tertiary,
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "기상송 신청하기",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                }
                            } else {
                                HorizontalPager(
                                    modifier = it,
                                    state = wakeupSongPagerState
                                ) { page ->
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
                                    Modifier
                                        .align(Alignment.End)
                                        .padding(end = 16.dp),
                                ) {
                                    repeat(wakeupSongPagerState.pageCount) { iteration ->
                                        val color =
                                            if (wakeupSongPagerState.currentPage == iteration) {
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
                },
                onClickContent = {
                    if (uiState.wakeupSongs.isEmpty()) {
                        // TODO : Navigate to wakeup song request screen
                    } else {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(uiState.wakeupSongs[wakeupSongPagerState.currentPage].videoUrl)
                            )
                        )
                    }
                }
            )

            Row {
                DodamContainer(
                    modifier = Modifier.weight(1f),
                    icon = Door,
                    title = "외출 외박",
                    content = {
                        if (uiState.isLoading) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(70.dp)
                                        .background(
                                            shimmerEffect(),
                                            RoundedCornerShape(100),
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
                                            .height(15.dp)
                                            .background(
                                                shimmerEffect(),
                                                RoundedCornerShape(4.dp),
                                            ),
                                    )
                                }
                            }
                        } else {
                            if (uiState.out.isEmpty()) {
                                Column(modifier = it) {
                                    Text(
                                        text = "외출, 외박이 필요하다면",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.tertiary,
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "외출/외박 신청하기",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                }
                            } else {
                                val out = uiState.out.first()
                                val outProgress = 1 - ChronoUnit.MICROS.between(
                                    out.startAt.toJavaLocalDateTime(),
                                    current
                                ).toFloat() / ChronoUnit.MICROS.between(
                                    out.startAt.toJavaLocalDateTime(),
                                    out.endAt.toJavaLocalDateTime()
                                )

                                when (out.status) {
                                    Status.ALLOWED -> {
                                        Row(modifier = it) {
                                            DodamCircularProgress(
                                                progress = outProgress,
                                                color = MaterialTheme.colorScheme.primary,
                                                backgroundColor = MaterialTheme.colorScheme.secondary,
                                            )
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Column {
                                                Text(
                                                    text = if (out.outType == OutType.OUTING) {
                                                        val date = out.endAt.toJavaLocalDateTime()
                                                            .minusHours(current.hour.toLong())
                                                            .minusMinutes(current.minute.toLong())
                                                        buildAnnotatedString {
                                                            if (date.hour > 0) append("${date.hour}시간 ${date.minute}분 ")
                                                            else append("${date.minute}분 ")
                                                            withStyle(
                                                                style = MaterialTheme.typography.labelMedium.copy(
                                                                    MaterialTheme.colorScheme.tertiary
                                                                ).toSpanStyle()
                                                            ) {
                                                                append("남음")
                                                            }
                                                        }
                                                    } else {
                                                        val date = out.endAt.toJavaLocalDateTime()
                                                            .minusDays(current.dayOfMonth.toLong())
                                                            .minusHours(current.hour.toLong())
                                                            .minusMinutes(current.minute.toLong())
                                                        buildAnnotatedString {
                                                            if (date.dayOfMonth > 0) append("${date.dayOfMonth}일 ")
                                                            else if (date.hour > 0) "${date.hour}시간 ${date.minute}분 "
                                                            else "${date.minute}분 "
                                                            withStyle(
                                                                style = MaterialTheme.typography.labelMedium.copy(
                                                                    MaterialTheme.colorScheme.tertiary
                                                                ).toSpanStyle()
                                                            ) {
                                                                append("까지")
                                                            }
                                                        }
                                                    },
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = if (out.outType == OutType.OUTING) {
                                                        String.format(
                                                            "%02d:%02d 복귀",
                                                            out.endAt.hour,
                                                            out.endAt.minute
                                                        )
                                                    } else String.format(
                                                        "%02d:%02d 까지",
                                                        out.endAt.monthNumber,
                                                        out.endAt.dayOfMonth
                                                    ),
                                                    style = MaterialTheme.typography.labelMedium,
                                                    color = MaterialTheme.colorScheme.tertiary,
                                                )
                                            }
                                        }
                                    }

                                    Status.REJECTED -> {
                                        Column(modifier = it) {
                                            Text(
                                                text = "${if (out.outType == OutType.OUTING) "외출" else "외박"}이 거절되었어요",
                                                style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.tertiary,
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = "다시 신청하기",
                                                style = MaterialTheme.typography.titleSmall,
                                                color = MaterialTheme.colorScheme.primary,
                                            )
                                        }
                                    }

                                    Status.PENDING -> {
                                        Row(modifier = it) {
                                            DodamCircularProgress(
                                                progress = outProgress,
                                                color = MaterialTheme.colorScheme.tertiary,
                                                backgroundColor = MaterialTheme.colorScheme.secondary,
                                            )
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Column {
                                                Text(
                                                    text = "대기중",
                                                    style = MaterialTheme.typography.titleSmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = if (out.outType == OutType.OUTING) {
                                                        String.format(
                                                            "%02d:%02d 시작",
                                                            out.startAt.hour,
                                                            out.startAt.minute
                                                        )
                                                    } else String.format(
                                                        "%02d:%02d 시작",
                                                        out.startAt.monthNumber,
                                                        out.startAt.dayOfMonth
                                                    ),
                                                    style = MaterialTheme.typography.labelMedium,
                                                    color = MaterialTheme.colorScheme.tertiary,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    onClickContent = {
                        if (uiState.out.isEmpty()) {
                            // TODO : Navigate to outing request screen
                        } else {
                            // TODO : Navigate to outing screen
                        }
                    }
                )

                Spacer(modifier = Modifier.width(12.dp))

                DodamContainer(
                    modifier = Modifier.weight(1f),
                    icon = MoonPlus,
                    title = "심야 자습",
                    content = {

                    },
                    onClickContent = {}
                )
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
