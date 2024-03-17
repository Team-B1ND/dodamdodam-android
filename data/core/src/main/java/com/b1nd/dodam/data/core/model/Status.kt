package com.b1nd.dodam.data.core.model

import com.b1nd.dodam.network.core.model.NetworkStatus

enum class Status(name: String) {
    PENDING("PENDING"),
    ALLOWED("ALLOWED"),
    REJECTED("REJECTED"),
}

fun NetworkStatus.toModel(): Status = when (this) {
    NetworkStatus.PENDING -> Status.PENDING
    NetworkStatus.ALLOWED -> Status.ALLOWED
    NetworkStatus.REJECTED -> Status.REJECTED
}
