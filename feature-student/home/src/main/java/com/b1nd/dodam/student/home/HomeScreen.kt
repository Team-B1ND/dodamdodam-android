package com.b1nd.dodam.student.home

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.outing.model.OutType
import com.b1nd.dodam.data.schedule.model.Grade
import com.b1nd.dodam.data.schedule.model.Schedule
import com.b1nd.dodam.designsystem.animation.bounceClick
import com.b1nd.dodam.designsystem.component.DodamCircularProgress
import com.b1nd.dodam.designsystem.component.DodamContainer
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.LoadingDotsIndicator
import com.b1nd.dodam.designsystem.component.shimmerEffect
import com.b1nd.dodam.designsystem.icons.Bell
import com.b1nd.dodam.designsystem.icons.Calendar
import com.b1nd.dodam.designsystem.icons.DodamLogo
import com.b1nd.dodam.designsystem.icons.Door
import com.b1nd.dodam.designsystem.icons.ForkAndKnife
import com.b1nd.dodam.designsystem.icons.MoonPlus
import com.b1nd.dodam.designsystem.icons.Note
import com.b1nd.dodam.student.home.model.BannerUiState
import com.b1nd.dodam.student.home.model.MealUiState
import com.b1nd.dodam.student.home.model.NightStudyUiState
import com.b1nd.dodam.student.home.model.OutUiState
import com.b1nd.dodam.student.home.model.ScheduleUiState
import com.b1nd.dodam.student.home.model.WakeupSongUiState
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.plus
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
internal fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val current = LocalDateTime.now()

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.run {
                fetchMeal()
                fetchWakeupSong()
                fetchOut()
                fetchNightStudy()
                fetchSchedule()
                fetchBanner()
            }
        },
    )

    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState),
        contentAlignment = Alignment.TopCenter,
    ) {
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
                    46.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                ),
            )

            when (val bannerUiState = uiState.bannerUiState) {
                is BannerUiState.Success -> {
                    if (bannerUiState.data.isNotEmpty()) {
                        val bannerPagerState = rememberPagerState { bannerUiState.data.size }

                        Box {
                            HorizontalPager(
                                state = bannerPagerState,
                            ) { page ->
                                AsyncImage(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(20.dp))
                                        .clickable {
                                            context.startActivity(
                                                Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse(bannerUiState.data[page].redirectUrl),
                                                ),
                                            )
                                        },
                                    model = bannerUiState.data[page].imageUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth,
                                )
                            }
                            PagerIndicator(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 16.dp, bottom = 16.dp),
                                size = bannerPagerState.pageCount,
                                currentPage = bannerPagerState.currentPage,
                            )
                        }
                    }
                }

                is BannerUiState.None -> {}
            }

            var mealTitle by remember { mutableStateOf("오늘의 급식") }

            DodamContainer(
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = spring(
                            stiffness = Spring.StiffnessLow,
                        ),
                    ),
                icon = ForkAndKnife,
                title = mealTitle,
                content = {
                    when (val mealUiState = uiState.mealUiState) {
                        is MealUiState.Success -> {
                            isRefreshing = false
                            val filteredMeal = mealUiState.data.filterNotNull()
                            val mealPagerState = rememberPagerState { filteredMeal.size }

                            val currentTime = current.toLocalTime()

                            LaunchedEffect(Unit) {
                                if (currentTime <= LocalTime.of(8, 10)) { // 아침 식사 시간 전이라면
                                    mealUiState.data[0]?.let { // 아침 급식이 있다면
                                        mealPagerState.animateScrollToPage(0)
                                    }
                                } else if (currentTime <= LocalTime.of(13, 30)) { // 점심 식사 시간 전이라면
                                    mealUiState.data[0]?.let { // 아침 급식이 있다면
                                        mealUiState.data[1]?.let { // 점심 급식이 있다면
                                            mealPagerState.animateScrollToPage(1)
                                        }
                                    } ?: run { // 아침 급식이 없다면
                                        mealUiState.data[1]?.let { // 점심 급식이 있다면
                                            mealPagerState.animateScrollToPage(0)
                                        }
                                    }
                                } else if (currentTime <= LocalTime.of(19, 10)) { // 저녁 식사 시간 전이라면
                                    mealUiState.data[0]?.let { // 아침 급식이 있다면
                                        mealUiState.data[1]?.let { // 점심 급식이 있다면
                                            mealPagerState.animateScrollToPage(2)
                                        } ?: run { // 점심 급식이 없다면
                                            mealPagerState.animateScrollToPage(1)
                                        }
                                    } ?: run { // 아침 급식이 없다면
                                        mealUiState.data[1]?.let { // 점심 급식이 있다면
                                            mealPagerState.animateScrollToPage(1)
                                        } ?: run { // 점심 급식이 없다면
                                            mealPagerState.animateScrollToPage(0)
                                        }
                                    }
                                } else { // 저녁 식사 시간이 지났다면
                                    mealUiState.data[0]?.let { // 다음날 아침이 있다면
                                        mealPagerState.animateScrollToPage(0)
                                    }
                                }
                            }

                            if (filteredMeal.isEmpty()) {
                                DefaultText(
                                    onClick = { /* TODO : Navigate to Meal Screen*/ },
                                    label = "오늘은 급식이 없어요",
                                    body = "내일 급식 보러가기",
                                )
                            } else {
                                mealTitle =
                                    if (currentTime > LocalTime.of(
                                            19,
                                            10,
                                        )
                                    ) {
                                        "내일의 "
                                    } else {
                                        "오늘의 "
                                    } + when (filteredMeal[mealPagerState.currentPage]) {
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
                                    state = mealPagerState,
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

                        is MealUiState.Shimmer -> {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp),
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

                        is MealUiState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                LoadingDotsIndicator()
                            }
                        }

                        is MealUiState.Error -> {
                            isRefreshing = false
                            DefaultText(
                                onClick = { viewModel.fetchMeal() },
                                label = "급식을 불러올 수 없어요",
                                body = "다시 불러오기",
                            )
                        }
                    }
                },
            )

            DodamContainer(
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = spring(
                            stiffness = Spring.StiffnessLow,
                        ),
                    ),
                icon = Note,
                title = "오늘의 기상송",
                showNextButton = true,
                onNextClick = {
                    // TODO : Navigate to wakeup song screen
                },
                content = {
                    when (val wakeupSongUiState = uiState.wakeupSongUiState) {
                        is WakeupSongUiState.Success -> {
                            val wakeupSongPagerState =
                                rememberPagerState { wakeupSongUiState.data.size }

                            if (wakeupSongUiState.data.isEmpty()) {
                                DefaultText(
                                    onClick = { /* TODO : Navigate to request wakeup song screen */ },
                                    label = "승인된 기상송이 없어요",
                                    body = "기상송 신청하기",
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
                                                    Uri.parse(wakeupSongUiState.data[wakeupSongPagerState.currentPage].videoUrl),
                                                ),
                                            )
                                        }
                                        .padding(6.dp),
                                    state = wakeupSongPagerState,
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

                        is WakeupSongUiState.Shimmer -> {
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
                                onClick = { viewModel.fetchWakeupSong() },
                                label = "기상송을 불러올 수 없어요",
                                body = "다시 불러오기",
                            )
                        }
                    }
                },
            )

            Row {
                DodamContainer(
                    modifier = Modifier
                        .animateContentSize(
                            animationSpec = spring(
                                stiffness = Spring.StiffnessLow,
                            ),
                        )
                        .weight(1f)
                        .fillMaxHeight(),
                    icon = Door,
                    title = "외출 외박",
                    content = {
                        when (val outUiState = uiState.outUiState) {
                            is OutUiState.Success -> {
                                outUiState.data?.let { out ->
                                    val outProgress = 1 - ChronoUnit.SECONDS.between(
                                        out.startAt.toJavaLocalDateTime(),
                                        current,
                                    ).toFloat() / ChronoUnit.SECONDS.between(
                                        out.startAt.toJavaLocalDateTime(),
                                        out.endAt.toJavaLocalDateTime(),
                                    )

                                    when (out.status) {
                                        Status.ALLOWED -> {
                                            if (outProgress < 0) {
                                                DefaultText(
                                                    onClick = { /* TODO : Navigate to request Out Screen */ },
                                                    label = "외출, 외박이 필요하다면",
                                                    body = "외출/외박 신청하기",
                                                )
                                            } else {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(horizontal = 10.dp)
                                                        .bounceClick {
                                                            // TODO : Navigate to my out screen
                                                        }
                                                        .padding(6.dp),
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
                                                                val day = ChronoUnit.DAYS.between(
                                                                    current,
                                                                    out.endAt.toJavaLocalDateTime(),
                                                                )
                                                                val hour = ChronoUnit.HOURS.between(
                                                                    current,
                                                                    out.endAt.toJavaLocalDateTime(),
                                                                )
                                                                val minute =
                                                                    ChronoUnit.MINUTES.between(
                                                                        current,
                                                                        out.endAt.toJavaLocalDateTime(),
                                                                    )

                                                                when (out.outType) {
                                                                    OutType.OUTING -> {
                                                                        append(
                                                                            if (hour > 0) {
                                                                                "${hour}시간 "
                                                                            } else {
                                                                                "${minute}분 "
                                                                            },
                                                                        )
                                                                    }

                                                                    OutType.SLEEPOVER -> {
                                                                        append(
                                                                            if (day > 0) {
                                                                                "${day}일 "
                                                                            } else if (hour > 0) {
                                                                                "${hour}시간 "
                                                                            } else {
                                                                                "${minute}분 "
                                                                            },
                                                                        )
                                                                    }
                                                                }
                                                                withStyle(
                                                                    style = MaterialTheme.typography.labelMedium.copy(
                                                                        MaterialTheme.colorScheme.tertiary,
                                                                    ).toSpanStyle(),
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
                                                                    out.endAt.minute,
                                                                )

                                                                OutType.SLEEPOVER -> String.format(
                                                                    "%02d.%02d 까지",
                                                                    out.endAt.monthNumber,
                                                                    out.endAt.dayOfMonth,
                                                                )
                                                            },
                                                            style = MaterialTheme.typography.labelMedium,
                                                            color = MaterialTheme.colorScheme.tertiary,
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                        Status.REJECTED -> {
                                            DefaultText(
                                                onClick = { /* TODO : Navigate to request out screen */ },
                                                label = "${if (out.outType == OutType.OUTING) "외출" else "외박"}이 거절되었어요",
                                                body = "다시 신청하기",
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
                                                    .padding(6.dp),
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
                                                                out.startAt.minute,
                                                            )
                                                        } else {
                                                            String.format(
                                                                "%02d.%02d 시작",
                                                                out.startAt.monthNumber,
                                                                out.startAt.dayOfMonth,
                                                            )
                                                        },
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
                                        body = "외출/외박 신청하기",
                                    )
                                }
                            }

                            is OutUiState.Shimmer -> {
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

                            is OutUiState.Loading -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    LoadingDotsIndicator()
                                }
                            }

                            is OutUiState.Error -> {
                                DefaultText(
                                    onClick = { viewModel.fetchOut() },
                                    label = "외출, 외박을 불러올 수 없어요",
                                    body = "다시 불러오기",
                                )
                            }
                        }
                    },
                )

                Spacer(modifier = Modifier.width(12.dp))

                DodamContainer(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .animateContentSize(
                            animationSpec = spring(
                                stiffness = Spring.StiffnessLow,
                            ),
                        ),
                    icon = MoonPlus,
                    title = "심야 자습",
                    content = {
                        when (val nightStudyUiState = uiState.nightStudyUiState) {
                            is NightStudyUiState.Success -> {
                                nightStudyUiState.data?.let { nightStudy ->
                                    val nightStudyProgress = 1 - ChronoUnit.SECONDS.between(
                                        nightStudy.startAt.toJavaLocalDateTime(),
                                        current,
                                    ).toFloat() / ChronoUnit.SECONDS.between(
                                        nightStudy.startAt.toJavaLocalDateTime(),
                                        nightStudy.endAt.toJavaLocalDateTime(),
                                    )

                                    when (nightStudy.status) {
                                        Status.ALLOWED -> {
                                            if (nightStudyProgress < 0) {
                                                DefaultText(
                                                    onClick = { /* TODO : Navigate to request night study screen */ },
                                                    label = "공부할 시간이 필요하다면",
                                                    body = "심야 자습 신청하기",
                                                )
                                            } else {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(horizontal = 10.dp)
                                                        .bounceClick {
                                                            // TODO : Navigate to my night study screen
                                                        }
                                                        .padding(6.dp),
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
                                                                val day = ChronoUnit.DAYS.between(
                                                                    current,
                                                                    nightStudy.endAt.toJavaLocalDateTime(),
                                                                )
                                                                val hour = ChronoUnit.HOURS.between(
                                                                    current,
                                                                    nightStudy.endAt.toJavaLocalDateTime(),
                                                                )
                                                                val minute =
                                                                    ChronoUnit.MINUTES.between(
                                                                        current,
                                                                        nightStudy.endAt.toJavaLocalDateTime(),
                                                                    )

                                                                append(
                                                                    if (day > 0) {
                                                                        "${day}일 "
                                                                    } else if (hour > 0) {
                                                                        "${hour}시간 "
                                                                    } else {
                                                                        "${minute}분 "
                                                                    },
                                                                )
                                                                withStyle(
                                                                    style = MaterialTheme.typography.labelMedium.copy(
                                                                        MaterialTheme.colorScheme.tertiary,
                                                                    ).toSpanStyle(),
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
                                                                nightStudy.endAt.dayOfMonth,
                                                            ),
                                                            style = MaterialTheme.typography.labelMedium,
                                                            color = MaterialTheme.colorScheme.tertiary,
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                        Status.REJECTED -> {
                                            DefaultText(
                                                onClick = { /* TODO : Navigate to request night study screen */ },
                                                label = "심야 자습이 거절되었어요",
                                                body = "다시 신청하기",
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
                                                    .padding(6.dp),
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
                                                        text = String.format(
                                                            "%02d.%02d 시작",
                                                            nightStudy.startAt.monthNumber,
                                                            nightStudy.startAt.dayOfMonth,
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
                                        body = "심야 자습 신청하기",
                                    )
                                }
                            }

                            is NightStudyUiState.Shimmer -> {
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

                            is NightStudyUiState.Loading -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    LoadingDotsIndicator()
                                }
                            }

                            is NightStudyUiState.Error -> {
                                DefaultText(
                                    onClick = { viewModel.fetchNightStudy() },
                                    label = "심야 자습을 불러올 수 없어요",
                                    body = "다시 불러오기",
                                )
                            }
                        }
                    },
                )
            }

            DodamContainer(
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = spring(
                            stiffness = Spring.StiffnessLow,
                        ),
                    ),
                icon = Calendar,
                title = "가까운 일정",
                showNextButton = true,
                onNextClick = { },
            ) {
                when (val scheduleUiState = uiState.scheduleUiState) {
                    is ScheduleUiState.Success -> {
                        if (scheduleUiState.data.isEmpty()) {
                            DefaultText(
                                onClick = { /*TODO : Navigate to schedule screen*/ },
                                label = "한 달간 일정이 없어요",
                                body = "전체 일정 확인하기",
                            )
                        } else {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateContentSize()
                                    .padding(horizontal = 10.dp)
                                    .bounceClick { /*TODO : Navigate to schedule screen*/ }
                                    .padding(6.dp),
                            ) {
                                val currentDate = current.toKotlinLocalDateTime().date
                                val latestSchedule = scheduleUiState.data.first()

                                ScheduleComponent(
                                    modifier = Modifier.weight(1f),
                                    title = if (currentDate in latestSchedule.startDate..latestSchedule.endDate) {
                                        "오늘"
                                    } else if (currentDate.plus(DatePeriod(days = 1)) in latestSchedule.startDate..latestSchedule.endDate) {
                                        "내일"
                                    } else {
                                        "D - ${latestSchedule.startDate.dayOfYear - currentDate.dayOfYear}"
                                    },
                                    titleColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    label = if (currentDate in latestSchedule.startDate..latestSchedule.endDate) {
                                        String.format(
                                            "%d월 %d일 (%s)",
                                            currentDate.monthNumber,
                                            currentDate.dayOfMonth,
                                            listOf(
                                                "월",
                                                "화",
                                                "수",
                                                "목",
                                                "금",
                                                "토",
                                                "일",
                                            )[currentDate.dayOfWeek.value - 1],
                                        )
                                    } else {
                                        String.format(
                                            "%d월 %d일 (%s)",
                                            latestSchedule.startDate.monthNumber,
                                            latestSchedule.startDate.dayOfMonth,
                                            listOf(
                                                "월",
                                                "화",
                                                "수",
                                                "목",
                                                "금",
                                                "토",
                                                "일",
                                            )[latestSchedule.startDate.dayOfWeek.value - 1],
                                        )
                                    },
                                    body = scheduleUiState.data.filter {
                                        if (currentDate in latestSchedule.startDate..latestSchedule.endDate) { // 오늘 일정이 있다면
                                            currentDate in it.startDate..it.endDate
                                        } else if (currentDate.plus(DatePeriod(days = 1)) in latestSchedule.startDate..latestSchedule.endDate) { // 내일 일정이 있다면
                                            currentDate.plus(DatePeriod(days = 1)) in it.startDate..it.endDate
                                        } else {
                                            latestSchedule.startDate == it.startDate
                                        }
                                    },
                                )

                                val tomorrow = currentDate.plus(DatePeriod(days = 1))
                                val nextSchedule =
                                    if (latestSchedule.startDate != latestSchedule.endDate && tomorrow in latestSchedule.startDate..latestSchedule.endDate) {  // 내일 일정이 있다면
                                        latestSchedule
                                    } else { // 한 달 내의 다음 일정이 있는지 검사
                                        scheduleUiState.data.asSequence()
                                            .filter { latestSchedule.endDate < it.startDate }
                                            .firstOrNull()
                                    }

                                if (nextSchedule != null) { // 한 달 내의 다음 일정이 있다면
                                    Spacer(modifier = Modifier.width(12.dp))

                                    ScheduleComponent(
                                        modifier = Modifier.weight(1f),
                                        title = if (tomorrow in nextSchedule.startDate..nextSchedule.endDate) {
                                            "내일"
                                        } else {
                                            "D - ${nextSchedule.startDate.dayOfYear - currentDate.dayOfYear}"
                                        },
                                        titleColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        label = if (tomorrow in nextSchedule.startDate..nextSchedule.endDate) {
                                            String.format(
                                                "%d월 %d일 (%s)",
                                                tomorrow.monthNumber,
                                                tomorrow.dayOfMonth,
                                                listOf(
                                                    "월",
                                                    "화",
                                                    "수",
                                                    "목",
                                                    "금",
                                                    "토",
                                                    "일",
                                                )[tomorrow.dayOfWeek.value - 1],
                                            )
                                        } else {
                                            String.format(
                                                "%d월 %d일 (%s)",
                                                nextSchedule.startDate.monthNumber,
                                                nextSchedule.startDate.dayOfMonth,
                                                listOf(
                                                    "월",
                                                    "화",
                                                    "수",
                                                    "목",
                                                    "금",
                                                    "토",
                                                    "일",
                                                )[nextSchedule.startDate.dayOfWeek.value - 1],
                                            )
                                        },
                                        body = scheduleUiState.data.filter {
                                            if (tomorrow in nextSchedule.startDate..nextSchedule.endDate) {
                                                tomorrow in it.startDate..it.endDate
                                            } else {
                                                nextSchedule.startDate == it.startDate
                                            }
                                        },
                                    )
                                }
                            }
                        }
                    }

                    is ScheduleUiState.Shimmer -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(36.dp)
                                        .height(20.dp)
                                        .background(
                                            shimmerEffect(),
                                            RoundedCornerShape(4.dp),
                                        ),
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Box(
                                    modifier = Modifier
                                        .width(70.dp)
                                        .height(14.dp)
                                        .background(
                                            shimmerEffect(),
                                            RoundedCornerShape(4.dp),
                                        ),
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .width(120.dp)
                                        .height(20.dp)
                                        .background(
                                            shimmerEffect(),
                                            RoundedCornerShape(4.dp),
                                        ),
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Box(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(20.dp)
                                        .background(
                                            shimmerEffect(),
                                            RoundedCornerShape(4.dp),
                                        ),
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(
                                modifier = Modifier.weight(1f),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(36.dp)
                                        .height(20.dp)
                                        .background(
                                            shimmerEffect(),
                                            RoundedCornerShape(4.dp),
                                        ),
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Box(
                                    modifier = Modifier
                                        .width(70.dp)
                                        .height(14.dp)
                                        .background(
                                            shimmerEffect(),
                                            RoundedCornerShape(4.dp),
                                        ),
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .width(120.dp)
                                        .height(20.dp)
                                        .background(
                                            shimmerEffect(),
                                            RoundedCornerShape(4.dp),
                                        ),
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Box(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(20.dp)
                                        .background(
                                            shimmerEffect(),
                                            RoundedCornerShape(4.dp),
                                        ),
                                )
                            }
                        }
                    }

                    is ScheduleUiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            LoadingDotsIndicator()
                        }
                    }

                    is ScheduleUiState.Error -> {
                        DefaultText(
                            onClick = { viewModel.fetchSchedule() },
                            label = "일정을 불러올 수 없어요",
                            body = "다시 불러오기",
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(150.dp))
        }

        PullRefreshIndicator(
            modifier = Modifier.padding(
                top = 36.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
            ),
            refreshing = isRefreshing,
            state = pullRefreshState,
        )

        DodamTopAppBar(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
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
            isCollapsed = scrollState.value > 0,
        )
    }
}

@Composable
private fun PagerIndicator(modifier: Modifier = Modifier, size: Int, currentPage: Int) {
    if (size > 1) {
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
}

@Composable
private fun DefaultText(onClick: () -> Unit, label: String, body: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .bounceClick(onClick)
            .padding(6.dp),
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

@Composable
private fun ScheduleComponent(modifier: Modifier = Modifier, title: String, titleColor: Color, label: String, body: List<Schedule>) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = titleColor,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.tertiary,
        )

        Spacer(modifier = Modifier.height(8.dp))

        body.forEach {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            if (it.targetGrades.isEmpty()) {
                                Color(0xFF0167BC)
                            } else {
                                when (it.targetGrades.first()) {
                                    Grade.GRADE_1 -> Color(0xFFFCA800)
                                    Grade.GRADE_2 -> Color(0xFF3FBDE5)
                                    Grade.GRADE_3 -> Color(0xFFA252E1)
                                    Grade.GRADE_ALL -> Color(0xFFF97E6D)
                                    Grade.GRADE_ETC -> Color(0xFF0167BC)
                                }
                            },
                            CircleShape,
                        ),
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = it.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
