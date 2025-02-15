package com.b1nd.dodam.data.division.model

enum class DivisionPermission(
    val value: Int,
) {
    READER(3),
    WRITER(2),
    ADMIN(1),
}

fun DivisionPermission.isAdmin() = this == DivisionPermission.ADMIN
