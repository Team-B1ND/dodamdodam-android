package com.b1nd.dodam.wakeupsong.model

import kotlinx.serialization.Serializable

@Serializable
enum class NetworkWakeupSongStatus {
    ALLOWED,
    PENDING,
    DENIED,
}
