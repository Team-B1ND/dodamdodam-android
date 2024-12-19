package com.b1nd.dodam.wakeupsong

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.wakeupsong.model.MelonChartSong
import com.b1nd.dodam.wakeupsong.model.SearchWakeupSong
import com.b1nd.dodam.wakeupsong.model.WakeupSong
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface WakeupSongRepository {
    fun getAllowedWakeupSongs(year: Int, month: Int, day: Int): Flow<Result<ImmutableList<WakeupSong>>>
    fun getMyWakeupSongs(): Flow<Result<ImmutableList<WakeupSong>>>
    fun getPendingWakeupSongs(): Flow<Result<ImmutableList<WakeupSong>>>
    fun deleteWakeupSong(id: Long): Flow<Result<Unit>>
    fun postWakeupSong(artist: String, title: String): Flow<Result<Unit>>
    fun postWakeupSongFromYoutubeUrl(url: String): Flow<Result<Unit>>

    fun searchWakeupSong(keyWord: String): Flow<Result<ImmutableList<SearchWakeupSong>>>
    fun getMelonChart(): Flow<Result<ImmutableList<MelonChartSong>>>
}
