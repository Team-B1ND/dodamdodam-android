package com.b1nd.dodam.wakeupsong.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchWakeupSongResponse(
    val channelTitle: String,
    val thumbnail: String,
    val videoId: String,
    val videoTitle: String,
    val videoUrl: String
)
