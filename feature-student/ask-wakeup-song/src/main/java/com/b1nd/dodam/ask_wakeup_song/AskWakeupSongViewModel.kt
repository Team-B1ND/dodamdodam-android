package com.b1nd.dodam.ask_wakeup_song

import androidx.lifecycle.ViewModel
import com.b1nd.dodam.wakeupsong.WakeupSongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AskWakeupSongViewModel @Inject constructor(
    private val wakeupSongRepository: WakeupSongRepository
) : ViewModel() {
}
