package com.b1nd.dodam.student.home.model

import com.b1nd.dodam.data.outing.model.Outing
import com.b1nd.dodam.wakeupsong.model.WakeupSong
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeUiState(
    val isLoading: Boolean = false,
    val meal: ImmutableList<String?> = persistentListOf("", "", ""),
    val wakeupSongs: ImmutableList<WakeupSong> = persistentListOf(),
    val out: ImmutableList<Outing> = persistentListOf(),
)
