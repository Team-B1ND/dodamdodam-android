package com.b1nd.dodam.meal

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import com.b1nd.dodam.data.meal.model.MealDetail
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.shimmerEffect
import com.b1nd.dodam.designsystem.theme.DodamTheme
import com.b1nd.dodam.meal.viewmodel.MealViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.math.roundToInt

@Composable
fun MealColumn(
    date: LocalDate,
    isActive: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inverseSurface,
                    RoundedCornerShape(100)
                )
                .padding(horizontal = 60.dp, vertical = 6.dp),
        ) {
            Text(
                text = String.format(
                    "%d월 %d일 %s요일",
                    date.monthValue,
                    date.dayOfMonth,
                    arrayOf(
                        "월",
                        "화",
                        "수",
                        "목",
                        "금",
                        "토",
                        "일"
                    )[date.dayOfWeek.value - 1]
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = if (isActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.inverseOnSurface
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MealScreen(
    viewModel: MealViewModel = hiltViewModel(),
) {
    val current = LocalDateTime.now()
    val uiState by viewModel.uiState.collectAsState()

    val currentMealType = when {
        isBetween(8, 40, 13, 0) -> 2
        isBetween(13, 0, 18, 40) -> 3
        isBetween(18, 40, 23, 59) -> 0
        else -> 1
    }
    val lazyListState = rememberLazyListState()
    val isCanScroll = lazyListState.canScrollForward
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                state = lazyListState,
            ) {
                item {
                    Spacer(
                        modifier = Modifier.height(
                            28.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                        )
                    )
                }
                if (!uiState.isLoading) {
                    items(uiState.meal.size) { index ->
                        if (!isCanScroll) {
                            val nextMonth = current.plusMonths(1)
                            Log.d("MealScreen: ", "${nextMonth.year}년 ${nextMonth.monthValue}월")
                            viewModel.fetchMealOfMonth(nextMonth.year, nextMonth.monthValue)
                        }

                        val meal = uiState.meal[index]
                        val date = meal.date
                        MealColumn(
                            isActive = index == 0,
                            date = LocalDate.of(date.year, date.monthNumber, date.dayOfMonth)
                        ) {

                            meal.breakfast?.let { meal ->
                                MealCard(
                                    title = "아침",
                                    meal = meal,
                                    isActive = index == 0 && currentMealType == 1,
                                )
                            }

                            meal.lunch?.let { meal ->
                                MealCard(
                                    title = "점심",
                                    meal = meal,
                                    isActive = index == 0 && currentMealType == 2,
                                )
                            }

                            meal.dinner?.let { meal ->
                                MealCard(
                                    title = "저녁",
                                    meal = meal,
                                    isActive = index == 0 && currentMealType == 3,
                                )
                            }
                        }
                    }
                } else if (viewModel.isFirst.value == true) {
                    items(3) { index ->
                        MealColumn(
                            isActive = index == 0,
                            date = LocalDate.now()
                        ) {
                            MealCard(isLoading = true)
                            MealCard(isLoading = true)
                            MealCard(isLoading = true)
                        }
                    }
                }
                item {
                    if (!uiState.endReached) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .padding(bottom = 20.dp)
                    )
                }
            }
        }
        DodamTopAppBar(
            title = "급식",
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surface,
        )
    }
}

private fun isBetween(startHour: Int, startMinute: Int, endHour: Int, endMinute: Int): Boolean {
    val current = LocalDateTime.now()
    val startDate = LocalDateTime.of(
        current.toLocalDate(),
        LocalTime.of(startHour, startMinute)
    )
    val endDate = LocalDateTime.of(
        current.toLocalDate(),
        LocalTime.of(endHour, endMinute)
    )
    return current.isAfter(startDate) && current.isBefore(endDate)
}

@Composable
fun MealCard(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isActive: Boolean = false,
    title: String = "",
    meal: MealDetail? = null,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(20.dp))
            .padding(16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!isLoading) {
                Box(
                    modifier = Modifier
                        .background(
                            if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(100)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .background(
                            brush = shimmerEffect(),
                            shape = RoundedCornerShape(100)
                        )
                        .width(50.dp)
                        .height(25.dp)
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (!isLoading) {
                Text(
                    text = meal?.calorie?.roundToInt().toString() + "Kcal",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            } else {
                Box(
                    modifier = Modifier
                        .background(
                            brush = shimmerEffect(),
                            shape = RoundedCornerShape(100)
                        )
                        .width(60.dp)
                        .height(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        if (!isLoading) {
            Column {
                meal?.details?.forEach {
                    Row {
                        Text(
                            text = it.name,
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
//                        TODO : composable for allergy sign
//                        Spacer(modifier = Modifier.weight(1f))
//                        Text(
//                            text = it.allergies.joinToString("."),
//                            modifier = Modifier,
//                            style = MaterialTheme.typography.bodyMedium,
//                            color = MaterialTheme.colorScheme.tertiary,
//                        )
                    }
                }
            }
        } else {
            Column {
                val widths = listOf(150, 100, 75, 120, 60, 80)
                repeat(6) { index ->
                    Box(
                        modifier = Modifier
                            .heightIn(19.dp)
                            .widthIn(widths[index].dp)
                            .background(
                                brush = shimmerEffect(),
                                RoundedCornerShape(8.dp)
                            )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMealCard() {
    DodamTheme {
        Column {
            MealCard(title = "아침", isLoading = true)
            MealCard(title = "아침")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun example() {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(3000)
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .pullRefresh(state)//state 적용
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(
                    if (refreshing) { //새로고침 중이면 높이 고정
                        140.dp
                    } else { //당기기 정도에 따라 0~140dp까지 크기가 늘어남
                        lerp(0.dp, 140.dp, state.progress.coerceIn(0f..1f))
                    }
                )
        ) {
            if (refreshing) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(70.dp)
                        .align(Alignment.Center),
                    color = Color.Red,
                    strokeWidth = 3.dp,
                )
            }
        }
    }
}
