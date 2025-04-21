package com.b1nd.dodam.data.nightstudy.repository

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.core.model.Place
import com.b1nd.dodam.data.core.model.ProjectPlace
import com.b1nd.dodam.data.core.model.toRequest
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import com.b1nd.dodam.data.nightstudy.model.MyBan
import com.b1nd.dodam.data.nightstudy.model.NightStudy
import com.b1nd.dodam.data.nightstudy.model.toModel
import com.b1nd.dodam.network.nightstudy.datasource.NightStudyDataSource
import com.b1nd.dodam.network.nightstudy.model.MyBanResponse
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

    override fun allowNightStudy(id: Long): Flow<Result<Unit>> {
        return flow {
            emit(remote.allowNightStudy(id))
        }.asResult().flowOn(dispatcher)
    }

    override fun rejectNightStudy(id: Long): Flow<Result<Unit>> {
        return flow {
            emit(remote.rejectNightStudy(id))
        }.asResult().flowOn(dispatcher)
    }

    override fun askProjectStudy(
        type: String,
        startAt: LocalDate,
        endAt: LocalDate,
        room: ProjectPlace,
        title: String,
        content: String,
        members: List<Int>
    ): Flow<Result<Unit>> {
        return flow {
            emit(
                remote.askProjectStudy(
                    type,
                    startAt,
                    endAt,
                    room.toRequest(),
                    title,
                    content,
                    members
                )
            )
        }.asResult().flowOn(dispatcher)
    }

    override fun myBan(): Flow<Result<MyBan>> {
        return flow {
            emit(remote.myBan().toModel())
        }.asResult().flowOn(dispatcher)
    }
}
