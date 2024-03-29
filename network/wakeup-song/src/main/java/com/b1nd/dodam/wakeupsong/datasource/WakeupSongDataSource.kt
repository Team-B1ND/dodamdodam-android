package com.b1nd.dodam.wakeupsong.datasource

import com.b1nd.dodam.wakeupsong.model.WakeupSongResponse
import kotlinx.collections.immutable.ImmutableList

interface WakeupSongDataSource {
    suspend fun getAllowedWakeupSongs(year: Int, month: Int, day: Int): ImmutableList<WakeupSongResponse>
}
