package com.b1nd.dodam.wakeupsong.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchWakeupSongRequest(
    val artist: String,
    val title: String,
)
