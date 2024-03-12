package com.b1nd.dodam.network.nightstudy.datasource

import com.b1nd.dodam.network.nightstudy.model.NightStudyResponse
import kotlinx.collections.immutable.ImmutableList

interface NightStudyDataSource {
    suspend fun getMyNightStudy(): ImmutableList<NightStudyResponse>
}
