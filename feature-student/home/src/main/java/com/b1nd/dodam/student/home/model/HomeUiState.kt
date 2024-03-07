package com.b1nd.dodam.student.home.model

import com.b1nd.dodam.model.Outing
import com.b1nd.dodam.model.WakeupSong
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

data class HomeUiState(
    val isLoading: Boolean = false,
    val meal: ImmutableList<String?> = persistentListOf("", "", ""),
    val wakeupSongs: ImmutableList<WakeupSong> = persistentListOf(),
    val out: ImmutableList<Outing> = persistentListOf(),
)
