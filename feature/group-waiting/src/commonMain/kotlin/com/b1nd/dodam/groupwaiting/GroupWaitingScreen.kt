package com.b1nd.dodam.groupwaiting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.ui.component.DodamGroupMemberCard

@Composable
fun GroupWaitingScreen(
    popBackStack: () -> Unit
) {

    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "‘B1ND’ 그룹\n" +
                        "가입 신청 대기 중인 멤버",
                onBackClick = popBackStack,
                type = TopAppBarType.Large
            )
        },
        containerColor = DodamTheme.colors.backgroundNormal
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    modifier = Modifier.padding(
                        start = 16.dp
                    ),
                    text = "학생",
                    style = DodamTheme.typography.body2Medium(),
                    color = DodamTheme.colors.labelAlternative
                )
            }

            items(2) {
                DodamGroupMemberCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    image = null,
                    name = "test $it"
                )
            }

            item {
                Text(
                    modifier = Modifier.padding(
                        start = 16.dp
                    ),
                    text = "학부모",
                    style = DodamTheme.typography.body2Medium(),
                    color = DodamTheme.colors.labelAlternative
                )
            }

            items(2) {
                DodamGroupMemberCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    image = null,
                    name = "test $it"
                )
            }
        }
    }
}