package com.b1nd.dodam.wakeupsong.model

data class SearchWakeupSong(
    val channelTitle: String,
    val thumbnail: String,
    val videoId: String,
    val videoTitle: String,
    val videoUrl: String
)

fun SearchWakeupSongResponse.toModel():SearchWakeupSong = SearchWakeupSong(
    channelTitle = channelTitle,
    thumbnail = thumbnail,
    videoId = videoId,
    videoTitle = videoTitle,
    videoUrl = videoUrl,
)
