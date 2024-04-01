package com.b1nd.dodam.wakeupsong.repository

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.wakeupsong.WakeupSongRepository
import com.b1nd.dodam.wakeupsong.datasource.WakeupSongDataSource
import com.b1nd.dodam.wakeupsong.model.WakeupSong
import com.b1nd.dodam.wakeupsong.model.toModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class WakeupSongRepositoryImpl @Inject constructor(
    private val network: WakeupSongDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : WakeupSongRepository {
    override fun getAllowedWakeupSongs(year: Int, month: Int, day: Int): Flow<Result<ImmutableList<WakeupSong>>> {
        return flow {
            emit(
                network.getAllowedWakeupSongs(year, month, day)
                    .map { it.toModel() }
                    .toImmutableList(),
            )
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override fun getMyWakeupSongs(): Flow<Result<ImmutableList<WakeupSong>>> {
        return flow {
            emit(
                network.getMyWakeupSongs()
                    .map { it.toModel() }
                    .toImmutableList(),
            )
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override fun getPendingWakeupSongs(): Flow<Result<ImmutableList<WakeupSong>>> {
        return flow {
            emit(
                network.getPendingWakeupSongs()
                    .map { it.toModel() }
                    .toImmutableList(),
            )
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override fun deleteWakeupSong(id: Long): Flow<Result<Unit>> {
        return flow {
            emit(
                network.deleteWakeupSongs(id),
            )
        }
            .asResult()
            .flowOn(dispatcher)
    }
}
