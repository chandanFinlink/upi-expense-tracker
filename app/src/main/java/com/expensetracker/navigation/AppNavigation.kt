package com.expensetracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.expensetracker.features.home.HomeScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {

        composable(Routes.Home.route) {
            HomeScreen()
        }

        composable(Routes.Transactions.route) {
            HomeScreen()
        }

        composable(Routes.Budgets.route) {
            HomeScreen()
        }

        composable(Routes.Analytics.route) {
            HomeScreen()
        }

        composable(Routes.Settings.route) {
            HomeScreen()
        }

    }

}