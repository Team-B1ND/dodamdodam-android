package com.b1nd.dodam.student.home.card

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.DodamLoadingDots
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.student.home.DefaultText
import com.b1nd.dodam.student.home.DodamContainer
import com.b1nd.dodam.student.home.PagerIndicator
import com.b1nd.dodam.student.home.model.MealUiState
import com.b1nd.dodam.ui.effect.shimmerEffect
import java.time.LocalTime

@ExperimentalFoundationApi
@Composable
fun MealCard(uiState: MealUiState, showShimmer: Boolean, navigateToMeal: () -> Unit, onContentClick: () -> Unit, fetchMeal: () -> Unit) {
    val currentTime = LocalTime.now()

    var playOnlyOnce by rememberSaveable { mutableStateOf(true) }
    var isRefreshing by remember { mutableStateOf(false) }

    val mealTitle by rememberSaveable { mutableStateOf("오늘의 급식") }

    DodamContainer(
        icon = DodamIcons.ForkAndKnife.value,
        title = mealTitle,
        showNextButton = true,
        onNextClick = navigateToMeal,
        content = {
            if (!showShimmer) {
                when (uiState) {
                    is MealUiState.Success -> {
                        val mealPagerState = rememberPagerState { 3 }

                        LaunchedEffect(Unit) {
                            if (isRefreshing || playOnlyOnce) {
                                when {
                                    currentTime <= LocalTime.of(8, 10) -> { // 아침 식사 시간 전이라면{
                                        mealPagerState.animateScrollToPage(page = 0)
                                    }

                                    currentTime <= LocalTime.of(13, 30) -> { // 점심 식사 시간 전이라면
                                        mealPagerState.animateScrollToPage(1)
                                    }

                                    currentTime <= LocalTime.of(19, 10) -> { // 저녁 식사 시간 전이라면
                                        mealPagerState.animateScrollToPage(2)
                                    }

                                    else -> { // 저녁 식사 시간이 지났다면
                                        mealPagerState.animateScrollToPage(2)
                                    }
                                }
                                playOnlyOnce = false
                                isRefreshing = false
                            }
                        }

                        Column {
                            HorizontalPager(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateContentSize()
                                    .padding(horizontal = 10.dp)
                                    .clickable(
                                        indication = rememberBounceIndication(),
                                        interactionSource = remember { MutableInteractionSource() },
                                        onClick = onContentClick,
                                    )
                                    .padding(6.dp),
                                state = mealPagerState,
                            ) { page ->
                                val meals = when (page) {
                                    0 -> uiState.data.breakfast
                                    1 -> uiState.data.lunch
                                    else -> uiState.data.dinner
                                }?.details

                                if (meals != null) {
                                    Column {
                                        for (i in meals.indices step 2) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                            ) {
                                                Text(
                                                    modifier = Modifier.weight(1f),
                                                    text = meals[i].name,
                                                    style = DodamTheme.typography.body1Medium(),
                                                    color = DodamTheme.colors.labelNormal,
                                                )
                                                Text(
                                                    modifier = Modifier.weight(1f),
                                                    text = meals.getOrNull(i + 1)?.name ?: "",
                                                    style = DodamTheme.typography.body1Medium(),
                                                    color = DodamTheme.colors.labelNormal,
                                                )
                                            }
                                        }
                                    }
                                } else {
                                    DefaultText(
                                        onClick = onContentClick,
                                        label = "오늘은 급식이 없어요",
                                        body = "내일 급식 보러가기",
                                    )
                                }
                            }

                            PagerIndicator(
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .padding(end = 16.dp),
                                pagerState = mealPagerState,
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
                        isRefreshing = true
                    }

                    is MealUiState.Error -> {
                        DefaultText(
                            onClick = fetchMeal,
                            label = "급식을 불러올 수 없어요",
                            body = "다시 불러오기",
                        )
                    }
                }
            } else {
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
        },
    )
}
