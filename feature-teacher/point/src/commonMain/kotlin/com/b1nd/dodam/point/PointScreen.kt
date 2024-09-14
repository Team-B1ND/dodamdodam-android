package com.b1nd.dodam.point

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.b1nd.dodam.data.point.model.PointReason
import com.b1nd.dodam.data.point.model.PointType
import com.b1nd.dodam.data.point.model.ScoreType
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.point.model.PointStudentModel
import com.b1nd.dodam.point.screen.GiveScreen
import com.b1nd.dodam.point.screen.SelectScreen
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun PointScreen(

) {
    var nowPage by remember { mutableStateOf(1) }

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
    AnimatedVisibility(
        visible = nowPage == 1
    ) {
        GiveScreen(
            isLoading = false,
            reasonList = persistentListOf(
                PointReason(
                    id = 0,
                    reason = "위에 규정된 사항 이외의 경우에 대한 상점을 사감이 판단하여 상점을 부여한다.",
                    score = 1,
                    scoreType = ScoreType.BONUS,
                    pointType = PointType.DORMITORY,
                ),
                PointReason(
                    id = 1,
                    reason = "청소 우수",
                    score = 1,
                    scoreType = ScoreType.MINUS,
                    pointType = PointType.DORMITORY,
                )
            ),
            studentList = persistentListOf(
                PointStudentModel(
                    id = 3,
                    name = "test",
                    grade = 2,
                    room = 4,
                    selected = true
                ),
                PointStudentModel(
                    id = 4,
                    name = "test",
                    grade = 2,
                    room = 4,
                    selected = true
                ),
                PointStudentModel(
                    id = 5,
                    name = "test",
                    grade = 2,
                    room = 4,
                    selected = true
                ),
                PointStudentModel(
                    id = 6,
                    name = "test",
                    grade = 2,
                    room = 4,
                    selected = true
                ),
                PointStudentModel(
                    id = 7,
                    name = "test",
                    grade = 2,
                    room = 4,
                    selected = true
                ),
                PointStudentModel(
                    id = 8,
                    name = "test",
                    grade = 2,
                    room = 4,
                    selected = true
                ),
            ),
            popBackStack = {}
        )
    }
}

internal fun getDodamSegment(text: String, selectText: String, onClick: (String) -> Unit) =
    DodamSegment(
        selected = text == selectText,
        onClick = {
            onClick(text)
        },
        text = text,
        enabled = true
    )