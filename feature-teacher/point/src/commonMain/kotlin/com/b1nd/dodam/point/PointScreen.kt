package com.b1nd.dodam.point

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.b1nd.dodam.point.model.PointStudentModel
import com.b1nd.dodam.point.screen.SelectScreen
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun PointScreen(

) {
    var nowPage by remember { mutableStateOf(0) }

    AnimatedVisibility(
        visible = nowPage == 0
    ) {
        SelectScreen(
            items = persistentListOf(
                PointStudentModel(
                    id = 0,
                    name = "test",
                    grade = 1,
                    room = 3,
                ),
                PointStudentModel(
                    id = 1,
                    name = "test",
                    grade = 2,
                    room = 3,
                ),
                PointStudentModel(
                    id = 2,
                    name = "test",
                    grade = 1,
                    room = 1,
                ),
                PointStudentModel(
                    id = 3,
                    name = "test",
                    grade = 2,
                    room = 4,
                ),
            ),
            onClickStudent = {},
            onClickNextPage = {},
            popBackStack = {}
        )
    }
}