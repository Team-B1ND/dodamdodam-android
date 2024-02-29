package com.b1nd.dodam.wakeupsong.model

import kotlinx.serialization.Serializable

@Serializable
data class WakeupSongResponse(
    val id: Long,
    val thumbnailUrl: String,
    val videoTitle: String,
    val videoId: String,
    val videoUrl: String,
    val duration: String,
    val channelTitle: String,
    val status: NetworkWakeupSongStatus,
    val playDate: String,
    val createdDate: String,
)
