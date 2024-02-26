package com.b1nd.dodam.wakeup_song.mapper

import com.b1nd.dodam.model.WakeupSong
import com.b1nd.dodam.model.WakeupSongStatus
import com.b1nd.dodam.wakeup_song.model.NetworkWakeupSongStatus
import com.b1nd.dodam.wakeup_song.model.WakeupSongResponse

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
    createdDate = createdDate
)

internal fun NetworkWakeupSongStatus.toModel(): WakeupSongStatus = when(this) {
    NetworkWakeupSongStatus.PENDING -> WakeupSongStatus.PENDING
    NetworkWakeupSongStatus.ALLOWED -> WakeupSongStatus.ALLOWED
    NetworkWakeupSongStatus.DENIED -> WakeupSongStatus.DENIED
}