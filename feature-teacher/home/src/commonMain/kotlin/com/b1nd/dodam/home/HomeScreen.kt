package com.b1nd.dodam.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.b1nd.dodam.data.banner.model.Banner
import com.b1nd.dodam.data.banner.model.BannerStatus
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ActionIcon
import com.b1nd.dodam.designsystem.component.DodamContentTopAppBar
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.designsystem.resources.Res
import com.b1nd.dodam.home.card.BannerCard
import com.b1nd.dodam.home.model.BannerUiState
import com.b1nd.dodam.ui.icons.DodamLogo
import io.ktor.utils.io.ByteReadChannel
import kotlinx.collections.immutable.persistentListOf
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
            }
        }
    }
}