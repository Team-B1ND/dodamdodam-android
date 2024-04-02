package com.b1nd.dodam.wakeupsong.datasource

import com.b1nd.dodam.wakeupsong.model.MelonChartSongResponse
import com.b1nd.dodam.wakeupsong.model.SearchWakeupSongResponse
import com.b1nd.dodam.wakeupsong.model.WakeupSongResponse
import kotlinx.collections.immutable.ImmutableList

interface WakeupSongDataSource {
    suspend fun getAllowedWakeupSongs(
        year: Int,
        month: Int,
        day: Int
    ): ImmutableList<WakeupSongResponse>

    suspend fun getMyWakeupSongs(): ImmutableList<WakeupSongResponse>
    suspend fun getPendingWakeupSongs(): ImmutableList<WakeupSongResponse>
    suspend fun deleteWakeupSong(id: Long)
    suspend fun postWakeupSong(
        artist: String,
        title: String
    )

    suspend fun searchWakeupSong(keyWord: String): ImmutableList<SearchWakeupSongResponse>
    suspend fun getMelonChart(): ImmutableList<MelonChartSongResponse>
}
