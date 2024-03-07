package com.b1nd.dodam.wakeupsong.model

import com.b1nd.dodam.network.core.model.NetworkStatus
import kotlinx.serialization.Serializable

@Serializable
data class WakeupSongResponse(
    val id: Long,
    val thumbnailUrl: String,
    val videoTitle: String,
    val videoId: String,
    val videoUrl: String,
    val channelTitle: String,
    val status: NetworkStatus,
)
