package com.b1nd.dodam.home.card

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.data.meal.model.Meal
import com.b1nd.dodam.data.meal.model.MealDetail
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.DodamLoadingDots
import com.b1nd.dodam.designsystem.component.DodamPageIndicator
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.home.DefaultText
import com.b1nd.dodam.home.model.MealUiState
import com.b1nd.dodam.ui.component.DodamContainer

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MealCard(
    state: MealUiState,
    showShimmer: Boolean,
    onClickContent: () -> Unit,
    onClickRefresh: () -> Unit
) {
    if (!showShimmer) {
        DodamContainer(
            icon = DodamIcons.ForkAndKnife,
            title = "오늘의 급식"
        ) {
            when (state) {
                is MealUiState.Success -> {
                    val mealState = rememberPagerState { 3 }
                    Column {
                        HorizontalPager(
                            state = mealState,
                            modifier = Modifier
                                .padding(
                                    top = 6.dp,
                                    start = 6.dp,
                                    end = 6.dp,
                                )
                                .animateContentSize()
                                .clickable(
                                    onClick = onClickContent,
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberBounceIndication()
                                )
                        ) { index ->
                            val meals = index.getMeal(state.data)?.details
                            if (meals != null) {
                                Column {
                                    for (i in meals.indices step 2) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = meals[i].name,
                                                style = DodamTheme.typography.body1Medium(),
                                                color = DodamTheme.colors.labelNormal
                                            )
                                            Spacer(Modifier.weight(1f))
                                            Text(
                                                text = meals.getOrNull(i)?.name ?: "",
                                                style = DodamTheme.typography.body1Medium(),
                                                color = DodamTheme.colors.labelNormal
                                            )
                                            Spacer(Modifier.weight(1f))
                                        }
                                    }
                                }
                            } else {
                                DefaultText(
                                    onClick = onClickContent,
                                    label = "오늘은 급식이 없어요",
                                    body = "내일 급식 보러가기",
                                )
                            }
                        }
                        DodamPageIndicator(
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .align(Alignment.End),
                            pagerState = mealState
                        )
                    }
                }

                is MealUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        DodamLoadingDots()
                    }
                }

                is MealUiState.Error -> {
                    DefaultText(
                        onClick = onClickRefresh,
                        label = "급식을 불러올 수 없어요",
                        body = "다시 불러오기"
                    )
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center,
        ) {
            DodamLoadingDots()
        }
    }
}

private fun Int.getMeal(meal: Meal): MealDetail? =
    when (this) {
        0 -> meal.breakfast
        1 -> meal.lunch
        else -> meal.dinner
    }
