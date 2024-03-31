package com.b1nd.dodam.wakeupsong

import com.b1nd.dodam.wakeupsong.model.WakeupSong
import kotlinx.collections.immutable.ImmutableList

data class WakeupSongUiState(
    val myWakeupSongs: ImmutableList<WakeupSong>? = null,
    val allowedWakeupSongs: ImmutableList<WakeupSong>? = null,
    val pendingWakeupSongs: ImmutableList<WakeupSong>? = null,
    val isLoading: Boolean = false,
)
