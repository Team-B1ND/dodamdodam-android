package com.b1nd.dodam.wakeup_song.datasource

import com.b1nd.dodam.wakeup_song.model.WakeupSongResponse
import kotlinx.collections.immutable.ImmutableList

interface WakeupSongDataSource {
    suspend fun getAllowedWakeupSongs(
        year: Int,
        month: Int,
        day: Int
    ): ImmutableList<WakeupSongResponse>
}
