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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.b1nd.dodam.designsystem.animation.bounceClick
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
import com.b1nd.dodam.student.home.model.MealUiState
import com.b1nd.dodam.student.home.model.NightStudyUiState
import com.b1nd.dodam.student.home.model.OutUiState
import com.b1nd.dodam.student.home.model.WakeupSongUiState
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()

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

            var mealTitle by remember { mutableStateOf("오늘의 급식") }

            DodamContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                icon = ForkAndKnife,
                title = mealTitle,
                content = {
                    when (val mealUiState = uiState.mealUiState) {
                        is MealUiState.Success -> {
                            val mealPagerState = rememberPagerState { mealUiState.data.size }
                            val filteredMeal = mealUiState.data.filterNotNull()

                            if (filteredMeal.isEmpty()) {
                                DefaultText(
                                    onClick = { /* TODO : Navigate to Meal Screen*/ },
                                    label = "오늘은 급식이 없어요",
                                    body = "내일 급식 보러가기"
                                )
                            } else {
                                mealTitle = "오늘의 " + when (filteredMeal[mealPagerState.currentPage]) {
                                    mealUiState.data[0] -> "아침"
                                    mealUiState.data[1] -> "점심"
                                    mealUiState.data[2] -> "저녁"
                                    else -> "급식"
                                }

                                HorizontalPager(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp)
                                        .bounceClick {
                                            // TODO : Navigate to Meal Screen
                                        }
                                        .padding(6.dp),
                                    state = mealPagerState
                                ) { page ->
                                    Text(
                                        text = filteredMeal[page],
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                                PagerIndicator(
                                    modifier = Modifier
                                        .align(Alignment.End)
                                        .padding(end = 16.dp),
                                    size = mealPagerState.pageCount,
                                    currentPage = mealPagerState.currentPage,
                                )
                            }
                        }

                        is MealUiState.Loading -> {
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
                        }

                        is MealUiState.Error -> {

                        }
                    }
                }
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
                        when (val wakeupSongUiState = uiState.wakeupSongUiState) {
                            is WakeupSongUiState.Success -> {
                                val wakeupSongPagerState =
                                    rememberPagerState { wakeupSongUiState.data.size }

                                if (wakeupSongUiState.data.isEmpty()) {
                                    DefaultText(
                                        onClick = { /* TODO : Navigate to request wakeup song screen */ },
                                        label = "승인된 기상송이 없어요",
                                        body = "기상송 신청하기"
                                    )
                                } else {
                                    HorizontalPager(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp)
                                            .bounceClick {
                                                context.startActivity(
                                                    Intent(
                                                        Intent.ACTION_VIEW,
                                                        Uri.parse(wakeupSongUiState.data[wakeupSongPagerState.currentPage].videoUrl)
                                                    )
                                                )
                                            }
                                            .padding(6.dp),
                                        state = wakeupSongPagerState
                                    ) { page ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                        ) {
                                            AsyncImage(
                                                modifier = Modifier
                                                    .size(100.dp)
                                                    .clip(RoundedCornerShape(12.dp)),
                                                model = wakeupSongUiState.data[page].thumbnailUrl,
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                            )

                                            Spacer(modifier = Modifier.width(12.dp))

                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                            ) {
                                                Text(
                                                    text = wakeupSongUiState.data[page].videoTitle,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                )

                                                Spacer(modifier = Modifier.height(4.dp))

                                                Text(
                                                    text = wakeupSongUiState.data[page].channelTitle,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = MaterialTheme.colorScheme.tertiary,
                                                )
                                            }
                                        }
                                    }
                                    PagerIndicator(
                                        modifier = Modifier
                                            .align(Alignment.End)
                                            .padding(end = 16.dp),
                                        size = wakeupSongPagerState.pageCount,
                                        currentPage = wakeupSongPagerState.currentPage,
                                    )
                                }
                            }

                            is WakeupSongUiState.Loading -> {
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
                            }

                            is WakeupSongUiState.Error -> {

                            }
                        }
                    }
                }
            )

            Row {
                DodamContainer(
                    modifier = Modifier.weight(1f),
                    icon = Door,
                    title = "외출 외박",
                    content = {
                        when (val outUiState = uiState.outUiState) {
                            is OutUiState.Success -> {
                                outUiState.data?.let { out ->
                                    val outProgress = 1 - ChronoUnit.MICROS.between(
                                        out.startAt.toJavaLocalDateTime(),
                                        current
                                    ).toFloat() / ChronoUnit.MICROS.between(
                                        out.startAt.toJavaLocalDateTime(),
                                        out.endAt.toJavaLocalDateTime()
                                    )

                                    when (out.status) {
                                        Status.ALLOWED -> {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 10.dp)
                                                    .bounceClick {
                                                        // TODO : Navigate to my out screen
                                                    }
                                                    .padding(6.dp)
                                            ) {
                                                DodamCircularProgress(
                                                    progress = outProgress,
                                                    color = MaterialTheme.colorScheme.primary,
                                                    backgroundColor = MaterialTheme.colorScheme.secondary,
                                                )
                                                Spacer(modifier = Modifier.width(12.dp))
                                                Column {
                                                    Text(
                                                        text = buildAnnotatedString {
                                                            val date =
                                                                out.endAt.toJavaLocalDateTime()
                                                                    .minusHours(current.hour.toLong())
                                                                    .minusMinutes(current.minute.toLong())

                                                            when (out.outType) {
                                                                OutType.OUTING -> {
                                                                    if (date.hour > 0) append("${date.hour}시간 ${date.minute}분 ")
                                                                    else append("${date.minute}분 ")
                                                                }

                                                                OutType.SLEEPOVER -> {
                                                                    date.minusDays(current.dayOfMonth.toLong())
                                                                    if (date.dayOfMonth > 0) append("${date.dayOfMonth}일 ")
                                                                    else if (date.hour > 0) "${date.hour}시간 ${date.minute}분 "
                                                                    else "${date.minute}분 "
                                                                }
                                                            }
                                                            withStyle(
                                                                style = MaterialTheme.typography.labelMedium.copy(
                                                                    MaterialTheme.colorScheme.tertiary
                                                                ).toSpanStyle()
                                                            ) {
                                                                append("남음")
                                                            }
                                                        },
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                    )

                                                    Spacer(modifier = Modifier.height(4.dp))

                                                    Text(
                                                        text = when (out.outType) {
                                                            OutType.OUTING -> String.format(
                                                                "%02d:%02d 복귀",
                                                                out.endAt.hour,
                                                                out.endAt.minute
                                                            )

                                                            OutType.SLEEPOVER -> String.format(
                                                                "%02d:%02d 까지",
                                                                out.endAt.monthNumber,
                                                                out.endAt.dayOfMonth
                                                            )
                                                        },
                                                        style = MaterialTheme.typography.labelMedium,
                                                        color = MaterialTheme.colorScheme.tertiary,
                                                    )
                                                }
                                            }
                                        }

                                        Status.REJECTED -> {
                                            DefaultText(
                                                onClick = { /* TODO : Navigate to request out screen */ },
                                                label = "${if (out.outType == OutType.OUTING) "외출" else "외박"}이 거절되었어요",
                                                body = "다시 신청하기"
                                            )
                                        }

                                        Status.PENDING -> {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 10.dp)
                                                    .bounceClick {
                                                        // TODO : Navigate to my out screen
                                                    }
                                                    .padding(6.dp)
                                            ) {
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
                                } ?: run {
                                    DefaultText(
                                        onClick = { /* TODO : Navigate to request Out Screen */ },
                                        label = "외출, 외박이 필요하다면",
                                        body = "외출/외박 신청하기"
                                    )
                                }
                            }

                            is OutUiState.Loading -> {
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
                            }

                            is OutUiState.Error -> {

                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.width(12.dp))

                DodamContainer(
                    modifier = Modifier.weight(1f),
                    icon = MoonPlus,
                    title = "심야 자습",
                    content = {
                        when (val nightStudyUiState = uiState.nightStudyUiState) {
                            is NightStudyUiState.Success -> {
                                nightStudyUiState.data?.let { nightStudy ->
                                    val nightStudyProgress = 1 - ChronoUnit.MICROS.between(
                                        nightStudy.startAt.toJavaLocalDate(),
                                        current
                                    ).toFloat() / ChronoUnit.MICROS.between(
                                        nightStudy.startAt.toJavaLocalDate(),
                                        nightStudy.endAt.toJavaLocalDate()
                                    )

                                    when (nightStudy.status) {
                                        Status.ALLOWED -> {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 10.dp)
                                                    .bounceClick {
                                                        // TODO : Navigate to my night study screen
                                                    }
                                                    .padding(6.dp)
                                            ) {
                                                DodamCircularProgress(
                                                    progress = nightStudyProgress,
                                                    color = MaterialTheme.colorScheme.primary,
                                                    backgroundColor = MaterialTheme.colorScheme.secondary,
                                                )
                                                Spacer(modifier = Modifier.width(12.dp))
                                                Column {
                                                    Text(
                                                        text = buildAnnotatedString {
                                                            val date =
                                                                nightStudy.endAt.toJavaLocalDate()
                                                                    .minusDays(current.dayOfMonth.toLong())
                                                            append("${date.dayOfMonth}일 ")
                                                            withStyle(
                                                                style = MaterialTheme.typography.labelMedium.copy(
                                                                    MaterialTheme.colorScheme.tertiary
                                                                ).toSpanStyle()
                                                            ) {
                                                                append("남음")
                                                            }
                                                        },
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                    )

                                                    Spacer(modifier = Modifier.height(4.dp))

                                                    Text(
                                                        text = String.format(
                                                            "%02d:%02d 까지",
                                                            nightStudy.endAt.monthNumber,
                                                            nightStudy.endAt.dayOfMonth
                                                        ),
                                                        style = MaterialTheme.typography.labelMedium,
                                                        color = MaterialTheme.colorScheme.tertiary,
                                                    )
                                                }
                                            }
                                        }

                                        Status.REJECTED -> {
                                            DefaultText(
                                                onClick = { /* TODO : Navigate to request night study screen */ },
                                                label = "심야 자습이 거절되었어요",
                                                body = "다시 신청하기"
                                            )
                                        }

                                        Status.PENDING -> {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 10.dp)
                                                    .bounceClick {
                                                        // TODO : Navigate to my night study screen
                                                    }
                                                    .padding(6.dp)
                                            ) {
                                                DodamCircularProgress(
                                                    progress = nightStudyProgress,
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
                                                        text =  String.format(
                                                            "%02d:%02d 시작",
                                                            nightStudy.startAt.monthNumber,
                                                            nightStudy.startAt.dayOfMonth
                                                        ),
                                                        style = MaterialTheme.typography.labelMedium,
                                                        color = MaterialTheme.colorScheme.tertiary,
                                                    )
                                                }
                                            }
                                        }
                                    }
                                } ?: run {
                                    DefaultText(
                                        onClick = { /* TODO : Navigate to request night study screen */ },
                                        label = "공부할 시간이 필요하다면",
                                        body = "심야 자습 신청하기"
                                    )
                                }
                            }

                            is NightStudyUiState.Loading -> {
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
                            }

                            is NightStudyUiState.Error -> {

                            }
                        }
                    }
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

@Composable
private fun PagerIndicator(
    modifier: Modifier = Modifier,
    size: Int,
    currentPage: Int,
) {
    Row(modifier = modifier) {
        repeat(size) { iteration ->
            val color =
                if (currentPage == iteration) {
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

@Composable
private fun DefaultText(
    onClick: () -> Unit,
    label: String,
    body: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .bounceClick(onClick)
            .padding(6.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.tertiary,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = body,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
