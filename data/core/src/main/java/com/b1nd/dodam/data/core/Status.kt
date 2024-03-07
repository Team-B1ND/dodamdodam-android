package com.b1nd.dodam.data.core

import com.b1nd.dodam.model.Status
import com.b1nd.dodam.network.core.model.NetworkStatus

fun NetworkStatus.toModel(): Status = when (this) {
    NetworkStatus.PENDING -> Status.PENDING
    NetworkStatus.ALLOWED -> Status.ALLOWED
    NetworkStatus.DENIED -> Status.DENIED
}
