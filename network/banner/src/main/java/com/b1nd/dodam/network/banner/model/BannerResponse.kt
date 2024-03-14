package com.b1nd.dodam.network.banner.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class BannerResponse(
    val id: Long,
    val imageUrl: String,
    val redirectUrl: String,
    val title: String,
    val status: NetworkBannerStatus,
    val expireAt: LocalDateTime,
)
