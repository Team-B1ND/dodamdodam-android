package com.b1nd.dodam.data.nightstudy

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.core.model.Place
import com.b1nd.dodam.data.nightstudy.model.NightStudy
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface NightStudyRepository {
    fun getMyNightStudy(): Flow<Result<ImmutableList<NightStudy>>>

    fun getPendingNightStudy(): Flow<Result<ImmutableList<NightStudy>>>

    fun getStudyingNightStudy(): Flow<Result<ImmutableList<NightStudy>>>

    fun askNightStudy(place: Place, content: String, doNeedPhone: Boolean, reasonForPhone: String?, startAt: LocalDate, endAt: LocalDate): Flow<Result<Unit>>

    fun deleteNightStudy(id: Long): Flow<Result<Unit>>
}
