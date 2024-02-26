package com.b1nd.dodam.wakeup_song.model

import kotlinx.serialization.Serializable

@Serializable
enum class NetworkWakeupSongStatus {
    ALLOWED,
    PENDING,
    DENIED
}
