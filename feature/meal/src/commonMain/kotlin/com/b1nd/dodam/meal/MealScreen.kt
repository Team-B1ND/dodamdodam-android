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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.data.meal.model.Menu
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ActionIcon
import com.b1nd.dodam.designsystem.component.DodamContentTopAppBar
import com.b1nd.dodam.designsystem.component.DodamDefaultTopAppBar
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.meal.viewmodel.MealUiState
import com.b1nd.dodam.meal.viewmodel.MealViewModel
import com.b1nd.dodam.ui.component.DodamCard
import com.b1nd.dodam.ui.effect.shimmerEffect
import com.b1nd.dodam.ui.icons.DodamLogo
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.plus
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import kotlin.math.roundToInt

@OptIn(KoinExperimentalAPI::class)
@ExperimentalMaterial3Api
@Composable
fun MealScreen(
    viewModel: MealViewModel = koinViewModel()
) {
    val uiState by viewModel.mealUiState.collectAsState()
    val mealScreenState = rememberMealScreenState()

    LaunchedEffect(mealScreenState.canScrollForward) {
        if (!mealScreenState.canScrollForward) {
            val date = mealScreenState.currentDate.plus(DatePeriod(months = 1))
            viewModel.getMealOfMonth(date.year, date.monthNumber)
        }
    }

    Scaffold(
        topBar = {
            DodamDefaultTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DodamTheme.colors.backgroundNeutral)
                    .statusBarsPadding(),
                title = "급식",
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(DodamTheme.colors.backgroundNeutral)
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
                                                if (mealScreenState.isDateInToday(meal.date)) {
                                                    DodamTheme.colors.primaryNormal
                                                } else {
                                                    DodamTheme.colors.lineNeutral
                                                },
                                                CircleShape,
                                            )
                                            .padding(horizontal = 60.dp, vertical = 4.dp),
                                    ) {
                                        Text(
                                            text = "${meal.date.monthNumber}월 ${meal.date.dayOfMonth}일 (" +
                                                    listOf(
                                                        "월",
                                                        "화",
                                                        "수",
                                                        "목",
                                                        "금",
                                                        "토",
                                                        "일",
                                                    )[meal.date.dayOfWeek.isoDayNumber - 1] + ")",
                                            style = DodamTheme.typography.labelMedium(),
                                            color = if (mealScreenState.isDateInToday(meal.date)) DodamTheme.colors.backgroundNormal else DodamTheme.colors.labelNeutral
                                        )
                                    }

                                    meal.breakfast?.let { breakfast ->
                                        MealCard(
                                            mealType = "아침",
                                            statusColor = if (mealScreenState.isDateInToday(meal.date) && mealScreenState.mealTime == MealTime.BREAKFAST) {
                                                DodamTheme.colors.primaryNormal
                                            } else {
                                                DodamTheme.colors.lineNormal
                                            },
                                            calorie = breakfast.calorie,
                                            menus = breakfast.details,
                                        )
                                    }

                                    meal.lunch?.let { lunch ->
                                        MealCard(
                                            mealType = "점심",
                                            statusColor = if (mealScreenState.isDateInToday(meal.date) && mealScreenState.mealTime == MealTime.LUNCH) {
                                                DodamTheme.colors.primaryNormal
                                            } else {
                                                DodamTheme.colors.lineNormal
                                            },
                                            calorie = lunch.calorie,
                                            menus = lunch.details,
                                        )
                                    }

                                    meal.dinner?.let { dinner ->
                                        MealCard(
                                            mealType = "저녁",
                                            statusColor = if (mealScreenState.isDateInToday(meal.date) && mealScreenState.mealTime == MealTime.DINNER) {
                                                DodamTheme.colors.primaryNormal
                                            } else {
                                                DodamTheme.colors.lineNormal
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
                                Text(
                                    text = "이번 달 급식이 없어요.",
                                    style = DodamTheme.typography.labelBold(),
                                    color = DodamTheme.colors.fillAlternative
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
        Text(
            text = menus.joinToString("\n") { it.name },
            style = DodamTheme.typography.body1Medium(),
            color = DodamTheme.colors.labelNormal
        )
    }
}
