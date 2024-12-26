package com.example.to_let.model

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon : ImageVector,
    val unSelectedIcon : ImageVector,
    val hasNews : Boolean,
    val badgeCount : Int? = null
)