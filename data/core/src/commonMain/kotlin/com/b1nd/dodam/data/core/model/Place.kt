package com.b1nd.dodam.data.core.model

enum class Place(val place: String) {
    PROJECT5("프로젝트 5실"),
    PROJECT6("프로젝트 6실"),
    LAB1718("랩 17, 18실"),
    LAB1920("랩 19, 20실"),
    LAB2122("랩 21, 22")
}

enum class ProjectPlace(val place: String) {
    LAB12("랩 12실"),
    LAB13("랩 13실"),
    LAB14("랩 14실"),
    LAB15("랩 15실"),
}

fun String.toPlace(): Place = when (this) {
    "프로젝트 5실" -> Place.PROJECT5
    "프로젝트 6실" -> Place.PROJECT6
    "랩 17, 18실" -> Place.LAB1718
    "랩 19, 20실" -> Place.LAB1920
    "랩 21, 22" -> Place.LAB2122
    else -> Place.PROJECT5
}

fun String.toProjectPlace(): ProjectPlace = when (this) {
    "랩 12실" -> ProjectPlace.LAB12
    "랩 13실" -> ProjectPlace.LAB13
    "랩 14실" -> ProjectPlace.LAB14
    "랩 15실" -> ProjectPlace.LAB15
    else -> ProjectPlace.LAB12
}

fun Place.toRequest(): String = when (this) {
    Place.PROJECT5 -> "프로젝트 5실"
    Place.PROJECT6 -> "프로젝ㅌ 6실"
    Place.LAB1718 -> "랩 17, 18실"
    Place.LAB1920 -> "랩 19, 20실"
    Place.LAB2122 -> "랩 21, 21실"
}

fun ProjectPlace.toRequest(): String = when (this) {
    ProjectPlace.LAB12 -> "랩 12실"
    ProjectPlace.LAB13 -> "랩 13실"
    ProjectPlace.LAB14 -> "랩 14실"
    ProjectPlace.LAB15 -> "랩 15실"
}
