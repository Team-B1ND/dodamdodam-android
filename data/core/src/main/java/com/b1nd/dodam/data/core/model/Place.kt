package com.b1nd.dodam.data.core.model

enum class Place(val place: String) {
    PROGRAMMING_1("프로그래밍 1실"),
    PROGRAMMING_2("프로그래밍 2실"),
    PROGRAMMING_3("프로그래밍 3실"),
    KOREAN("국어실"),
    MATH("수학실"),
    SOCIETY("사회실"),
}

fun String.toPlace(): Place = when (this) {
    "프로그래밍1실" -> Place.PROGRAMMING_1
    "프로그래밍2실" -> Place.PROGRAMMING_2
    "프로그래밍3실" -> Place.PROGRAMMING_3
    "국어실" -> Place.KOREAN
    "수학실" -> Place.MATH
    "사회실" -> Place.SOCIETY
    else -> Place.PROGRAMMING_1
}

fun Place.toRequest(): String = when (this) {
    Place.PROGRAMMING_1 -> "프로그래밍1실"
    Place.PROGRAMMING_2 -> "프로그래밍2실"
    Place.PROGRAMMING_3 -> "프로그래밍3실"
    Place.KOREAN -> "국어실"
    Place.SOCIETY -> "사회실"
    Place.MATH -> "수학실"
}
