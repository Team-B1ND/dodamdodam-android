package com.b1nd.dodam.network.banner.model

import kotlinx.serialization.Serializable

@Serializable
enum class NetworkBannerStatus {
    ACTIVE,
    DEACTIVATED
}