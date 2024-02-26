package com.b1nd.dodam.student.home.model

import com.b1nd.dodam.model.WakeupSong
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeUiState(
    val isLoading: Boolean = false,
    val meal: Triple<String, String, String> = Triple(
        "아침을 불러오고 있어요.",
        "점심을 불러오고 있어요.",
        "저녁을 불러오고 있어요."
    ),
    val wakeupSongs: ImmutableList<WakeupSong> = persistentListOf()
)
