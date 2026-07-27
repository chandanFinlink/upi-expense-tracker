package com.expensetracker.navigation

sealed class Routes(val route: String) {

    object Home : Routes("home")
    object Transactions : Routes("transactions")
    object Budgets : Routes("budgets")
    object Analytics : Routes("analytics")
    object Settings : Routes("settings")

    object AddTransaction : Routes("add_transaction")

}