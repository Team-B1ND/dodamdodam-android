package com.b1nd.dodam.wakeupsong.model

import kotlinx.serialization.Serializable

@Serializable
data class MelonChartSongResponse(
    val album: String,
    val artist: String,
    val name: String,
    val rank: Int,
    val thumbnail: String
)
