package com.expensetracker.navigation

sealed class Screen(val route: String) {

    object Home : Screen("home")

    object Transactions : Screen("transactions")

    object AddTransaction : Screen("add_transaction")

}