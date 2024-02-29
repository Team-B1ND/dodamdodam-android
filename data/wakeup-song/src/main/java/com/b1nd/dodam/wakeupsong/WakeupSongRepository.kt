package com.b1nd.dodam.wakeupsong

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.model.WakeupSong
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface WakeupSongRepository {
    fun getAllowedWakeupSongs(year: Int, month: Int, day: Int): Flow<Result<ImmutableList<WakeupSong>>>
}
