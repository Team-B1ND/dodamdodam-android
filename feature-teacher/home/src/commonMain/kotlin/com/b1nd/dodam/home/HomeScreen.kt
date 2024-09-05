package com.b1nd.dodam.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.b1nd.dodam.data.banner.model.Banner
import com.b1nd.dodam.data.banner.model.BannerStatus
import com.b1nd.dodam.data.meal.model.Meal
import com.b1nd.dodam.data.meal.model.MealDetail
import com.b1nd.dodam.data.meal.model.Menu
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ActionIcon
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamContentTopAppBar
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.designsystem.foundation.DodamShapes
import com.b1nd.dodam.designsystem.resources.Res
import com.b1nd.dodam.home.card.BannerCard
import com.b1nd.dodam.home.card.MealCard
import com.b1nd.dodam.home.card.NightStudyCard
import com.b1nd.dodam.home.card.OutCard
import com.b1nd.dodam.home.model.BannerUiState
import com.b1nd.dodam.home.model.MealUiState
import com.b1nd.dodam.ui.component.DodamContainer
import com.b1nd.dodam.ui.icons.DodamLogo
import io.ktor.utils.io.ByteReadChannel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource


@Composable
internal fun HomeScreen(

) {
    Scaffold(
        modifier = Modifier.background(DodamTheme.colors.backgroundNeutral),
        topBar = {
            DodamContentTopAppBar(
                modifier = Modifier
                    .background(DodamTheme.colors.backgroundNeutral)
                    .statusBarsPadding(),
                content = {
                    Column {
                        Row {
                            Spacer(Modifier.width(16.dp))
                            Icon(
                                modifier = Modifier.width(90.dp),
                                imageVector = DodamLogo,
                                contentDescription = null,
                                tint = DodamTheme.colors.primaryNormal,
                            )
                        }
                    }
                },
                actionIcons = persistentListOf(
                    ActionIcon(
                        icon = DodamIcons.Bell,
                        onClick = {}
                    )
                )
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.TopCenter,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DodamTheme.colors.backgroundNeutral)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item {
                    BannerCard(
                        state = BannerUiState.Success(
                            persistentListOf(
                                Banner(
                                    id = 0,
                                    imageUrl = "https://dodam.kr.object.ncloudstorage.com/dodam/6634113f-951b-430c-81c9-957de0e8abddalimo.png",
                                    redirectUrl = "https://dodam.b1nd.com",
                                    title = "test",
                                    status = BannerStatus.ACTIVE,
                                    expireAt = kotlinx.datetime.LocalDateTime.parse("2024-01-26T13:48:25.623088")
                                )
                            )
                        )
                    )
                }
                item {

                    MealCard(
                        state = MealUiState.Success(
                            data = Meal(
                                exists = false,
                                date = LocalDate.fromEpochDays(150000),
                                breakfast = MealDetail(
                                    details = listOf(
                                        Menu(name = "쇠고기우엉볶음밥", allergies = listOf()),
                                        Menu(name = "오이생채", allergies = listOf()),
                                        Menu(name = "불고기치즈파니니", allergies = listOf()),
                                        Menu(name = "배추김치", allergies = listOf()),
                                        Menu(name = "계란실파국", allergies = listOf())
                                    ),
                                    calorie = 30f
                                ),
                                lunch = null,
                                dinner = null
                            )
                        ),
                        onClickContent = {},
                        onClickRefresh = {}
                    )
                }

                item {
                    OutCard()
                }

                item {
                    NightStudyCard()
                }
            }
        }
    }
}

@Composable
internal fun DefaultText(onClick: () -> Unit, label: String, body: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBounceIndication(),
            ),
        contentAlignment = Alignment.Center

    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.CenterStart)
        ) {
            Text(
                text = label,
                style = DodamTheme.typography.caption1Medium(),
                color = DodamTheme.colors.labelAlternative
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = body,
                style = DodamTheme.typography.body1Bold().copy(fontWeight = FontWeight.Bold),
                color = DodamTheme.colors.primaryNormal
            )
        }
    }
}

@Composable
internal fun InnerCountCard(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                style = DodamTheme.typography.labelMedium(),
                color = DodamTheme.colors.labelAssistive
            )
            Text(
                text = content,
                style = DodamTheme.typography.heading2Medium(),
                color = DodamTheme.colors.labelNormal
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(8.dp))
        DodamButton(
            text = buttonText,
            onClick = onClick,
            buttonRole = ButtonRole.Assistive,
            buttonSize = ButtonSize.Small,
        )
    }
}