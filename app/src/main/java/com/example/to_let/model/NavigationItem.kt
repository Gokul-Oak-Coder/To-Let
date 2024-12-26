package com.example.to_let.model

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem (
    val title : String,
    val route: String? = null,
    val selectedIcon : ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)