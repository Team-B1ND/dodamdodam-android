package com.b1nd.dodam.club.model

sealed interface ClubSideEffect {
    data object Exist: ClubSideEffect
    data object NotExist: ClubSideEffect
    data object Apply: ClubSideEffect
}