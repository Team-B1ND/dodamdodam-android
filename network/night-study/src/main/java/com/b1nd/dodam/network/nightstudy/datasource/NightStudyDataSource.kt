package com.b1nd.dodam.network.nightstudy.datasource

import com.b1nd.dodam.network.core.model.NetworkPlace
import com.b1nd.dodam.network.nightstudy.model.NightStudyResponse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate

interface NightStudyDataSource {
    suspend fun getMyNightStudy(): ImmutableList<NightStudyResponse>

    suspend fun askNightStudy(place: NetworkPlace, content: String, doNeedPhone: Boolean, reasonForPhone: String?, startAt: LocalDate, endAt: LocalDate)
}
