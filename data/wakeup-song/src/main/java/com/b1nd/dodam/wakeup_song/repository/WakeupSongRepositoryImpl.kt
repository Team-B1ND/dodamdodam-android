package com.b1nd.dodam.wakeup_song.repository

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.model.WakeupSong
import com.b1nd.dodam.wakeup_song.WakeupSongRepository
import com.b1nd.dodam.wakeup_song.datasource.WakeupSongDataSource
import com.b1nd.dodam.wakeup_song.mapper.toModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class WakeupSongRepositoryImpl @Inject constructor(
    private val network: WakeupSongDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher
) : WakeupSongRepository {
    override fun getAllowedWakeupSongs(
        year: Int,
        month: Int,
        day: Int
    ): Flow<Result<ImmutableList<WakeupSong>>> {
        return flow {
            emit(network.getAllowedWakeupSongs(year, month, day)
                .map { it.toModel() }
                .toImmutableList())
        }
            .asResult()
            .flowOn(dispatcher)
    }
}