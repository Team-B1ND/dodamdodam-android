package com.b1nd.dodam.meal

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFilter
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastJoinToString
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.CalendarDate
import com.b1nd.dodam.designsystem.component.DividerType
import com.b1nd.dodam.designsystem.component.DodamDatePickerDefaults
import com.b1nd.dodam.designsystem.component.DodamDatePickerState
import com.b1nd.dodam.designsystem.component.DodamDefaultTopAppBar
import com.b1nd.dodam.designsystem.component.DodamDivider
import com.b1nd.dodam.designsystem.component.DodamTag
import com.b1nd.dodam.designsystem.component.TagType
import com.b1nd.dodam.designsystem.component.rememberDodamDatePickerState
import com.b1nd.dodam.meal.model.MealUiState
import com.b1nd.dodam.meal.viewmodel.MealViewModel
import com.b1nd.dodam.ui.effect.shimmerEffect
import com.b1nd.dodam.ui.icons.ColoredCookedRice
import kotlin.math.roundToInt
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun MealScreen(viewModel: MealViewModel = koinViewModel()) {
    val datePickerState = rememberDodamDatePickerState()
    val uiState by viewModel.mealUiState.collectAsState()
    val nowDate = DodamDate.localDateNow()

    LaunchedEffect(true) {
        viewModel.getMealOfMonth(nowDate.year, nowDate.monthNumber)
    }

    Scaffold(
        topBar = {
            DodamDefaultTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "${nowDate.monthNumber}월 급식",
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize()
                .padding(it)
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ExpandableCalendar(
                datePickerState = datePickerState,
                onClickDate = { date, isValid ->
                    datePickerState.selectedDate = date
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            DodamDivider(type = DividerType.Normal)
            Spacer(modifier = Modifier.height(16.dp))

            when (uiState) {
                is MealUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        val uiState = uiState as MealUiState.Success

                        val meal = uiState.meals.fastFilter {
                            it.date.dayOfMonth == datePickerState.selectedDate?.dayOfMonth
                        }.firstOrNull()

                        if (meal != null) {
                            if (meal.breakfast != null) {
                                MealCard(
                                    tagText = "아침",
                                    content = meal.breakfast!!.details.fastJoinToString("\n") { it.name },
                                    kcalText = "${meal.breakfast!!.calorie.roundToInt()}Kcal",
                                )
                            }

                            if (meal.lunch != null) {
                                MealCard(
                                    tagText = "점심",
                                    content = meal.lunch!!.details.fastJoinToString("\n") { it.name },
                                    kcalText = "${meal.lunch!!.calorie.roundToInt()}Kcal",
                                )
                            }

                            if (meal.dinner != null) {
                                MealCard(
                                    tagText = "저녁",
                                    content = meal.dinner!!.details.fastJoinToString("\n") { it.name },
                                    kcalText = "${meal.dinner!!.calorie.roundToInt()}Kcal",
                                )
                            }

                            Spacer(modifier = Modifier.height(100.dp))
                        } else {
                            NotFoundMeal()
                        }
                    }
                }
                MealUiState.Error -> {
                    NotFoundMeal()
                }
                MealUiState.Loading -> {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = DodamTheme.colors.backgroundNormal,
                        shape = DodamTheme.shapes.large,
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .height(28.dp)
                                        .width(52.dp)
                                        .background(
                                            brush = shimmerEffect(),
                                            shape = RoundedCornerShape(12.dp),
                                        ),
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Box(
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(54.dp)
                                        .background(
                                            brush = shimmerEffect(),
                                            shape = RoundedCornerShape(12.dp),
                                        ),
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .background(
                                        brush = shimmerEffect(),
                                        shape = RoundedCornerShape(12.dp),
                                    ),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NotFoundMeal() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.size(36.dp),
                imageVector = ColoredCookedRice,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "급식이 없어요",
                style = DodamTheme.typography.labelMedium(),
                color = DodamTheme.colors.labelAlternative.copy(alpha = 0.5f),
            )
        }
    }
}

@Composable
internal fun MealCard(modifier: Modifier = Modifier, tagText: String, content: String, kcalText: String) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = DodamTheme.colors.backgroundNormal,
        shape = DodamTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DodamTag(
                    text = tagText,
                    tagType = TagType.Primary,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = kcalText,
                    style = DodamTheme.typography.labelMedium(),
                    color = DodamTheme.colors.labelAlternative,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = content,
                style = DodamTheme.typography.body1Medium(),
                color = DodamTheme.colors.labelNormal,
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Composable
internal fun ExpandableCalendar(
    datePickerState: DodamDatePickerState = rememberDodamDatePickerState(),
    onClickDate: (date: CalendarDate, isValid: Boolean) -> Unit,
    isValidDate: (date: CalendarDate) -> Boolean = datePickerState::validDate,
    getUtcTimeMillis: (year: Int, month: Int, dayOfMonth: Int) -> Long = datePickerState::getUtcTimeMillis,
) {
    val weekdayNames = datePickerState.weekdayNames
    val month = datePickerState.month
    val selectDate = datePickerState.selectedDate

    val cellHeight = (36).dp
    val cellCount = when {
        (30 == month.numberOfDays && month.daysFromStartOfWeekToFirstOfMonth == 6) ||
            (31 == month.numberOfDays && (month.daysFromStartOfWeekToFirstOfMonth == 5 || month.daysFromStartOfWeekToFirstOfMonth == 6)) -> 6
        (28 == month.numberOfDays && month.daysFromStartOfWeekToFirstOfMonth == 0) -> 4
        else -> 5
    }
    val cellAllHeight = cellHeight * cellCount
    val scrollRange = cellHeight * (cellCount - 1)

    var scrollRatio by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableStateOf(0.dp) }

    val animateHeight by animateDpAsState(targetValue = cellAllHeight + offsetY + (12.dp * scrollRatio), label = "")

    var clickRowIndex by remember { mutableIntStateOf(0) }

    val baseOffSetPadding = when (cellCount) {
        4 -> 54.dp
        5 -> 72.dp
        6 -> 90.dp
        else -> 0.dp
    }

    val animateOffsetY by animateDpAsState(targetValue = (baseOffSetPadding + -cellHeight * clickRowIndex) * scrollRatio, label = "")

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth(),
    ) {
        maxWidth
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            weekdayNames.fastForEach { weekdayName ->
                Text(
                    modifier = Modifier.weight(1f),
                    text = weekdayName,
                    style = DodamTheme.typography.labelRegular(),
                    textAlign = TextAlign.Center,
                    color = DodamTheme.colors.labelAlternative,
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .pointerInput(Unit) {
                    detectVerticalDragGestures(
                        onDragEnd = {
                            if (scrollRatio >= 0.5f) {
                                offsetY = -(cellAllHeight - cellHeight)
                                scrollRatio = 1f
                            } else {
                                offsetY = 0.dp
                                scrollRatio = 0f
                            }
                        },
                        onDragCancel = {
                            if (scrollRatio >= 0.5f) {
                                offsetY = -(cellAllHeight - cellHeight)
                                scrollRatio = 1f
                            } else {
                                offsetY = 0.dp
                                scrollRatio = 0f
                            }
                        },
                        onVerticalDrag = { change, dragAmount ->
                            val transDp = dragAmount.toDp()
                            if (cellAllHeight + (offsetY + transDp) < cellHeight) {
                                offsetY = -(cellAllHeight - cellHeight)
                                scrollRatio = 1f
                                return@detectVerticalDragGestures
                            }

                            if (0.dp < offsetY + transDp) {
                                offsetY = 0.dp
                                scrollRatio = 0f
                                return@detectVerticalDragGestures
                            }
                            // offsetY = 음수
                            offsetY += dragAmount.toDp()

                            scrollRatio = (-offsetY.value / scrollRange.value).coerceIn(0f, 1f)
                        },
                    )
                }
                .height(animateHeight)
                .clip(RoundedCornerShape(0.dp)),

        ) {
            Column(
                modifier = Modifier
                    .offset(y = animateOffsetY)
                    .requiredHeight(cellAllHeight),
            ) {
                val maxCalendarRows = MAX_CALENDAR_ROWS

                for (weekIndex in 0 until maxCalendarRows) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(cellHeight),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        for (dayIndex in 0 until DAYS_IN_WEEK) {
                            val cellIndex = weekIndex * DAYS_IN_WEEK + dayIndex
                            // 3
                            if (
                                cellIndex < month.daysFromStartOfWeekToFirstOfMonth ||
                                cellIndex >=
                                (month.daysFromStartOfWeekToFirstOfMonth + month.numberOfDays)
                            ) {
                                Spacer(
                                    modifier = Modifier.weight(1f),
                                )
                            } else {
                                val dayNumber =
                                    cellIndex - month.daysFromStartOfWeekToFirstOfMonth + 1
                                val isSelect = dayNumber == selectDate?.dayOfMonth &&
                                    month.month == selectDate.month &&
                                    month.year == selectDate.year
                                val date = CalendarDate(
                                    year = month.year,
                                    month = month.month,
                                    dayOfMonth = dayNumber,
                                    utcTimeMillis = getUtcTimeMillis(
                                        month.year,
                                        month.month,
                                        dayNumber,
                                    ),
                                )
                                val isValid = isValidDate(date)

                                val selectContainerColor =
                                    DodamDatePickerDefaults.SelectDayContainerColor
                                val backgroundModifier = if (isSelect) {
                                    Modifier.drawBehind {
                                        val boxWidth = size.width
                                        val indicatorSize = 36.dp.toPx()
                                        val offsetX = (boxWidth - indicatorSize) / 2
                                        drawRoundRect(
                                            color = selectContainerColor,
                                            cornerRadius = CornerRadius(DodamDatePickerDefaults.SelectDayShape.toPx()),
                                            size = Size(indicatorSize, indicatorSize),
                                            topLeft = Offset(
                                                x = offsetX,
                                                y = -(0.5).dp.toPx(),
                                            ),
                                        )
                                    }
                                } else {
                                    Modifier
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(cellHeight)
                                        .clickable(
                                            onClick = {
                                                clickRowIndex = weekIndex
                                                onClickDate(date, isValid)
                                            },
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = rememberBounceIndication(
                                                showBackground = false,
                                            ),
                                        )
                                        .then(backgroundModifier),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        modifier = Modifier,
                                        text = dayNumber.toString(),
                                        style = DodamTheme.typography.headlineMedium(),
                                        color = when {
                                            isSelect -> DodamTheme.colors.staticWhite
                                            isValid -> DodamTheme.colors.labelAlternative
                                            else -> DodamTheme.colors.labelAlternative.copy(alpha = 0.5f)
                                        },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

internal const val MAX_CALENDAR_ROWS = 6
internal const val DAYS_IN_WEEK = 7
