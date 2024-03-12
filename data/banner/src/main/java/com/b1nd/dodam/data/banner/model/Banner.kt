package com.b1nd.dodam.data.banner.model

import com.b1nd.dodam.network.banner.model.BannerResponse
import kotlinx.datetime.LocalDateTime

data class Banner(
    val id: Long,
    val imageUrl: String,
    val redirectUrl: String,
    val title: String,
    val status: BannerStatus,
    val expireAt: LocalDateTime,
)

internal fun BannerResponse.toModel(): Banner = Banner(
    id = id,
    imageUrl = imageUrl,
    redirectUrl = redirectUrl,
    title = title,
    status = status.toModel(),
    expireAt = expireAt
)
