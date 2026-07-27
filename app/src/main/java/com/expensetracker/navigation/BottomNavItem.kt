package com.expensetracker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(

    val title: String,

    val icon: ImageVector,

    val route: String
)

val BottomNavigationItems = listOf(

    BottomNavItem(
        title = "Home",
        icon = Icons.Rounded.Home,
        route = Routes.Home.route
    ),

    BottomNavItem(
        title = "Transactions",
        icon = Icons.Rounded.ReceiptLong,
        route = Routes.Transactions.route
    ),

    BottomNavItem(
        title = "Budgets",
        icon = Icons.Rounded.Wallet,
        route = Routes.Budgets.route
    ),

    BottomNavItem(
        title = "Analytics",
        icon = Icons.Rounded.Analytics,
        route = Routes.Analytics.route
    ),

    BottomNavItem(
        title = "Settings",
        icon = Icons.Rounded.Settings,
        route = Routes.Settings.route
    )
)