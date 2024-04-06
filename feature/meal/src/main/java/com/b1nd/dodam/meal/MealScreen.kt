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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.b1nd.dodam.data.meal.model.Menu
import com.b1nd.dodam.dds.component.DodamTopAppBar
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.BodyMedium
import com.b1nd.dodam.dds.style.LabelLarge
import com.b1nd.dodam.meal.viewmodel.MealUiState
import com.b1nd.dodam.meal.viewmodel.MealViewModel
import com.b1nd.dodam.ui.component.DodamCard
import com.b1nd.dodam.ui.effect.shimmerEffect
import kotlin.math.roundToInt

@ExperimentalMaterial3Api
@Composable
fun MealScreen(viewModel: MealViewModel = hiltViewModel()) {
    val uiState by viewModel.mealUiState.collectAsStateWithLifecycle()
    val mealScreenState = rememberMealScreenState()

    val color = MaterialTheme.colorScheme

    LaunchedEffect(mealScreenState.canScrollForward) {
        if (!mealScreenState.canScrollForward) {
            val date = mealScreenState.currentDate.plusMonths(1)
            viewModel.getMealOfMonth(date.year, date.monthValue)
        }
    }

    Scaffold(
        topBar = {
            Column {
                DodamTopAppBar(
                    title = { Text(text = "급식") },
                )
                AnimatedVisibility(mealScreenState.canScrollBackward) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color.outlineVariant),
                    )
                }
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color.surface)
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = mealScreenState.lazyListState,
        ) {
            when (val mealState = uiState) {
                is MealUiState.Success -> {
                    if (mealState.meals.isNotEmpty()) {
                        items(
                            items = mealState.meals,
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
                                                if (mealScreenState.isDateInToday(meal.date)) color.primary
                                                else color.secondaryContainer,
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
                                            color = if (mealScreenState.isDateInToday(meal.date))
                                                color.onPrimary else color.onSecondaryContainer,
                                        )
                                    }

                                    meal.breakfast?.let { breakfast ->
                                        MealCard(
                                            mealType = "아침",
                                            statusColor = if (mealScreenState.isDateInToday(meal.date) && mealScreenState.mealTime == MealTime.BREAKFAST)
                                                color.primary else color.onSurfaceVariant,
                                            calorie = breakfast.calorie,
                                            menus = breakfast.details,
                                        )
                                    }

                                    meal.lunch?.let { lunch ->
                                        MealCard(
                                            mealType = "점심",
                                            statusColor =if (mealScreenState.isDateInToday(meal.date) && mealScreenState.mealTime == MealTime.LUNCH)
                                                color.primary else color.onSurfaceVariant,
                                            calorie = lunch.calorie,
                                            menus = lunch.details,
                                        )
                                    }

                                    meal.dinner?.let { dinner ->
                                        MealCard(
                                            mealType = "저녁",
                                            statusColor = if (mealScreenState.isDateInToday(meal.date) && mealScreenState.mealTime == MealTime.DINNER)
                                                color.primary else color.onSurfaceVariant,
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
                }

                is MealUiState.Loading -> {
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

                is MealUiState.Error -> {

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
