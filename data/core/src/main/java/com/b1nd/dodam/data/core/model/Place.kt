package com.b1nd.dodam.data.core.model

import com.b1nd.dodam.network.core.model.NetworkPlace

enum class Place(val place: String) {
    PROGRAMMING_1("프로그래밍1실"),
    PROGRAMMING_2("프로그래밍2실"),
}

fun NetworkPlace.toModel(): Place = when (this) {
    NetworkPlace.PROGRAMMING_1 -> Place.PROGRAMMING_1
    NetworkPlace.PROGRAMMING_2 -> Place.PROGRAMMING_2
}

fun Place.toRequest(): NetworkPlace = when (this) {
    Place.PROGRAMMING_1 -> NetworkPlace.PROGRAMMING_1
    Place.PROGRAMMING_2 -> NetworkPlace.PROGRAMMING_2
}
