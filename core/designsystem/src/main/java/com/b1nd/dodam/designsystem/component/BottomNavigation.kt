package com.b1nd.dodam.designsystem.component

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.b1nd.dodam.designsystem.animation.NoInteractionSource
import kotlin.math.roundToInt

@Composable
fun DodamBottomNavigation(
    navController: NavHostController,
    bottomNavigationItems: List<BottomNavigationItem>,
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        shape = RoundedCornerShape(20.dp)
    ) {

        Row(
            Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
                .height(BottomNavigationHeight)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box {
                var selectedXOffset by remember { mutableIntStateOf(0) }
                var size by remember { mutableStateOf(Size.Zero) }

                val density = LocalDensity.current

                val x by animateIntAsState(targetValue = selectedXOffset, label = "")

                Box(
                    modifier = Modifier
                        .offset { IntOffset(x, 0) }
                        .size(with(density) { size.toDpSize() }),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .size(40.dp)
                    )
                }


                Row {
                    var selectedIndex: Int by remember { mutableIntStateOf(0) }

                    bottomNavigationItems.forEachIndexed { index, item ->
                        var xOffset by remember { mutableIntStateOf(0) }

                        BottomNavigationItem(
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                                selectedXOffset = xOffset

                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    imageVector = item.icon,
                                    contentDescription = null,
                                    tint = if (selectedIndex == index)
                                        MaterialTheme.colorScheme.onPrimary
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            modifier = Modifier
                                .onGloballyPositioned {
                                    xOffset = it.positionInParent().x.roundToInt()
                                    size = it.size.toSize()
                                },

                            interactionSource = remember { NoInteractionSource() },
                        )
                    }
                }
            }
        }
    }
}

private val BottomNavigationHeight = 64.dp

data class BottomNavigationItem(
    val route: String,
    val icon: ImageVector
)
