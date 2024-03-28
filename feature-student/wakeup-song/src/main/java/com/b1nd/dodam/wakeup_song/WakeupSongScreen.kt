package com.b1nd.dodam.wakeup_song

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.b1nd.dodam.wakeup_song.viewmodel.WakeupSongViewModel

@Composable
fun WakeupSongScreen(
    onClickAddWakeupSong: () -> Unit,
    viewModel: WakeupSongViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
}
