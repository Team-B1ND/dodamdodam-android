package com.b1nd.dodam.parent.children_manage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType


@Composable
internal fun ChildrenManageScreen(
    popBackStack: () -> Unit
) {
    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "학생코드로\n" +
                        "자녀를 등록해주세요",
                onBackClick = popBackStack,
                type = TopAppBarType.Large,
            )
        },
        containerColor = DodamTheme.colors.backgroundNormal
    ) {
        Box(
            modifier = Modifier.padding(it)
        )
    }
}