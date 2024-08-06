package com.b1nd.dodam.data.schedule.model

import com.b1nd.dodam.network.schedule.model.NetworkGrade

/*enum class Grade(name: String) {
    GRADE_1("1학년"),
    GRADE_2("2학년"),
    GRADE_3("3학년"),
    GRADE_ALL("전교생"),
    GRADE_ETC("기타"),
}*/

enum class Grade {
    GRADE_1,
    GRADE_2,
    GRADE_3,
    GRADE_ALL,
    GRADE_ETC,
}

internal fun NetworkGrade.toModel(): Grade = when (this) {
    NetworkGrade.GRADE_1 -> Grade.GRADE_1
    NetworkGrade.GRADE_2 -> Grade.GRADE_2
    NetworkGrade.GRADE_3 -> Grade.GRADE_3
    NetworkGrade.GRADE_ALL -> Grade.GRADE_ALL
    NetworkGrade.GRADE_ETC -> Grade.GRADE_ETC
}
