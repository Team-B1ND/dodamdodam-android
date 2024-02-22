package com.b1nd.dodam.student.home

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.b1nd.dodam.designsystem.component.DodamContainer
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.icons.Bell
import com.b1nd.dodam.designsystem.icons.DodamLogo
import com.b1nd.dodam.designsystem.icons.ForkAndKnife

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val density = LocalDensity.current

    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState { 3 }

    var height by remember { mutableStateOf(30.dp) }
    val mealComponentHeight by animateDpAsState(targetValue = height, label = "tween")

    Log.d("MEAL", mealComponentHeight.value.toString())

    val state by viewModel.uiState.collectAsState()

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            Spacer(modifier = Modifier.height(48.dp))

            DodamContainer(
                icon = ForkAndKnife,
                title = "오늘의 급식"
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                ) {
                    HorizontalPager(state = pagerState) { page ->
                        Text(
                            text = when (page) {
                                0 -> state.meal.first
                                2 -> state.meal.second
                                else -> state.meal.third
                            },
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Row(
                        Modifier.align(Alignment.End)
                    ) {
                        repeat(pagerState.pageCount) { iteration ->
                            val color = if (pagerState.currentPage == iteration)
                                MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.secondary

                            Box(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .size(5.dp)
                            )
                        }
                    }
                }
            }
        }

        DodamTopAppBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                .statusBarsPadding(),
            containerColor = Color.Transparent,
            titleIcon = {
                Icon(
                    modifier = Modifier.height(22.dp),
                    imageVector = DodamLogo,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
            icon = Bell,
            onIconClick = {
                // TODO : Navigate to notification screen
            }
        )
    }
}
