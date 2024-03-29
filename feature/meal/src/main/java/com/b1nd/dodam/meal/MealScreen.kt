package com.b1nd.dodam.meal

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.b1nd.dodam.data.meal.model.MealDetail
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.shimmerEffect
import com.b1nd.dodam.designsystem.theme.DodamTheme
import com.b1nd.dodam.meal.viewmodel.MealViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.math.roundToInt

@Composable
fun MealColumn(date: LocalDate, isActive: Boolean = false, content: @Composable ColumnScope.() -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (isActive) MaterialTheme.colorScheme.primary.copy(alpha = 0.65f) else MaterialTheme.colorScheme.secondary,
                    RoundedCornerShape(100),
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
                        "일",
                    )[date.dayOfWeek.value - 1],
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = if (isActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.tertiary,
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            content()
        }
    }
}

@Composable
fun MealScreen(viewModel: MealViewModel = hiltViewModel()) {
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
            .fillMaxSize(),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            DodamTopAppBar(
                title = "급식",
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                state = lazyListState,
            ) {
                if (!uiState.isLoading) {
                    items(uiState.meal.size) { index ->
                        val isToday = index == 0
                        if (!isCanScroll) {
                            val nextMonth = current.plusMonths(1)
                            viewModel.fetchMealOfMonth(nextMonth.year, nextMonth.monthValue)
                        }

                        val meal = uiState.meal[index]
                        val date = meal.date
                        MealColumn(
                            isActive = isToday,
                            date = LocalDate.of(date.year, date.monthNumber, date.dayOfMonth),
                        ) {
                            meal.breakfast?.let { meal ->
                                MealCard(
                                    title = "아침",
                                    meal = meal,
                                    isActive = isToday && currentMealType == 1,
                                )
                            }

                            meal.lunch?.let { meal ->
                                MealCard(
                                    title = "점심",
                                    meal = meal,
                                    isActive = isToday && currentMealType == 2,
                                )
                            }

                            meal.dinner?.let { meal ->
                                MealCard(
                                    title = "저녁",
                                    meal = meal,
                                    isActive = isToday && currentMealType == 3,
                                )
                            }
                        }
                    }
                } else if (viewModel.isFirst.value == true) {
                    items(3) { index ->
                        val isToday = index == 0
                        MealColumn(
                            isActive = isToday,
                            date = LocalDate.now(),
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
                            .padding(bottom = 100.dp),
                    )
                }
            }
        }
        if (!uiState.isLoading && uiState.meal.isEmpty()) {
            EmptyMeal(text = "이번 달 급식이 없어요", modifier = Modifier.align(Alignment.Center))
        }
//        TODO : composable for allergy sign
//        DodamTopAppBar(
//            title = "",
//            contentColor = MaterialTheme.colorScheme.onSurface,
//            containerColor = Color.Transparent,
//            modifier = Modifier.background(
//                MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
//            )
//        )
    }
}

private fun isBetween(startHour: Int, startMinute: Int, endHour: Int, endMinute: Int): Boolean {
    val current = LocalDateTime.now()
    val startDate = LocalDateTime.of(
        current.toLocalDate(),
        LocalTime.of(startHour, startMinute),
    )
    val endDate = LocalDateTime.of(
        current.toLocalDate(),
        LocalTime.of(endHour, endMinute),
    )
    return current.isAfter(startDate) && current.isBefore(endDate)
}

@Composable
fun MealCard(modifier: Modifier = Modifier, isLoading: Boolean = false, isActive: Boolean = false, title: String = "", meal: MealDetail? = null) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(18.dp))
            .padding(16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (!isLoading) {
                Box(
                    modifier = Modifier
                        .background(
                            if (isActive) {
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.65f)
                            } else {
                                MaterialTheme.colorScheme.tertiary
                            },
                            shape = RoundedCornerShape(100),
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp),
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
                            shape = RoundedCornerShape(100),
                        )
                        .width(50.dp)
                        .height(25.dp)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
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
                            shape = RoundedCornerShape(100),
                        )
                        .width(60.dp)
                        .height(20.dp),
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
                widths.forEach { size ->
                    Box(
                        modifier = Modifier
                            .heightIn(19.dp)
                            .widthIn(size.dp)
                            .background(
                                brush = shimmerEffect(),
                                RoundedCornerShape(8.dp),
                            ),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
fun EmptyMeal(text: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = com.b1nd.dodam.designsystem.R.drawable.ic_empty),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMealCard() {
    DodamTheme {
        EmptyMeal("이번 달 급식이 없어요")
    }
}
