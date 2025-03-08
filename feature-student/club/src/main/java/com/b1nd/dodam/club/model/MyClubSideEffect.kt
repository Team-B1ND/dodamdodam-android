package com.b1nd.dodam.club.model

sealed interface MyClubSideEffect {
    data object Exist : MyClubSideEffect
    data object NotExist : MyClubSideEffect
    data object Apply : MyClubSideEffect
}
