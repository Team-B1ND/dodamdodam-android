package com.b1nd.dodam.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.theme.DodamTheme

/**
 * Dodam MealCard
 * @author wsi1212
 * 
 * @param modifier - the Modifier to be applied to this card
 * @param shape - defines the shape of this card's container, border (when border is not null), and shadow (when using elevation)
 * @param colors - CardColors that will be used to resolve the colors used for this card in different states. See CardDefaults.cardColors.
 * @param elevation - CardElevation used to resolve the elevation for this card in different states. This controls the size of the shadow below the card. Additionally, when the container color is ColorScheme.surface, this controls the amount of primary color applied as an overlay. See also: Surface.
 * @param border - the border to draw around the container of this card
 * @param title - the title of the meal
 * @param calorie - the calorie of the meal
 * @param menu - the menu of the meal
 */

@Composable
fun MealCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation(),
    border: BorderStroke? = null,
    title: String,
    calorie: Int,
    menu: List<String>,
) {
    Card(
        shape = shape,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant),
        colors = colors,
        elevation = elevation,
        border = border,
    ) {
        Row(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 12.dp,
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = RoundedCornerShape(100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.titleSmall,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = calorie.toString() + "kcal",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
        Text(
            text = menu
                .joinToString("\n")
                .replace("[*#]".toRegex(), ""),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
@Preview
fun MealCardPreview() {
    DodamTheme {
        MealCard(
            title = "아침",
            calorie = 500,
            menu = listOf("*밥", "김치", "#동인동 찜갈비", "김")
        )
    }
}
