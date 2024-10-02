package com.b1nd.dodam.student.main.navigation

import androidx.compose.runtime.Composable
import com.b1nd.dodam.dds.style.DoorOpenIcon
import com.b1nd.dodam.dds.style.ForkAndKnifeIcon
import com.b1nd.dodam.dds.style.HomeIcon
import com.b1nd.dodam.dds.style.MenuIcon
import com.b1nd.dodam.dds.style.MoonPlusIcon
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.meal.navigation.MEAL_ROUTE
import com.b1nd.dodam.member.navigation.ALL_ROUTE
import com.b1nd.dodam.nightstudy.navigation.NIGHT_STUDY_ROUTE
import com.b1nd.dodam.outing.nanigation.OUTING_ROUTE
import com.b1nd.dodam.student.home.navigation.HOME_ROUTE

enum class MainDestination(
    val icon: DodamIcons,
    val route: String,
) {
    HOME(DodamIcons.Home, HOME_ROUTE),
    MEAL(DodamIcons.ForkAndKnife, MEAL_ROUTE),
    OUT(DodamIcons.DoorOpen, OUTING_ROUTE),
    NIGHT_STUDY(DodamIcons.MoonPlus, NIGHT_STUDY_ROUTE),
    ALL(DodamIcons.Menu, ALL_ROUTE),
}
