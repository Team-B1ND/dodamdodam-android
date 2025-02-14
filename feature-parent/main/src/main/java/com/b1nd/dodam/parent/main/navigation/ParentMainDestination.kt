package com.b1nd.dodam.parent.main.navigation

import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.notice.navigation.NOTICE_ROUTE
import com.b1nd.dodam.parnet.home.navigation.PARENT_HOME_ROUTE

enum class ParentMainDestination(
    val icon: DodamIcons,
    val route: String,
) {
    HOME(DodamIcons.Home, PARENT_HOME_ROUTE),
    NOTICE(DodamIcons.Bell, NOTICE_ROUTE),
}
