package com.b1nd.dodam.model

data class WakeupSong(
    val id: Long,
    val thumbnailUrl: String,
    val videoTitle: String,
    val videoId: String,
    val videoUrl: String,
    val duration: String,
    val channelTitle: String,
    val status: WakeupSongStatus,
    val playDate: String,
    val createdDate: String,
)
