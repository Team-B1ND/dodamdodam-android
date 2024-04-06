package com.b1nd.dodam.student.main.navigation

import androidx.compose.runtime.Composable
import com.b1nd.dodam.dds.style.DoorOpenIcon
import com.b1nd.dodam.dds.style.ForkAndKnifeIcon
import com.b1nd.dodam.dds.style.HomeIcon
import com.b1nd.dodam.dds.style.MenuIcon
import com.b1nd.dodam.dds.style.MoonPlusIcon

enum class MainDestination(
    val icon: @Composable () -> Unit,
) {
    HOME({ HomeIcon() }),
    MEAL({ ForkAndKnifeIcon() }),
    OUT({ DoorOpenIcon() }),
    NIGHT_STUDY({ MoonPlusIcon() }),
    ALL({ MenuIcon() }),
}
