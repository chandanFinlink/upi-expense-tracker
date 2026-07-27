package com.expensetracker.navigation

sealed class Routes(val route: String) {

    data object Home : Routes("home")

    data object Transactions : Routes("transactions")

    data object Budgets : Routes("budgets")

    data object Analytics : Routes("analytics")

    data object Settings : Routes("settings")
}