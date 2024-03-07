package com.b1nd.dodam.wakeupsong.mapper

import com.b1nd.dodam.data.core.toModel
import com.b1nd.dodam.model.WakeupSong
import com.b1nd.dodam.model.Status
import com.b1nd.dodam.network.core.model.NetworkStatus
import com.b1nd.dodam.wakeupsong.model.WakeupSongResponse

internal fun WakeupSongResponse.toModel(): WakeupSong = WakeupSong(
    id = id,
    thumbnailUrl = thumbnailUrl,
    videoTitle = videoTitle,
    videoId = videoId,
    videoUrl = videoUrl,
    duration = duration,
    channelTitle = channelTitle,
    status = status.toModel(),
    playDate = playDate,
    createdDate = createdDate,
)
