package com.b1nd.dodam.data.nightstudy

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.nightstudy.model.NightStudy
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface NightStudyRepository {
    fun getMyNightStudy(): Flow<Result<ImmutableList<NightStudy>>>
}
