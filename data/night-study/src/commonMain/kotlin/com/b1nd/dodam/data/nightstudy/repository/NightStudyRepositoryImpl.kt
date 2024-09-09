package com.b1nd.dodam.data.nightstudy.repository

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.core.model.Place
import com.b1nd.dodam.data.core.model.toRequest
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import com.b1nd.dodam.data.nightstudy.model.NightStudy
import com.b1nd.dodam.data.nightstudy.model.toModel
import com.b1nd.dodam.network.nightstudy.datasource.NightStudyDataSource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.LocalDate

internal class NightStudyRepositoryImpl(
    private val remote: NightStudyDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : NightStudyRepository {
    override fun getMyNightStudy(): Flow<Result<ImmutableList<NightStudy>>> {
        return flow {
            emit(remote.getMyNightStudy().map { it.toModel() }.toImmutableList())
        }.asResult().flowOn(dispatcher)
    }

    override fun getPendingNightStudy(): Flow<Result<ImmutableList<NightStudy>>> {
        return flow {
            emit(remote.getPendingNightStudy().map { it.toModel() }.toImmutableList())
        }.asResult().flowOn(dispatcher)
    }

    override fun getStudyingNightStudy(): Flow<Result<ImmutableList<NightStudy>>> {
        return flow {
            emit(remote.getStudyingNightStudy().map { it.toModel() }.toImmutableList())
        }.asResult().flowOn(dispatcher)
    }

    override fun askNightStudy(
        place: Place,
        content: String,
        doNeedPhone: Boolean,
        reasonForPhone: String?,
        startAt: LocalDate,
        endAt: LocalDate,
    ): Flow<Result<Unit>> {
        return flow {
            emit(
                remote.askNightStudy(
                    place.toRequest(),
                    content,
                    doNeedPhone,
                    reasonForPhone,
                    startAt,
                    endAt,
                ),
            )
        }.asResult().flowOn(dispatcher)
    }

    override fun deleteNightStudy(id: Long): Flow<Result<Unit>> {
        return flow {
            emit(remote.deleteNightStudy(id))
        }.asResult().flowOn(dispatcher)
    }

    override fun getNightStudy(): Flow<Result<ImmutableList<NightStudy>>> {
        return flow {
            emit(remote.getNightStudy().map { it.toModel() }.toImmutableList())
        }.asResult().flowOn(dispatcher)
    }

    override fun getNightStudyPending(): Flow<Result<ImmutableList<NightStudy>>> {
        return flow {
            emit(remote.getNightStudyPending().map { it.toModel() }.toImmutableList())
        }.asResult().flowOn(dispatcher)
    }
}
