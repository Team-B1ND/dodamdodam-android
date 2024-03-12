package com.b1nd.dodam.data.banner.model

import com.b1nd.dodam.network.banner.model.NetworkBannerStatus

enum class BannerStatus {
    ACTIVE,
    DEACTIVATED
}

internal fun NetworkBannerStatus.toModel(): BannerStatus = when (this) {
    NetworkBannerStatus.ACTIVE -> BannerStatus.ACTIVE
    NetworkBannerStatus.DEACTIVATED -> BannerStatus.DEACTIVATED
}