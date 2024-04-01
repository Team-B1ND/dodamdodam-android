package com.b1nd.dodam.wakeupsong.model

import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.core.model.toModel
import kotlinx.datetime.LocalDate

data class WakeupSong(
    val id: Long,
    val thumbnailUrl: String,
    val videoTitle: String,
    val videoId: String,
    val videoUrl: String,
    val channelTitle: String,
    val status: Status,
    val createdAt: LocalDate,
)

internal fun WakeupSongResponse.toModel(): WakeupSong = WakeupSong(
    id = id,
    thumbnailUrl = thumbnailUrl,
    videoTitle = videoTitle,
    videoId = videoId,
    videoUrl = videoUrl,
    channelTitle = channelTitle,
    status = status.toModel(),
    createdAt = createdAt,
)
