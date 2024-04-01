package com.b1nd.dodam.wakeupsong

import com.b1nd.dodam.wakeupsong.model.WakeupSong
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class WakeupSongUiState(
    val myWakeupSongs: ImmutableList<WakeupSong> = persistentListOf(),
    val allowedWakeupSongs: ImmutableList<WakeupSong> = persistentListOf(),
    val pendingWakeupSongs: ImmutableList<WakeupSong> = persistentListOf(),
    val isLoading: Boolean = false,
)
