package com.b1nd.dodam.data.nightstudy.model

import com.b1nd.dodam.data.core.model.StudentImage
import com.b1nd.dodam.data.core.model.toModel
import com.b1nd.dodam.network.nightstudy.model.MyBanResponse
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime

data class MyBan(
    val id: Long?,
    val student: StudentImage?,
    val banReason: String?,
    val started: LocalDateTime?,
    val ended: LocalDateTime?,
)

internal fun MyBanResponse.toModel(): MyBan = MyBan(
    id = id,
    student = student?.toModel(),
    banReason = banReason,
    started = started?.atTime(hour = 23, minute = 0, second = 0),
    ended = ended?.atTime(hour = 23, minute = 0, second = 0),
)
