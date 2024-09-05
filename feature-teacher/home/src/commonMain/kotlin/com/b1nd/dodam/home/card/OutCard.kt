package com.b1nd.dodam.home.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamLoadingDots
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.home.InnerCountCard
import com.b1nd.dodam.ui.component.DodamContainer

@Composable
internal fun OutCard(
    showShimmer: Boolean,
) {
    DodamContainer(
        icon = DodamIcons.DoorOpen,
        title = "외출/외박 현황"
    ) {
        if (!showShimmer) {
            Column(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
            ) {
                InnerCountCard(
                    title = "현재 외출중인 학생",
                    content = "13명",
                    buttonText = "12명 대기중",
                    onClick = {

                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                InnerCountCard(
                    title = "현재 외박중인 학생",
                    content = "10명",
                    buttonText = "11명 대기중",
                    onClick = {

                    }
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                contentAlignment = Alignment.Center,
            ) {
                DodamLoadingDots()
            }
        }
    }
}
