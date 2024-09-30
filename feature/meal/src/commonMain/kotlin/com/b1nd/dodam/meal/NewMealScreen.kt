package com.b1nd.dodam.meal

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.times
import androidx.compose.ui.util.fastForEach
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.CalendarDate
import com.b1nd.dodam.designsystem.component.CalendarMonth
import com.b1nd.dodam.designsystem.component.DodamDatePicker
import com.b1nd.dodam.designsystem.component.DodamDatePickerDefaults
import com.b1nd.dodam.designsystem.component.DodamDatePickerState
import com.b1nd.dodam.designsystem.component.rememberDodamDatePickerState
import com.b1nd.dodam.logging.KmLog
import com.b1nd.dodam.logging.KmLogging

class NewMealScreen {
}

@Composable
internal fun ExpandableCalendar(
    datePickerState: DodamDatePickerState = rememberDodamDatePickerState()
) {
    val weekdayNames = datePickerState.weekdayNames
    val month = datePickerState.month
    val selectDate = datePickerState.selectedDate
    val getUtcTimeMillis = datePickerState::getUtcTimeMillis
    val isValidDate = datePickerState::validDate
    val onClickDate: (CalendarDate, Boolean) -> Unit = { date, isValid ->
        datePickerState.selectedDate = date
    }

    val cellHeight = (55.33).dp
    val cellCount = MAX_CALENDAR_ROWS
    val cellAllHeight = cellHeight * cellCount
    val scrollRange = cellHeight * (cellCount - 1)

    var offsetY by remember { mutableStateOf(0.dp) }
    val animateHeight by animateDpAsState(targetValue = cellAllHeight + offsetY, label = "")
    var scrollRatio by remember { mutableFloatStateOf(0f) }

    var clickRowIndex by remember { mutableIntStateOf(0) }

    val animateOffsetY by animateDpAsState(targetValue = (138.dp - cellHeight * clickRowIndex) * scrollRatio, label = "")

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
                    textAlign = TextAlign.Center,
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
                        }
                    )
                }
                .height(animateHeight)
                .clip(RoundedCornerShape(0.dp))

        ) {
            Column(
                modifier = Modifier
                    .offset(y = animateOffsetY)
                    .requiredHeight(cellAllHeight)
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
                                        dayNumber
                                    )
                                )
                                val isValid = isValidDate(date)

                                val selectContainerColor =
                                    DodamDatePickerDefaults.SelectDayContainerColor
                                val backgroundModifier = if (isSelect) Modifier.drawBehind {
                                    val boxWidth = size.width
                                    val indicatorSize = 38.dp.toPx()
                                    val offsetX = (boxWidth - indicatorSize) / 2
                                    drawRoundRect(
                                        color = selectContainerColor,
                                        cornerRadius = CornerRadius(DodamDatePickerDefaults.SelectDayShape.toPx()),
                                        size = Size(indicatorSize, indicatorSize),
                                        topLeft = Offset(
                                            x = offsetX,
                                            y = -(9).dp.toPx(),
                                        ),
                                    )
                                } else Modifier
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable(
                                            onClick = {
                                                clickRowIndex = weekIndex
                                                onClickDate(date, isValid)
                                            },
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = rememberBounceIndication(
                                                showBackground = false
                                            )
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