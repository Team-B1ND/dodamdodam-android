package com.b1nd.dodam.ask_wakeup_song.model

import com.b1nd.dodam.wakeupsong.model.MelonChartSong
import com.b1nd.dodam.wakeupsong.model.SearchWakeupSong
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class AskWakeupSongUiState(
    val isLoading: Boolean = false,
    val isSearchLoading: Boolean = false,
    val isError: Boolean = false,
    val melonChartSongs: ImmutableList<MelonChartSong> = persistentListOf(),
    val searchWakeupSongs: ImmutableList<SearchWakeupSong> = persistentListOf(),
)
