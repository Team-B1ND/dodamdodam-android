package com.b1nd.dodam.wakeupsong.datasource

import com.b1nd.dodam.wakeupsong.model.WakeupSongResponse
import kotlinx.collections.immutable.ImmutableList

interface WakeupSongDataSource {
    suspend fun getAllowedWakeupSongs(year: Int, month: Int, day: Int): ImmutableList<WakeupSongResponse>
    suspend fun getMyWakeupSongs(): ImmutableList<WakeupSongResponse>
    suspend fun getPendingWakeupSongs(): ImmutableList<WakeupSongResponse>
    suspend fun deleteWakeupSongs(id: Long): Unit
}
