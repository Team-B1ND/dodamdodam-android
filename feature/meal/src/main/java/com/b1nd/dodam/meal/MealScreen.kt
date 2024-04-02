package com.b1nd.dodam.meal

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.b1nd.dodam.data.meal.model.Menu
import com.b1nd.dodam.dds.component.DodamTopAppBar
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.BodyMedium
import com.b1nd.dodam.dds.style.LabelLarge
import com.b1nd.dodam.meal.viewmodel.MealViewModel
import com.b1nd.dodam.ui.component.DodamCard
import com.b1nd.dodam.ui.effect.shimmerEffect
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.math.roundToInt
import kotlinx.datetime.toKotlinLocalDate

@ExperimentalMaterial3Api
@Composable
fun MealScreen(viewModel: MealViewModel = hiltViewModel()) {
    val current = LocalDate.now().toKotlinLocalDate()
    val uiState by viewModel.uiState.collectAsState()

    var plus by rememberSaveable { mutableLongStateOf(0) }

    val currentMealType = when {
        isBetween(8, 40, 13, 0) -> 2
        isBetween(13, 0, 18, 40) -> 3
        isBetween(18, 40, 23, 59) -> 0
        else -> 1
    }
    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState.canScrollForward) {
        if (!lazyListState.canScrollForward) {
            val date = LocalDate.now().plusMonths(plus)
            if (plus == 0L) {
                viewModel.getMealOfMonth(date.year, date.monthValue)
            } else if (plus == 1L) {
                viewModel.fetchMealOfMonth(date.year, date.monthValue)
            }
            plus++
        }
    }

    Scaffold(
        topBar = {
            Column {
                DodamTopAppBar(
                    title = { Text(text = "급식") },
                )
                AnimatedVisibility(lazyListState.canScrollBackward) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.outlineVariant),
                    )
                }
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = lazyListState,
        ) {
            if (!uiState.showShimmer) {
                if (uiState.meal.isNotEmpty()) {
                    items(
                        items = uiState.meal,
                        key = { it.date.toString() },
                    ) { meal ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            if (meal.exists) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            if (current == meal.date) {
                                                MaterialTheme.colorScheme.primary
                                            } else {
                                                MaterialTheme.colorScheme.secondaryContainer
                                            },
                                            CircleShape,
                                        )
                                        .padding(horizontal = 60.dp, vertical = 4.dp),
                                ) {
                                    BodyMedium(
                                        text = String.format(
                                            "%d월 %d일 (%s)",
                                            meal.date.monthNumber,
                                            meal.date.dayOfMonth,
                                            listOf(
                                                "월",
                                                "화",
                                                "수",
                                                "목",
                                                "금",
                                                "토",
                                                "일",
                                            )[meal.date.dayOfWeek.value - 1],
                                        ),
                                        color = if (current == meal.date) {
                                            MaterialTheme.colorScheme.onPrimary
                                        } else {
                                            MaterialTheme.colorScheme.onSecondaryContainer
                                        },
                                    )
                                }

                                meal.breakfast?.let { breakfast ->
                                    MealCard(
                                        mealType = "아침",
                                        statusColor = if (meal.date == current && currentMealType == 1) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                        },
                                        calorie = breakfast.calorie,
                                        menus = breakfast.details,
                                    )
                                }

                                meal.lunch?.let { lunch ->
                                    MealCard(
                                        mealType = "점심",
                                        statusColor = if (meal.date == current && currentMealType == 2) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                        },
                                        calorie = lunch.calorie,
                                        menus = lunch.details,
                                    )
                                }

                                meal.dinner?.let { dinner ->
                                    MealCard(
                                        mealType = "저녁",
                                        statusColor = if (meal.date == current && currentMealType == 3) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                        },
                                        calorie = dinner.calorie,
                                        menus = dinner.details,
                                    )
                                }
                            }
                        }
                    }
                } else {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            LabelLarge(
                                text = "이번 달 급식이 없어요.",
                                color = MaterialTheme.colorScheme.tertiary,
                            )
                        }
                    }
                }
            } else {
                items(30) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.surfaceContainer,
                                MaterialTheme.shapes.large,
                            )
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp, 27.dp)
                                    .background(shimmerEffect(), CircleShape),
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Box(
                                modifier = Modifier
                                    .size(52.dp, 20.dp)
                                    .background(shimmerEffect(), RoundedCornerShape(4.dp)),
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(140.dp, 18.dp)
                                    .background(shimmerEffect(), RoundedCornerShape(4.dp)),
                            )

                            Box(
                                modifier = Modifier
                                    .size(110.dp, 18.dp)
                                    .background(shimmerEffect(), RoundedCornerShape(4.dp)),
                            )

                            Box(
                                modifier = Modifier
                                    .size(60.dp, 18.dp)
                                    .background(shimmerEffect(), RoundedCornerShape(4.dp)),
                            )

                            Box(
                                modifier = Modifier
                                    .size(120.dp, 18.dp)
                                    .background(shimmerEffect(), RoundedCornerShape(4.dp)),
                            )

                            Box(
                                modifier = Modifier
                                    .size(110.dp, 18.dp)
                                    .background(shimmerEffect(), RoundedCornerShape(4.dp)),
                            )
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
private fun MealCard(mealType: String, statusColor: Color, calorie: Float, menus: List<Menu>) {
    DodamCard(
        statusText = mealType,
        statusColor = statusColor,
        labelText = "${calorie.roundToInt()} Kcal",
    ) {
        BodyLarge(text = menus.joinToString("\n") { it.name })
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
