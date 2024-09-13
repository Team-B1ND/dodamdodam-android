package com.b1nd.dodam.member.model

enum class ActiveStatus {
    ACTIVE,
    PENDING,
    DEACTIVATED
}

internal fun String.toActiveStatus() =
    when (this) {
        "ACTIVE" -> ActiveStatus.ACTIVE
        "PENDING" -> ActiveStatus.PENDING
        "DEACTIVATED" -> ActiveStatus.DEACTIVATED
        else -> ActiveStatus.ACTIVE
    }