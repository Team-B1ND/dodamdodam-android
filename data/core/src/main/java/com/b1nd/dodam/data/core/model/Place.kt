package com.b1nd.dodam.data.core.model

import com.b1nd.dodam.network.core.model.NetworkPlace

enum class Place(name: String) {
    PROGRAMMING_1("프로그래밍1실"),
}

fun NetworkPlace.toModel(): Place = when (this) {
    NetworkPlace.PROGRAMMING_1 -> Place.PROGRAMMING_1
}
