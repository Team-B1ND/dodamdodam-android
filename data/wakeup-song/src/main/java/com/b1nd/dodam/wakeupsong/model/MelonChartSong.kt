package com.b1nd.dodam.wakeupsong.model

data class MelonChartSong(
    val album: String,
    val artist: String,
    val name: String,
    val rank: Int,
    val thumbnail: String,
)

fun MelonChartSongResponse.toModel(): MelonChartSong = MelonChartSong(
    album = album,
    artist = artist,
    name = name,
    rank = rank,
    thumbnail = thumbnail,
)
