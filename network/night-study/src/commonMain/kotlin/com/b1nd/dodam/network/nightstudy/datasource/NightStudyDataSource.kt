package com.b1nd.dodam.network.nightstudy.datasource

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.network.nightstudy.model.NightStudyResponse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface NightStudyDataSource {
    suspend fun getMyNightStudy(): ImmutableList<NightStudyResponse>

    suspend fun getPendingNightStudy(): ImmutableList<NightStudyResponse>

    suspend fun getStudyingNightStudy(): ImmutableList<NightStudyResponse>

    suspend fun askNightStudy(place: String, content: String, doNeedPhone: Boolean, reasonForPhone: String?, startAt: LocalDate, endAt: LocalDate)

    suspend fun deleteNightStudy(id: Long)

    suspend fun getNightStudy(): ImmutableList<NightStudyResponse>

    suspend fun getNightStudyPending(): ImmutableList<NightStudyResponse>

    suspend fun allowNightStudy(id: Long)

    suspend fun rejectNightStudy(id: Long)
}
