package com.b1nd.dodam.student.main.navigation

import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.meal.navigation.MEAL_ROUTE
import com.b1nd.dodam.member.navigation.ALL_ROUTE
import com.b1nd.dodam.nightstudy.navigation.NIGHT_STUDY_ROUTE
import com.b1nd.dodam.notice.navigation.NOTICE_ROUTE
import com.b1nd.dodam.outing.nanigation.OUTING_ROUTE
import com.b1nd.dodam.student.home.navigation.HOME_ROUTE

enum class MainDestination(
    val icon: DodamIcons,
    val route: String,
) {
    HOME(DodamIcons.Home, HOME_ROUTE),
    NOTICE(DodamIcons.Bell, NOTICE_ROUTE),
    OUT(DodamIcons.DoorOpen, OUTING_ROUTE),
    NIGHT_STUDY(DodamIcons.MoonPlus, NIGHT_STUDY_ROUTE),
    ALL(DodamIcons.Menu, ALL_ROUTE),
}
