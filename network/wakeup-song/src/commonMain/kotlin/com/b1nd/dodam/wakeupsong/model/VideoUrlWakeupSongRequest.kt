package com.b1nd.dodam.wakeupsong.model

import kotlinx.serialization.Serializable

@Serializable
data class VideoUrlWakeupSongRequest(
    val videoUrl: String,
)
