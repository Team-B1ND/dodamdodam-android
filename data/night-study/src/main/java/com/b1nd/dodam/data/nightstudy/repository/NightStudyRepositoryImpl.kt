package com.b1nd.dodam.data.nightstudy.repository

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import com.b1nd.dodam.data.nightstudy.model.NightStudy
import com.b1nd.dodam.data.nightstudy.model.toModel
import com.b1nd.dodam.network.nightstudy.datasource.NightStudyDataSource
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class NightStudyRepositoryImpl @Inject constructor(
    private val remote: NightStudyDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : NightStudyRepository {
    override fun getMyNightStudy(): Flow<Result<ImmutableList<NightStudy>>> {
        return flow {
            emit(remote.getMyNightStudy().map { it.toModel() }.toImmutableList())
        }.asResult().flowOn(dispatcher)
    }
}
