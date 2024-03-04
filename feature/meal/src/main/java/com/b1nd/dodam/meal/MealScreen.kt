package com.b1nd.dodam.meal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.MealCard
import com.b1nd.dodam.designsystem.theme.DodamTheme
import com.b1nd.dodam.model.Meal

@Composable
fun MealScreen() {
    val meals = listOf(
        Meal(
            date = "2021-10-01",
            exists = true,
            breakfast = "토스트, 계란후라이, 우유*",
            lunch = "김치찌#개",
            dinner = "비빔밥",
            existsCalorie = true,
            breakfastCalorie = "300",
            lunchCalorie = "500",
            dinnerCalorie = "400",
        ),
        Meal(
            date = "2021-10-02",
            exists = true,
            breakfast = null,
            lunch = "김치찌개",
            dinner = "비빔밥",
            existsCalorie = true,
            breakfastCalorie = "300",
            lunchCalorie = "500",
            dinnerCalorie = "400",
        ),
    )
    Column {
        DodamTopAppBar(title = "급식", containerColor = MaterialTheme.colorScheme.surface)
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            itemsIndexed(meals) { _, meal ->
                Card(
                    modifier = Modifier
                        .padding(bottom = 20.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.inverseSurface,
                    )
                ) {
                    Text(
                        text = meal.date,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier
                            .padding(horizontal = 63.dp, vertical = 6.dp)
                    )
                }
                if (!meal.breakfast.isNullOrBlank()) {
                    MealCard(
                        title = "아침",
                        calorie = meal.breakfastCalorie?.toInt() ?: 0,
                        menu = meal.breakfast ?: "",
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                    )
                }
                if (!meal.lunch.isNullOrBlank()) {
                    MealCard(
                        title = "점심",
                        calorie = meal.lunchCalorie?.toInt() ?: 0,
                        menu = meal.lunch ?: "",
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                    )
                }
                if (!meal.dinner.isNullOrBlank()) {
                    MealCard(
                        title = "저녁",
                        calorie = meal.dinnerCalorie?.toInt() ?: 0,
                        menu = meal.dinner ?: "",
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 20.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFE2F1FD)
fun MealScreenPreview() {
    DodamTheme {
        MealScreen()
    }
}
