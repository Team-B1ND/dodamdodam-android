package com.b1nd.dodam.student.home.card

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.b1nd.dodam.dds.foundation.DodamShape
import com.b1nd.dodam.student.home.PagerIndicator
import com.b1nd.dodam.student.home.model.BannerUiState

@ExperimentalFoundationApi
@Composable
internal fun BannerCard(uiState: BannerUiState, context: Context) {
    when (uiState) {
        is BannerUiState.Success -> {
            val banners = remember { uiState.data }
            val bannerPagerState = rememberPagerState { banners.size }
            if (banners.isNotEmpty()) {
                Box {
                    HorizontalPager(
                        state = bannerPagerState,
                    ) { page ->
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(DodamShape.Large)
                                .clickable {
                                    context.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(banners[page].redirectUrl),
                                        ),
                                    )
                                },
                            model = banners[page].imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                        )
                    }
                    PagerIndicator(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 16.dp, bottom = 10.dp),
                        pagerState = bannerPagerState,
                    )
                }
            }
        }

        is BannerUiState.None -> {}
    }
}
