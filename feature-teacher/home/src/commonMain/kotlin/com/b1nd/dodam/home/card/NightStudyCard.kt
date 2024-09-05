package com.b1nd.dodam.home.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.data.nightstudy.model.NightStudy
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.home.InnerCountCard
import com.b1nd.dodam.ui.component.DodamContainer

@Composable
internal fun NightStudyCard(

) {
    DodamContainer(
        icon = DodamIcons.MoonPlus,
        title = "심야 자습 현황"
    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
        ) {
            InnerCountCard(
                title = "현재 자습중인 학생",
                content = "13명",
                buttonText = "12명 대기중",
                onClick = {

                }
            )
        }
    }
}
