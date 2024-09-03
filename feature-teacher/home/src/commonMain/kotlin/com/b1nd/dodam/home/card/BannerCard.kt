package com.b1nd.dodam.home.card

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.memory.MemoryCache
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import coil3.util.DebugLogger
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.home.model.BannerUiState
import com.b1nd.dodam.logging.KmLogging

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun BannerCard(
    state: BannerUiState
) {
    when (state) {
        is BannerUiState.None -> {}
        is BannerUiState.Success -> {
            val banners = remember { state.data }
            val bannerPagerState = rememberPagerState { state.data.size }
            val urlHandler = LocalUriHandler.current
            Box(
                modifier = Modifier
                    .background(DodamTheme.colors.backgroundNeutral)
            ) {

                HorizontalPager(
                    state = bannerPagerState
                ) { page ->
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(DodamTheme.shapes.large)
                            .clickable {
                                urlHandler.openUri(banners[page].redirectUrl)
                            },
                        model = banners[page].imageUrl,
                        placeholder = rememberVectorPainter(DodamIcons.Note.value),
                        error = rememberVectorPainter(DodamIcons.XMarkCircle.value),
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