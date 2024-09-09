package com.b1nd.dodam.home.card

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import coil3.compose.AsyncImage
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.home.model.BannerUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun BannerCard(state: BannerUiState) {
    when (state) {
        is BannerUiState.None -> {}
        is BannerUiState.Success -> {
            val banners = remember { state.data }
            val bannerPagerState = rememberPagerState { state.data.size }
            val urlHandler = LocalUriHandler.current
            Box(
                modifier = Modifier
                    .background(DodamTheme.colors.backgroundNeutral),
            ) {
                HorizontalPager(
                    state = bannerPagerState,
                ) { page ->
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(DodamTheme.shapes.large)
                            .clickable {
                                urlHandler.openUri(banners[page].redirectUrl)
                            },
                        model = banners[page].imageUrl,
                        onError = {
                            it.result.throwable.printStackTrace()
                        },
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                    )
                }
            }
        }
    }
}
